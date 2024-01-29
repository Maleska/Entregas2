plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.consulsoftware.entregas"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.consulsoftware.entregas"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth:22.3.0")
    //implementation("com.squareup.retrofit2:retrofit:2.4.0")
    //implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    //implementation("androidx.test.espresso:espresso-core:3.3.0")
    implementation("com.android.volley:volley:1.2.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //implementation("com.github.ok2c.hc4.android:httpclient-android:0.1.0")
    /*implementation("org.apache.httpcomponents:httpcore:4.4.1")
    implementation("org.apache.httpcomponents:httpclient:4.5")*/
    implementation("com.android.volley:volley:1.1.1")

    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-database-ktx")
}

