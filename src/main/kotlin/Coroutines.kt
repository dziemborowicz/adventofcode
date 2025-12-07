import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import kotlinx.coroutines.CoroutineScope

fun launchUnscoped(block: suspend CoroutineScope.() -> Unit) {
  block.startCoroutine(object : CoroutineScope {
    override val coroutineContext = EmptyCoroutineContext
  }, object : Continuation<Unit> {
    override val context = EmptyCoroutineContext
    override fun resumeWith(result: Result<Unit>) = Unit
  })
}
