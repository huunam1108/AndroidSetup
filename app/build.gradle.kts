import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidApp)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
    id(Plugins.detekt).version(Versions.detekt)
}

buildscript {
    apply(from = "ktlint.gradle.kts")
}

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
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro")
            )
        }
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
