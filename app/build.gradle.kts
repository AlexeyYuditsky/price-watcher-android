import java.util.Properties

plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.pricewatcher.android)
}

val backend = providers.gradleProperty("backend").getOrElse("local")
val apiBaseUrl = providers.gradleProperty("apiBaseUrl").getOrElse(
    when (backend) {
        "local" -> "http://127.0.0.1:8080/"
        "vps" -> "https://api.mypricewatcher.ru/"
        else -> error("Unknown backend '$backend'. Use local or vps.")
    }
)

// Release signing secrets live in the untracked keystore.properties; without it release stays unsigned.
val keystoreProperties = Properties().apply {
    rootProject.file("keystore.properties").takeIf { it.isFile }?.inputStream()?.use(::load)
}

fun keystoreProperty(name: String): String =
    checkNotNull(keystoreProperties.getProperty(name)?.takeIf(String::isNotBlank)) {
        "keystore.properties: $name is missing"
    }

android {
    namespace = "alexeyyuditsky.pricewatcher"

    defaultConfig {
        applicationId = "alexeyyuditsky.pricewatcher"
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        if (!keystoreProperties.isEmpty) {
            create("release") {
                storeFile = file(keystoreProperty("storeFile"))
                storePassword = keystoreProperty("storePassword")
                keyAlias = keystoreProperty("keyAlias")
                keyPassword = keystoreProperty("keyPassword")
            }
        }
    }

    buildTypes {
        // A separate application id lets debug and release builds be installed side by side.
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            signingConfig = signingConfigs.findByName("release")
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
    implementation(project(":feature:auth"))
    implementation(project(":feature:tracking"))
    implementation(project(":feature:services"))
    implementation(project(":core:auth"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:products"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}