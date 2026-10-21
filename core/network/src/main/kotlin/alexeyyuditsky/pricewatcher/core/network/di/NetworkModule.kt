package alexeyyuditsky.pricewatcher.core.network.di

import alexeyyuditsky.pricewatcher.core.network.AuthInterceptor
import alexeyyuditsky.pricewatcher.core.network.NetworkConfig
import alexeyyuditsky.pricewatcher.core.network.PlainRetrofit
import alexeyyuditsky.pricewatcher.core.network.TokenAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    @PlainRetrofit
    fun providePlainHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .callTimeout(180, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    @Provides
    @Singleton
    fun provideHttpClient(
        @PlainRetrofit plainClient: OkHttpClient,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient = plainClient.newBuilder()
        .addInterceptor(authInterceptor)
        .authenticator(tokenAuthenticator)
        .build()

    @Provides
    @Singleton
    @PlainRetrofit
    fun providePlainRetrofit(
        config: NetworkConfig,
        @PlainRetrofit client: OkHttpClient
    ): Retrofit = createRetrofit(config, client)

    @Provides
    @Singleton
    fun provideRetrofit(config: NetworkConfig, client: OkHttpClient): Retrofit =
        createRetrofit(config, client)

    // The backend may add response fields; unknown keys must not break older app versions.
    private val json = Json { ignoreUnknownKeys = true }

    private fun createRetrofit(config: NetworkConfig, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(config.baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}