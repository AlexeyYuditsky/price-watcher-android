package alexeyyuditsky.pricewatcher.core.network.di

import alexeyyuditsky.pricewatcher.core.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(150, TimeUnit.SECONDS)
        .callTimeout(180, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(config: NetworkConfig, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(config.baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}