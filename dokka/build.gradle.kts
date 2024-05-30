dependencies {
    implementation(kotlin("reflect"))

    compileOnly("org.jetbrains.dokka:dokka-core:1.9.20")
    compileOnly("org.jetbrains.dokka:analysis-kotlin-api:1.9.20")
    implementation("org.jetbrains.dokka:dokka-base:1.9.20")
    implementation("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.20")
    implementation("com.soywiz.korlibs.korte:korte-jvm:4.0.10")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
