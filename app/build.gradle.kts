plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.mikkipastel.gastracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mikkipastel.gastracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // For Glance support
    implementation(libs.androidx.glance)
    // For AppWidgets support
    implementation(libs.androidx.glance.appwidget)
    // Fixed issue with Glance
    implementation(libs.androidx.glance.appwidget.proto)
    implementation(libs.androidx.glance.appwidget.external.protobuf)

    // For call API
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.koin.android)

    // Crashlytics
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
}