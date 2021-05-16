val kotlinVersion: String by project

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.tatsuro.app.kakeinote"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        // 複数人の開発者が1つのdebug.keystoreを共有し、
        // デバッグのときの不要なアプリアンインストールを回避する。
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyPassword = "android"
            keyAlias = "androiddebugkey"
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true

            // 同一端末内でデバッグビルドとリリースビルドを共存させるため、
            // デバッグビルドのパッケージ名にsuffixを付与する。
            applicationIdSuffix = ".debug"

            // アプリのバージョン名からデバッグビルドであることがわかるようにする。
            versionNameSuffix = "debug"
        }
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        // java.timeを使用するため、desugarを有効にする。
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

    // AndroidX
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.fragment:fragment-ktx:1.3.3")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")

    implementation("com.google.android.material:material:1.3.0")

    // Flipper
    debugImplementation("com.facebook.flipper:flipper:0.87.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.1")
    debugImplementation("com.facebook.flipper:flipper-leakcanary2-plugin:0.87.0")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.87.0")

    implementation("com.orhanobut:logger:2.2.0")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}