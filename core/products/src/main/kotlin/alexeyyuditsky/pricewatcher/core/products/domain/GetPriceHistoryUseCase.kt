package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class GetPriceHistoryUseCase @Inject constructor(
    private val repository: TrackingRepository
) {

    suspend operator fun invoke(productId: String): TrackingResult = repository.history(productId)
}