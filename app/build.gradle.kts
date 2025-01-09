plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services") // Plugin pentru Firebase
}

android {
    namespace = "com.example.plantapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.plantapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        resValue("string", "facebook_app_id", "\"${project.properties["FACEBOOK_APP_ID"]}\"")

        buildConfigField("String", "GOOGLE_REQUEST_TOKEN", "\"${project.properties["GOOGLE_REQUEST_TOKEN"]}\"")
        buildConfigField("String", "FACEBOOK_CLIENT_TOKEN", "\"${project.properties["FACEBOOK_CLIENT_TOKEN"]}\"")
        buildConfigField("String", "FACEBOOK_APP_ID", "\"${project.properties["FACEBOOK_APP_ID"]}\"")
        buildConfigField("String", "API_URL", "\"${project.properties["API_URL"]}\"")
    }
    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.tracing.perfetto.handshake)

    // Firebase BOM (recomandat)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.8.8")

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation(libs.androidx.espresso.core)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.compose)

    // Biblioteca pentru Google Sign-In
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    // Alte servicii Firebase (opțional)
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")

    // Dagger hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Facebook
    implementation("com.facebook.android:facebook-android-sdk:16.0.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.3") // Versiunea poate varia


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

apply(plugin = "com.google.gms.google-services") // Aplică plugin-ul Google Services
