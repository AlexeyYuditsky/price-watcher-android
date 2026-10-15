package alexeyyuditsky.pricewatcher.core.products.di

import alexeyyuditsky.pricewatcher.core.products.data.cloud.PriceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object PriceApiModule {

    @Provides
    @Singleton
    fun providePriceApi(retrofit: Retrofit): PriceApi = retrofit.create(PriceApi::class.java)
}