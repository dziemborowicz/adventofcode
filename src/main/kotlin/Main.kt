import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

private fun solve(args: List<String>) {
  require(args.size in 2..3) { "Wrong number of args for `solve` command." }
  val year = args[0].toInt()
  val day = args[1].toInt()
  val levels = if (args.size > 2) (args[2].toInt()..args[2].toInt()) else (1..2)

  @Suppress("UNCHECKED_CAST")
  val puzzleClass = Class.forName("PuzzleY${year}D${day}").kotlin as KClass<out Puzzle>

  val puzzleCompanion = puzzleClass.companionObjectInstance
  if (puzzleCompanion != null) {
    for (level in levels) {
      for (inputProperty in puzzleCompanion::class.memberProperties) {
        val match = Regex("testInput($level(_\\d+)?)").matchEntire(inputProperty.name) ?: continue
        val label = match.groups[1]!!.value
        val answerProperty =
          puzzleCompanion::class.memberProperties.firstOrNull { it.name == "testAnswer$label" }
            ?: throw AssertionError("No testAnswer$label found.")

        val input = inputProperty.call(puzzleCompanion) as String
        val expectedAnswer = answerProperty.call(puzzleCompanion)

        timed("Test $label") {
          val puzzle = puzzleClass.createInstance()
          puzzle.parse(input)
          val actualAnswer =
            when (level) {
              1 -> puzzle.solve1()
              2 -> puzzle.solve2()
              else -> throw AssertionError()
            }
          println("Expected Answer: $expectedAnswer")
          println("Actual Answer  : $actualAnswer")
          if (actualAnswer.toString() != expectedAnswer.toString()) {
            throw AssertionError("Test $label failed.")
          }
        }
      }
    }
  }

  val session = System.getenv("ADVENT_OF_CODE_SESSION")
  require(session != null && session.isNotBlank()) { "ADVENT_OF_CODE_SESSION not set." }

  val client = Client(session)
  val input = timed("Download Input") {
    client.downloadInput(year, day).let {
      if (it.contents.length > 32) {
        println("${it.contents.take(32)}...")
      } else {
        println(it)
      }
      println("${it.contents.length} character(s)")
      println("${it.contents.count { c -> c == '\n' }} line(s)")
      println("Saved to ${it.file.absolutePath}")
      it.contents
    }
  }

  val puzzle = puzzleClass.createInstance()
  timed("Parse Input") { puzzle.parse(input) }

  for (level in levels) {
    val answer = timed("Solve $level") {
      when (level) {
        1 -> puzzle.solve1()
        2 -> puzzle.solve2()
        else -> throw AssertionError()
      }.also {
        println("Answer: $it")
      }
    }
    timed("Upload Answer $level") {
      client.uploadAnswer(year, day, level, answer).also { println(it) }
    }
  }
}

private fun clearCache(args: List<String>) {
  require(args.isEmpty()) { "Too many args for `clearCache` command." }
  Client.clearCache()
}

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    throw IllegalArgumentException("No command specified.")
  }

  when (val command = args[0]) {
    "solve" -> solve(args.drop(1))
    "clearCache" -> clearCache(args.drop(1))
    else -> throw IllegalArgumentException("Invalid command: $command")
  }
}
