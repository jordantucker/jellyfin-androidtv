plugins {
	id("com.android.application")
	id("kotlin-android")
	id("kotlin-android-extensions")
}

android {
	compileSdkVersion(29)
	ndkVersion = "21.0.6113669"

	defaultConfig {
		// Android version targets
		minSdkVersion(21)
		targetSdkVersion(29)

		// Release version
		versionCode = 905
		versionName = "0.11.0"
	}

	compileOptions {
		// Use Java 1.8 features
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}

	kotlinOptions {
		jvmTarget = compileOptions.targetCompatibility.toString()
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
			resValue("string", "app_name", "@string/app_name_release")
		}

		getByName("debug") {
			// Use different application id to run release and debug at the same time
			applicationIdSuffix = ".debug"
			resValue("string", "app_name", "@string/app_name_debug")
		}
	}

	applicationVariants.all {
		val variant = this
		variant.outputs.all {
			val output = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
			output.outputFileName = output.outputFileName
				.replace("app-", "jellyfin-androidtv_")
				.replace(".apk", "_${variant.versionName}.apk")
		}
	}
}

dependencies {
	// Jellyfin
	implementation("com.github.jellyfin.jellyfin-apiclient-java:android:master-SNAPSHOT")

	// Kotlin
	implementation(kotlin("stdlib-jdk8"))
	val kotlinCoroutinesVersion = "1.3.3"
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion")

	// Android(x)
	implementation("androidx.core:core-ktx:1.2.0")
	implementation("androidx.fragment:fragment-ktx:1.2.4")
	val androidxLeanbackVersion = "1.1.0-alpha03"
	implementation("androidx.leanback:leanback:$androidxLeanbackVersion")
	implementation("androidx.leanback:leanback-preference:$androidxLeanbackVersion")
	implementation("androidx.preference:preference:1.1.1")
	implementation("androidx.appcompat:appcompat:1.1.0")
	implementation("androidx.tvprovider:tvprovider:1.0.0")
	implementation("androidx.palette:palette:1.0.0")
	implementation("androidx.constraintlayout:constraintlayout:1.1.3")
	implementation("androidx.recyclerview:recyclerview:1.1.0")
	implementation("com.google.android:flexbox:2.0.1")

	// Media players
	implementation("com.amazon.android:exoplayer:2.11.3")
	implementation("org.videolan.android:libvlc-all:3.2.5")

	// Image utility
	implementation("com.squareup.picasso:picasso:2.3.2")
	implementation("com.github.bumptech.glide:glide:3.7.0")
	implementation("com.flaviofaria:kenburnsview:1.0.6")

	// HTTP utility
	// NOTE: This is used by Picasso through reflection and can cause weird caching issues if removed!
	val okhttpVersion = "2.7.5"
	implementation("com.squareup.okhttp:okhttp:$okhttpVersion")
	implementation("com.squareup.okhttp:okhttp-urlconnection:$okhttpVersion")

	// Crash Reporting
	val acraVersion = "5.4.0"
	implementation("ch.acra:acra-http:$acraVersion")
	implementation("ch.acra:acra-dialog:$acraVersion")
	implementation("ch.acra:acra-limiter:$acraVersion")

	// Testing
	testImplementation("junit:junit:4.12")
	testImplementation("org.mockito:mockito-core:3.2.4")
}
