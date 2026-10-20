package alexeyyuditsky.pricewatcher.core.products.di

import alexeyyuditsky.pricewatcher.core.products.data.cloud.TrackingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal object TrackingApiModule {

    @Provides
    @Singleton
    fun provideTrackingApi(retrofit: Retrofit): TrackingApi = retrofit.create(TrackingApi::class.java)
}