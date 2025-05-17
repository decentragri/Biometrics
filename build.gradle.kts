buildscript {
    val agp_version by extra("8.8.2")
}
plugins {
    id("com.android.application") version "8.8.2" apply false
    id("com.android.library") version "8.6.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
}
