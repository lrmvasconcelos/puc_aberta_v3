plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation (project(Modules.core))
    implementation (project(Modules.domain))

    implementation(AppDependencies.kotlin)
    implementation(AppDependencies.koinCore)
    implementation(AppDependencies.koinAndroidx)
    implementation(AppDependencies.appCompat)
    implementation(AppDependencies.material)
    implementation(AppDependencies.constraintLayout)
    implementation(AppDependencies.maps)
    implementation(AppDependencies.localtionService)
    implementation(AppDependencies.zxing)
    implementation(AppDependencies.lottie)
    testImplementation(AppDependencies.junit)
    androidTestImplementation(AppDependencies.testExt)
    androidTestImplementation(AppDependencies.expresso)
}