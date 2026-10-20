package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class GetTrackedProductsUseCase @Inject constructor(
    private val repository: TrackingRepository
) {

    suspend operator fun invoke(): TrackingResult = repository.products()
}