import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
    jacoco
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

val tmdbToken: String = localProperties["token"] as String? ?: ""

android {
    namespace = "com.example.cinemax"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cinemax"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "TMDB_BEARER_TOKEN", "\"$tmdbToken\"")
            buildConfigField("String", "TMDB_CERT_PIN", "\"sha256/f78NVAesYtdZ9OGSbK7VtGQkSIVykh3DnduuLIJHMu4=\"")
        }
        release {
            buildConfigField("String", "TMDB_BEARER_TOKEN", "\"$tmdbToken\"")
            buildConfigField("String", "TMDB_CERT_PIN", "\"sha256/f78NVAesYtdZ9OGSbK7VtGQkSIVykh3DnduuLIJHMu4=\"")
            isMinifyEnabled = true
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
        buildConfig = true
    }
    dynamicFeatures += setOf(":wishlistfeature")
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
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //splash
    implementation(libs.androidx.core.splashscreen)

    //navigation
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.compose)

    //foundation
    implementation(libs.androidx.foundation)

    //icon extended
    implementation(libs.androidx.material.icons.extended)

    //datastore
    implementation(libs.datastore.preferences)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    //chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    //icon extended
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.compose.material)

    //paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.paging.compose)

    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
    testImplementation(libs.room.testing)

    //firebase
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    //lottie
    implementation(libs.lottie.compose)

    //coil
    implementation(libs.coil.compose)

    //encrypt
    implementation(libs.security.crypto)
    implementation(libs.sqlcipher)

    implementation(libs.youtube.player)
    implementation(libs.accompanist.placeholder.material)

    debugImplementation(libs.leakcanary)
    testImplementation(libs.mockk)

    implementation(project(":core"))
}