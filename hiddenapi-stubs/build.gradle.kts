plugins {
    alias(libs.plugins.android.library)
}

android {
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    namespace = "tw.idv.palatis.xappdebug.hiddenapi"
}

dependencies {
    // implementation(libs.androidx.annotation)

    annotationProcessor("dev.rikka.tools.refine:annotation-processor:${libs.versions.refine.get()}")
    compileOnly("dev.rikka.tools.refine:annotation:${libs.versions.refine.get()}")
}
