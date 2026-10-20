package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class TrackProductUseCase @Inject constructor(
    private val repository: TrackingRepository
) {

    suspend operator fun invoke(input: String): TrackingResult {
        val url = try {
            extractOzonProductUrl(input)
        } catch (_: IllegalArgumentException) {
            return TrackingFailure.INVALID_URL
        }
        return repository.track(url)
    }
}