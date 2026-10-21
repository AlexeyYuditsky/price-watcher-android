package alexeyyuditsky.pricewatcher.core.products.domain

// Ozon CDN serves a resized copy when a size segment precedes the file name:
// https://ir.ozone.ru/s3/multimedia-1-b/wc500/14591428427.jpg. Other URLs are returned unchanged.
fun resizeOzonImageUrl(imageUrl: String, size: Int): String {
    require(size > 0) { "Image size must be positive" }
    val match = IMAGE_URL.matchEntire(imageUrl) ?: return imageUrl
    val (prefix, fileName) = match.destructured
    return "${prefix}wc$size/$fileName"
}

private val IMAGE_URL = Regex(
    "^(https://[a-z0-9-]+\\.ozone\\.ru/s3/[^/?#]+/)(?:wc\\d+/)?([^/?#]+)$",
    RegexOption.IGNORE_CASE
)