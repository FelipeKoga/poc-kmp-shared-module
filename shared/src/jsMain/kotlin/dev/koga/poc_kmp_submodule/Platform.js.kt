package dev.koga.poc_kmp_submodule

actual fun getPlatform(): Platform {
    return object : Platform {
        override val name: String
            get() = "Js!"

    }
}

@OptIn(ExperimentalJsExport::class)
@JsExport
fun greet(name: String): String {
    return "Hello, $name"
}