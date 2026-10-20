package alexeyyuditsky.pricewatcher.core.products.di

import alexeyyuditsky.pricewatcher.core.products.data.TrackingRepositoryImpl
import alexeyyuditsky.pricewatcher.core.products.domain.TrackingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTrackingRepository(implementation: TrackingRepositoryImpl): TrackingRepository
}