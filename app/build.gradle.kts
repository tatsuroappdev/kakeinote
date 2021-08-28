plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.tatsuro.app.kakeinote"
        minSdk = 21
        targetSdk = 31
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

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1-native-mt")

    // AndroidX
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.fragment:fragment-ktx:1.4.0-alpha07")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.preference:preference:1.1.1")

    // AndroidX Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")

    // AndroidX Room
    implementation("androidx.room:room-runtime:2.3.0")
    implementation("androidx.room:room-ktx:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    implementation("com.google.android.material:material:1.4.0")

    // Flipper
    debugImplementation("com.facebook.flipper:flipper:0.104.0")
    debugImplementation("com.facebook.soloader:soloader:0.10.1")
    debugImplementation("com.facebook.flipper:flipper-leakcanary2-plugin:0.104.0")
    releaseImplementation("com.facebook.flipper:flipper-noop:0.104.0")

    implementation("com.orhanobut:logger:2.2.0")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
