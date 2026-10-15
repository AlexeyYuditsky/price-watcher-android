plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("pricewatcher.android")
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.network"
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
}