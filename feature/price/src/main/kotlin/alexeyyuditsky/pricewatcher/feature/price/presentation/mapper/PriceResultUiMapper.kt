package alexeyyuditsky.pricewatcher.feature.price.presentation.mapper

import alexeyyuditsky.pricewatcher.core.products.domain.PriceResultMapper
import alexeyyuditsky.pricewatcher.core.products.domain.ProductPrice
import alexeyyuditsky.pricewatcher.feature.price.R
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceError
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceSuccess
import alexeyyuditsky.pricewatcher.feature.price.presentation.PriceUiState
import javax.inject.Inject

internal class PriceResultUiMapper @Inject constructor(
    private val priceMapper: ProductPriceUiMapper
) : PriceResultMapper<PriceUiState> {

    override fun mapSuccess(price: ProductPrice): PriceUiState = PriceSuccess(priceMapper.map(price))

    override fun mapInvalidUrl(): PriceUiState = PriceError(R.string.error_invalid_url)

    override fun mapProductUnavailable(): PriceUiState = PriceError(R.string.error_product_unavailable)

    override fun mapServerError(): PriceUiState = PriceError(R.string.error_server)

    override fun mapTimeout(): PriceUiState = PriceError(R.string.error_timeout)

    override fun mapNetworkError(): PriceUiState = PriceError(R.string.error_network)

    override fun mapInvalidResponse(): PriceUiState = PriceError(R.string.error_response)
}