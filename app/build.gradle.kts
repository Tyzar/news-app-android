@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dev.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.assignment.newsapp"
    compileSdk = 34

    defaultConfig {
        applicationId =
            "com.assignment.newsapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.versions.kotlinCompilerExtension.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_17
        targetCompatibility =
            JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation(libs.bundles.lifecycle)
    val composeBom =
        platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.coroutine)

    ksp(libs.hilt.compiler)
    ksp(libs.hilt.androidx.compiler)
    implementation(libs.bundles.hilt)

    implementation(libs.bundles.ktor.http.client)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}