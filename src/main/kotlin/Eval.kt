import javax.script.ScriptEngineManager

inline fun <reified T> eval(script: String): T =
  ScriptEngineManager().getEngineByExtension("kts").eval(script) as T

fun evalDouble(script: String): Double = eval(script)

fun evalInt(script: String): Int = eval(script)

fun evalLong(script: String): Long = eval(script)

fun evalString(script: String): String = eval(script)

fun <T> evalAll(scripts: List<String>): List<T> = evalAll(context = "", scripts)

fun <T> evalAll(context: String, scripts: List<String>): List<T> {
  val list = scripts.map { "run { $it }" }.joinToString(", ")
  val script = "$context\n\nlistOf($list)"
  @Suppress("UNCHECKED_CAST")
  return ScriptEngineManager().getEngineByExtension("kts").eval(script) as List<T>
}

fun evalDoubles(scripts: List<String>): List<Double> = evalAll(context = "", scripts)

fun evalDoubles(context: String, scripts: List<String>): List<Double> = evalAll(context, scripts)

fun evalInts(scripts: List<String>): List<Int> = evalAll(context = "", scripts)

fun evalInts(context: String, scripts: List<String>): List<Int> = evalAll(context, scripts)

fun evalLongs(scripts: List<String>): List<Long> = evalAll(context = "", scripts)

fun evalLongs(context: String, scripts: List<String>): List<Long> = evalAll(context, scripts)

fun evalStrings(scripts: List<String>): List<String> = evalAll(context = "", scripts)

fun evalStrings(context: String, scripts: List<String>): List<String> = evalAll(context, scripts)
