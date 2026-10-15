package alexeyyuditsky.pricewatcher.core.products.domain

import javax.inject.Inject

class GetProductPriceUseCase @Inject constructor(
    private val repository: PriceRepository
) {

    suspend operator fun invoke(input: String): PriceResult {
        val url = try {
            extractOzonProductUrl(input)
        } catch (_: IllegalArgumentException) {
            return PriceFailure.INVALID_URL
        }
        return repository.getPrice(url)
    }
}