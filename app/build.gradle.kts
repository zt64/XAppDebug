import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.refine)
}

kotlin {
    jvmToolchain(17)
}

android {
    signingConfigs {
        create("release") {
            // for custom release signing keys
            // 1. create `tw.idv.palatis.XAppDebug.signing.properties` under `~/.gradle`
            // 2. populate with these values:
            //   - STORE_FILE=/path/to/keystore.jks
            //   - STORE_PASSWORD=super_secret_password
            //   - KEY_ALIAS=your key alias
            //   - KEY_PASSWORD=super_secret_password

            val propsPath = System.getProperty("user.home") + "/.gradle/tw.idv.palatis.XAppDebug.signing.properties"
            val propsFile = file(propsPath)
            if (propsFile.canRead()) {
                val props =
                    Properties().apply {
                        load(FileInputStream(propsFile))
                    }
                storeFile = file(props["STORE_FILE"] as String)
                storePassword = props["STORE_PASSWORD"] as String
                keyAlias = props["KEY_ALIAS"] as String
                keyPassword = props["KEY_PASSWORD"] as String
            } else {
                println("$propsPath is missing")
            }
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }

    namespace = "tw.idv.palatis.xappdebug2"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        versionCode = 100006
        versionName = "1.0.6"
        project.ext.set("archivesBaseName", "$applicationId-$versionName-$versionCode")
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-dbg"
        }
        release {
            if (signingConfigs.findByName("release")?.storeFile != null) {
                signingConfig = signingConfigs.getByName("release")
            }

            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

// android.applicationVariants.all {
//     val variant = this
//     val version = if (!variant.versionName.isNullOrEmpty()) "${'$'}{variant.versionName}-${'$'}{variant.versionCode}" else "${'$'}{variant.versionCode}"
//     val archiveBuildTask =
//         tasks.register<Copy>("archive${'$'}{variant.name.capitalize()}Apk") {
//             group = "archive"
//             description = "Assembles and archives apk and its proguard mapping for the ${'$'}{variant.name.capitalize()} build"
//             dependsOn(variant.assembleProvider)
//             variant.outputs.forEach { output ->
//                 from(output.outputFile)
//                 rename(output.outputFile.name, "${'$'}{variant.applicationId}-${'$'}version-${'$'}{output.outputFile.name}")
//             }
//             variant.mappingFile?.let { mapping ->
//                 from(mapping)
//                 rename(mapping.name, "${'$'}{variant.applicationId}-${'$'}version-${'$'}{mapping.name}")
//             }
//             into("${'$'}rootDir/archive/${'$'}project.name/")
//         }
//     variant.assembleProvider.configure { finalizedBy(archiveBuildTask) }
// }

dependencies {
    // implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.refine.runtime)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)
    implementation(libs.appcompat)
    implementation(libs.preference)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.lifecycle.extensions)
    compileOnly(libs.xposed.api)
    compileOnly(libs.xposed.api.sources)
}
