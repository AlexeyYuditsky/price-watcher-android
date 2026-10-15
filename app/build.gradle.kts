plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("pricewatcher.android")
}

val backend = providers.gradleProperty("backend").getOrElse("local")
val apiBaseUrl = providers.gradleProperty("apiBaseUrl").getOrElse(
    when (backend) {
        "local" -> "http://127.0.0.1:8080/"
        "vps" -> "http://95.163.19.231:8080/"
        else -> error("Unknown backend '$backend'. Use local or vps.")
    }
)

android {
    namespace = "alexeyyuditsky.pricewatcher"

    defaultConfig {
        applicationId = "alexeyyuditsky.pricewatcher"
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":feature:price"))
    implementation(project(":feature:services"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:products"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}