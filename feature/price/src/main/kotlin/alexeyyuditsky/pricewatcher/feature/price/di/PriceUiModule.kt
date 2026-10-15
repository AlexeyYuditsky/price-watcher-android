package alexeyyuditsky.pricewatcher.feature.price.di

import alexeyyuditsky.pricewatcher.feature.price.presentation.mapper.ProductPriceUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
internal object PriceUiModule {

    @Provides
    fun provideProductPriceUiMapper(): ProductPriceUiMapper = ProductPriceUiMapper(
        locale = Locale.forLanguageTag("ru-RU"),
        zoneId = ZoneId.systemDefault()
    )
}