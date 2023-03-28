plugins {
    id("org.jetbrains.kotlin.kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android{
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
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
        jvmTarget = AppConfig.kotlinTarget
    }
}

dependencies{
    implementation(project(Modules.domain))
    implementation(AppDependencies.koinCore)
    implementation(AppDependencies.koinAndroidx)
}