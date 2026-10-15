plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidConventions") {
            id = "pricewatcher.android"
            implementationClass = "AndroidConventionPlugin"
        }
    }
}