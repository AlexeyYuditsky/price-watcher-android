plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.pricewatcher.android)
}

android {
    namespace = "alexeyyuditsky.pricewatcher.core.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}