// Top-level build file where you can add configuration options common to all sub-projects/modules.
// noinspection UseTomlInstead
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.49")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
    alias(libs.plugins.google.gms.google.services) apply false
}