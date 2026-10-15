package alexeyyuditsky.pricewatcher.di

import alexeyyuditsky.pricewatcher.BuildConfig
import alexeyyuditsky.pricewatcher.core.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AppNetworkModule {

    @Provides
    fun provideNetworkConfig(): NetworkConfig = NetworkConfig(baseUrl = BuildConfig.API_BASE_URL)
}