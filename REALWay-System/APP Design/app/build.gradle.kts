import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.parking_navigation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.parking_navigation_wifi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit.core) // ensure this matches the TOML entry
    implementation(libs.retrofit.gson) // ensure this matches the TOML entry
    implementation(libs.usbserial)
    implementation(libs.tinder.scarlet)
    implementation(libs.scarlet.websocket.okhttp)
    implementation(libs.scarlet.message.adapter.moshi)
    implementation(libs.scarlet.lifecycle.android)
    implementation(libs.moshi)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.stream.adapter.coroutines)
    implementation(libs.scarlet.lifecycle.android)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.tinder.scarlet:scarlet:0.1.12")
    implementation(libs.scarlet.websocket.okhttp)
    implementation(libs.message.adapter.gson)
    implementation("io.ktor:ktor-client-android:1.6.4")
    implementation(libs.ktor.client.websockets)
    //implementation(libs.scarlet.stream.adapter.coroutines)
    implementation(libs.kotlinx.coroutines.core.v16x)
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("androidx.compose.material:material-icons-core:1.7.7") // check for the latest version suitable for your setup
    implementation("androidx.compose.material:material-icons-extended:1.7.7")  // check for the latest version suitable for your setup


}