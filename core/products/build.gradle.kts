plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("pricewatcher.android")
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.products"
}

dependencies {
    implementation(project(":core:network"))
    implementation(libs.retrofit)
    implementation(libs.gson)
}