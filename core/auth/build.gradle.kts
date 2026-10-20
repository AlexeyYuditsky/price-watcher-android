plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("pricewatcher.android")
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.auth"
}

dependencies {
    implementation(project(":core:network"))
    api(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.retrofit)
    implementation(libs.gson)
}