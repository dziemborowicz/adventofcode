import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "2.0.21"
  application
}

group = "me.chrisdz"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.google.guava:guava:33.3.1-jre")
  implementation("com.google.truth:truth:1.4.4")
  implementation("org.jetbrains.kotlin:kotlin-reflect:2.0.21")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.9.0")
  runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:2.0.21")
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}

application {
  mainClass.set("MainKt")
}
