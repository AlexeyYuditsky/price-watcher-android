import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension> {
                configureAndroidDefaults(this, libs)
                defaultConfig.targetSdk = libs.findVersion("androidTargetSdk")
                    .get().requiredVersion.toInt()
            }
        }
        pluginManager.withPlugin("com.android.library") {
            extensions.configure<LibraryExtension> {
                configureAndroidDefaults(this, libs)
            }
        }
        pluginManager.withPlugin("org.jetbrains.kotlin.plugin.compose") {
            extensions.configure<CommonExtension> {
                buildFeatures.compose = true
            }
            dependencies.add(
                "implementation",
                dependencies.platform(libs.findLibrary("androidx-compose-bom").get())
            )
            dependencies.add("implementation", libs.findLibrary("androidx-compose-ui").get())
            dependencies.add(
                "implementation",
                libs.findLibrary("androidx-compose-ui-tooling-preview").get()
            )
            dependencies.add(
                "debugImplementation",
                libs.findLibrary("androidx-compose-ui-tooling").get()
            )
        }
        pluginManager.withPlugin("com.google.dagger.hilt.android") {
            dependencies.add("implementation", libs.findLibrary("hilt-android").get())
            pluginManager.withPlugin("com.google.devtools.ksp") {
                dependencies.add("ksp", libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}

private fun configureAndroidDefaults(android: CommonExtension, libs: VersionCatalog) {
    android.compileSdk {
        version = release(libs.findVersion("androidCompileSdk").get().requiredVersion.toInt())
    }
    android.defaultConfig.minSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
    val jvmTarget = JavaVersion.toVersion(libs.findVersion("jvmTarget").get().requiredVersion)
    android.compileOptions.apply {
        sourceCompatibility = jvmTarget
        targetCompatibility = jvmTarget
    }
}