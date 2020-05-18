object Versions {
    const val kotlin = "1.3.71"
    const val android_gradle_plugin = "3.5.0"

    const val compile_sdk_version = 29
    const val build_tools_version = "29.0.2"
    const val min_sdk_version = 21
    const val target_sdk_version = 29

    const val appcompat = "1.1.0"
    const val androidx_test = "1.2.0"
    const val core_ktx = "1.1.0"
    const val espresso = "3.2.0"
    const val junit = "4.12"
    const val constraint_layout = "1.1.3"
    const val material = "1.2.0-alpha02"

    const val ktlint = "0.36.0"
}

object ClassPaths {
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Plugins {
    const val androidApp = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinExt = "android.extensions"
    const val kotlinApt = "kapt"
}

object Deps {
    const val support_app_compat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val support_design = "com.google.android.material:material:${Versions.material}"
    const val support_core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val atsl_runner = "androidx.test:runner:${Versions.androidx_test}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val junit = "junit:junit:${Versions.junit}"

    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
}
