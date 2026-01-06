plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.park.core.test"
    compileSdk = 36
    testOptions.unitTests.isIncludeAndroidResources = true

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(projects.core.data)

    api(libs.junit)
    api(libs.turbine)
    api(libs.kotlinx.coroutines.test)
    api(libs.androidx.test.rules)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.androidx.ui.test.junit4)
    api(libs.androidx.ui.test.manifest)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui.tooling)
    api(libs.androidx.paging.common)
}