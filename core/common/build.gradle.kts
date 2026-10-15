plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("pricewatcher.android")
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.common"
}

dependencies {
    api(libs.kotlinx.coroutines.core)
}