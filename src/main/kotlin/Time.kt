import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
inline fun <T> timed(label: String, block: () -> T): T {
  println(">>> Start: $label")
  var result: Result<T>
  val duration = measureTime { result = runCatching { block() } }
  if (result.isSuccess) {
    println("<<< Done: $label ($duration)\n")
    return result.getOrThrow()
  } else {
    println(result.exceptionOrNull())
    println("<<< FAILED! $label ($duration)\n")
    throw result.exceptionOrNull()!!
  }
}
