package dev.koga.poc_kmp_submodule

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform