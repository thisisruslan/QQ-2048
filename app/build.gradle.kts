plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
//    compileSdkVersion = 32
//    buildToolsVersion = "30.0.3"
    compileSdk = 32

    defaultConfig {
        applicationId = "uz.gita.my2048game"
        minSdk = 21
        targetSdk = 32
        versionCode = 2
        versionName = "1.0.1"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
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
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    /**
     * Navigation
     * */
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    /**
     * ViewModelProviders
     * */
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    /**
     *  lifeCycle
     */
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    /**
     *  kotlinx.coroutines
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    /**
     * Better Logging In Android Using Timber
     **/
    implementation ("com.jakewharton.timber:timber:5.0.1")


    /**
     *   viewBinding
     */
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6")
}