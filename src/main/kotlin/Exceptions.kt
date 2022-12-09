fun fail(message: Any? = null): Nothing = error(message ?: "Unknown error.")

fun error(): Nothing = error("Unknown error.")
