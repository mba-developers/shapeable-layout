import com.android.build.gradle.internal.utils.createPublishingInfoForApp

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.mbadevelopers.shapeablelayout"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


afterEvaluate {
    // Configure the publishing block after evaluation
    (extensions.getByName("publishing") as PublishingExtension).apply {
        publications {
            create<MavenPublication>("release") {
                // Configure the publication to use the release component
                from(components["release"])

                groupId = "com.github.mba-developers" // Your group ID
                artifactId = "shapeable-layout" // Your artifact ID
                version = "1.0" // Your version
            }
        }

        repositories {
            maven {
                // Publish to a local Maven repository
                url = uri("${project.rootDir}/maven-repo")
                // Uncomment if publishing to a remote repository:
                // credentials {
                //     username = project.findProperty("mavenUser") as String? ?: System.getenv("MAVEN_USER")
                //     password = project.findProperty("mavenPassword") as String? ?: System.getenv("MAVEN_PASSWORD")
                // }
            }
        }
    }
}