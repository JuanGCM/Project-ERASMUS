import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
	kotlin("plugin.allopen") version "1.5.31"
	id("org.jetbrains.dokka") version "1.5.31"
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.Embeddable")
	annotation("javax.persistence.MappedSuperclass")
}

group = "cnos.fap"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	//We use it to define the entities and the type of relations between classes.
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	//We use it for the security and the application has a login.
	implementation("org.springframework.boot:spring-boot-starter-security")
	//Used for validation of some class parameters.
	implementation("org.springframework.boot:spring-boot-starter-validation")
	//Used to define a web application.
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	//We use it for the implementation of the token and its various uses.
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation (group= "io.jsonwebtoken", name= "jjwt-api", version="0.11.2")
	runtimeOnly (group= "io.jsonwebtoken", name= "jjwt-jackson", version= "0.11.2")
	runtimeOnly (group= "io.jsonwebtoken", name= "jjwt-impl", version= "0.11.2")
	implementation(group="org.springframework.security", name= "spring-security-crypto", version= "5.4.5")
	implementation(group="io.springfox", name= "springfox-swagger-ui", version= "2.9.2")
	implementation(group="io.springfox", name= "springfox-swagger2", version= "2.9.2")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
