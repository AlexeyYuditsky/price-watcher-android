package alexeyyuditsky.pricewatcher.core.auth.di

import alexeyyuditsky.pricewatcher.core.auth.data.cloud.AuthApi
import alexeyyuditsky.pricewatcher.core.network.PlainRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object AuthApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(@PlainRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}