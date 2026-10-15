package alexeyyuditsky.pricewatcher.core.products.domain

data class PriceSuccessResult(
    private val price: ProductPrice
) : PriceResult {

    override fun <T> map(mapper: PriceResultMapper<T>): T = mapper.mapSuccess(price)
}