package alexeyyuditsky.pricewatcher.core.products.di

import alexeyyuditsky.pricewatcher.core.products.data.PriceRepositoryImpl
import alexeyyuditsky.pricewatcher.core.products.domain.PriceRepository
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
    abstract fun bindPriceRepository(implementation: PriceRepositoryImpl): PriceRepository
}