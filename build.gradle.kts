plugins {
  kotlin("jvm") version "2.2.21"
  application
}

group = "me.chrisdz"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("com.google.guava:guava:33.5.0-jre")
  implementation("com.google.truth:truth:1.4.5")
  implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.21")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.10.2")
  runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:2.2.21")
  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(23)
}

tasks.test {
  useJUnitPlatform()
}

application {
  mainClass.set("MainKt")
}
