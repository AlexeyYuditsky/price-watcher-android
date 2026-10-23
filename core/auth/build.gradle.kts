plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pricewatcher.android)
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.auth"
}

dependencies {
    implementation(project(":core:network"))
    api(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.tink)
    implementation(libs.tink.android)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
}