package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class RefreshProductUseCase @Inject constructor(
    private val repository: TrackingRepository
) {

    suspend operator fun invoke(productId: String): TrackingResult = repository.refresh(productId)
}