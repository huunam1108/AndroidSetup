import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

plugins {
    id(Plugins.androidApp)
    id(Plugins.firebase_distribution)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
    id(Plugins.detekt).version(Versions.detekt)
}

buildscript {
    apply(from = "ktlint.gradle.kts")
    apply(from = "../distribution/distribution.gradle.kts")
}

// Load signing configs for release
val keystorePropertiesFile = rootProject.file("distribution/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdkVersion(Versions.compile_sdk_version)
    buildToolsVersion(Versions.build_tools_version)
    defaultConfig {
        applicationId = "com.namnh.androidsetup"
        minSdkVersion(Versions.min_sdk_version)
        targetSdkVersion(Versions.target_sdk_version)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles(file("proguard-rules.pro"))
    }

    // Add release keystore info
    // ref: https://developer.android.com/studio/publish/app-signing#secure-shared-keystore
    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["storeFile"] as Any)
            storePassword = keystoreProperties["storePassword"] as? String
            keyAlias = keystoreProperties["keyAlias"] as? String
            keyPassword = keystoreProperties["keyPassword"] as? String
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro")
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions("default")
    productFlavors {
        create("develop") {
            manifestPlaceholders = mapOf("applicationName" to "@string/app_name_dev")
            // setup release note and group of testers
            // ref: https://firebase.google.com/docs/app-distribution/android/distribute-gradle?authuser=1#step_3_configure_your_distribution_properties
            firebaseAppDistribution {
                appId = "1:578593782165:android:a7777d0c48a2b35aac5e9c"
                releaseNotesFile = "app/src/develop/release_notes.txt"
                groups = "my-testers"
                serviceCredentialsFile = "distribution/androidsetup-private-key.json"
            }
        }
        // other flavor
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

androidExtensions {
    isExperimental = true
}

detekt {
    config = files("$rootDir/config/detekt/detekt.yml")
    input = files("src/main/java")
    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = false // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = false // similar to the console output, contains issue signature to manually edit baseline files
    }
}

dependencies {
    implementation(Deps.kotlin_stdlib)
    implementation(Deps.support_app_compat)
    implementation(Deps.support_core_ktx)
    implementation(Deps.support_design)
    implementation(Deps.constraint_layout)
    testImplementation(Deps.junit)
    testImplementation(Deps.atsl_runner)
    androidTestImplementation(Deps.espresso_core)
}
