import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") apply false
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

val projectGroup: String by project
val applicationVersion: String by project
allprojects {
	group = projectGroup
	version = applicationVersion

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		testImplementation("org.junit.jupiter:junit-jupiter-api:${property("junitJupiterVersion")}")
		testImplementation("org.junit.jupiter:junit-jupiter-params:${property("junitJupiterVersion")}")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${property("junitJupiterVersion")}")
	}

	tasks.getByName("jar") {
		enabled = true
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
