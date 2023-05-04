/**
 * @author  Ridwan Ismail <iwanz@pm.me>
 * @version 1
 * @since   Tuesday, 15 Nov 2022
 */

object Versions {
    // Kotlin
    const val kotlin = "1.7.0"

    // Android Component
    const val core = "1.8.0"
    const val appcompat = "1.4.2"
    const val material = "1.6.1"
    const val constraintLayout = "2.1.4"
    const val livedataKtx = "2.5.0"
    const val viewModelKtx = "2.5.0"
    const val navigationFragKtx = "2.5.0"
    const val navigationUiKtx = "2.5.0"
    const val paging = "3.1.1"

    // Dependency Injection
    const val hilt = "2.43.2"

    // Network
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val okhttp = "4.9.0"
    const val loggingInterceptor = "4.9.0"

    // Asynchronous
    const val coroutines = "1.6.1"

    // Image Loader
    const val glide = "4.13.1"

    // Local Storage
    const val dataStore = "1.0.0"

    // Location
    const val locationService = "20.0.0"

    // Camera
    const val camerax = "1.1.0-beta02"

    // Test
    const val jUnit = "4.13.2"
    const val extJUnit = "1.1.3"
    const val espresso = "3.4.0"
}

object Dependencies {
    // Android Component
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedataKtx}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
    const val navigationFragKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragKtx}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"

    // Dependency Injection
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAndroidTest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"

    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    // Asynchronous
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    // Image Loader
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Local Storage
    const val dataStore = "androidx.datastore:datastore-preferences:${Versions.dataStore}"

    // Location
    const val locationService =
        "com.google.android.gms:play-services-location:${Versions.locationService}"

    // Camera
    const val camera = "androidx.camera:camera-camera2:${Versions.camerax}"
    const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.camerax}"
    const val cameraView = "androidx.camera:camera-view:${Versions.camerax}"

    // Test
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val extJUnit = "androidx.test.ext:junit:${Versions.extJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}


