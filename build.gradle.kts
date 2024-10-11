plugins {
    java
}

group = "dev.onechris"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.opencollab.dev/main")
}

dependencies {
    compileOnly("org.geysermc.geyser:api:2.4.3-SNAPSHOT")
    compileOnly("org.geysermc.geyser:core:2.4.3-SNAPSHOT") {
        isTransitive = false
    }
}