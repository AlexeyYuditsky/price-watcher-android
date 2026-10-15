package alexeyyuditsky.pricewatcher.core.products.domain

import java.net.URI

fun extractOzonProductUrl(text: String): String =
    URL_REGEX.findAll(text).firstNotNullOfOrNull { match ->
        runCatching {
            val candidate = match.value.trimEnd(',', '.', ';', '!', '?', ')', ']', '}', '»', '”')
            val uri = URI(candidate)
            require(
                uri.scheme.equals("https", ignoreCase = true) &&
                        uri.host?.lowercase() in setOf("ozon.ru", "www.ozon.ru") &&
                        uri.port in setOf(-1, 443) && uri.rawUserInfo == null
            )
            val path = uri.rawPath.orEmpty()
            when {
                PRODUCT_PATH.matches(path) -> "https://www.ozon.ru${path.trimEnd('/')}/"
                SHORT_PATH.matches(path) -> "https://ozon.ru${path.trimEnd('/')}"
                else -> throw IllegalArgumentException("Expected an Ozon product or short URL")
            }
        }.getOrNull()
    } ?: throw IllegalArgumentException("Ozon product URL was not found")

private val URL_REGEX = Regex("https://[^\\s<>\"']+", RegexOption.IGNORE_CASE)
private val PRODUCT_PATH = Regex("^/product/[^/]*\\d{6,}/?$")
private val SHORT_PATH = Regex("^/t/[A-Za-z0-9]+/?$")