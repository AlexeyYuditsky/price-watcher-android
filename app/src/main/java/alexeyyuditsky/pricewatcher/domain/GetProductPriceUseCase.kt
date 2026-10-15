package alexeyyuditsky.pricewatcher.domain

import javax.inject.Inject

class GetProductPriceUseCase @Inject constructor(private val repository: PriceRepository) {
    suspend operator fun invoke(input: String): ProductPrice =
        repository.getPrice(extractOzonProductUrl(input))
}