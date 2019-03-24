buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.21")
        classpath("com.google.gms:google-services:4.2.0")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }

    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class) {
            options.compilerArgs.addAll(arrayOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}


tasks {
    withType(Delete::class.java) {
        delete(rootProject.buildDir)
    }
}