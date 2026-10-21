plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.pricewatcher.android)
}

android {
    namespace = "alexeyyuditsky.pricewatcher.feature.services"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.compose.material3)
}