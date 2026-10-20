package alexeyyuditsky.pricewatcher.feature.tracking.di

import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingUiFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
internal object TrackingUiModule {

    @Provides
    fun provideTrackingUiFormatter(): TrackingUiFormatter = TrackingUiFormatter(
        locale = Locale.forLanguageTag("ru-RU"),
        zoneId = ZoneId.systemDefault()
    )
}