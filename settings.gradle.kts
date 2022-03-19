pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "1.6.10"
    }
}

rootProject.name = "ktor-demo-app-b"
include("app_base")
project(":app_base").projectDir = File("../ktor_demo_app_base")
