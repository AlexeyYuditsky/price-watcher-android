package alexeyyuditsky.pricewatcher.feature.tracking.di

import alexeyyuditsky.pricewatcher.feature.tracking.presentation.mapper.TrackingUiFormatter
import android.text.format.DateFormat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
internal object TrackingUiModule {

    // Prices stay in rubles with Russian grouping; dates follow the device language.
    @Provides
    fun provideTrackingUiFormatter(): TrackingUiFormatter {
        val dateLocale = Locale.getDefault()
        return TrackingUiFormatter(
            priceLocale = Locale.forLanguageTag("ru-RU"),
            dateLocale = dateLocale,
            dateTimePattern = DateFormat.getBestDateTimePattern(dateLocale, DATE_TIME_SKELETON),
            zoneId = ZoneId.systemDefault()
        )
    }

    // Day, short month and hours:minutes in the locale's order and 12/24-hour style, without a year.
    private const val DATE_TIME_SKELETON = "dMMMjmm"
}