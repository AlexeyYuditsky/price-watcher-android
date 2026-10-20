package alexeyyuditsky.pricewatcher.core.auth.di

import alexeyyuditsky.pricewatcher.core.auth.data.AuthRepositoryImpl
import alexeyyuditsky.pricewatcher.core.auth.data.SessionTokenProvider
import alexeyyuditsky.pricewatcher.core.auth.domain.AuthRepository
import alexeyyuditsky.pricewatcher.core.network.AuthTokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(implementation: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindAuthTokenProvider(implementation: SessionTokenProvider): AuthTokenProvider
}