package dev.koga.poc_kmp_submodule

actual fun getPlatform(): Platform {
    return object : Platform {
        override val name: String
            get() = "Js!"

    }
}