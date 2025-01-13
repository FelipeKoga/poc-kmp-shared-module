package dev.koga.poc_kmp_submodule

object Analytics {
    fun logEvent(event: String, params: Map<String, Any>) {
        println("Logging event $event with params $params")
    }
}