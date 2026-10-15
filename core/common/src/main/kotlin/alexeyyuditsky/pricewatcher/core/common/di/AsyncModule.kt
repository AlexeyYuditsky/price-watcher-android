package alexeyyuditsky.pricewatcher.core.common.di

import alexeyyuditsky.pricewatcher.core.common.DefaultRunAsync
import alexeyyuditsky.pricewatcher.core.common.IoDispatcher
import alexeyyuditsky.pricewatcher.core.common.RunAsync
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object AsyncModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideRunAsync(implementation: DefaultRunAsync): RunAsync = implementation
}