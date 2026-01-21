plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.park.core.domain"
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
    api(projects.core.model)
    implementation(libs.javax.inject)
    implementation(libs.androidx.paging.compose)
}