import Client.AnswerResult
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties

private fun solve(args: List<String>) {
  require(args.size in 2..3) { "Wrong number of args for `solve` command." }
  val year = args[0].toInt()
  val day = args[1].toInt()
  val levels = if (args.size > 2) (args[2].toInt()..args[2].toInt()) else (1..2)
  return solve(year, day, levels)
}

private fun solve(year: Int, day: Int, levels: IntRange = (1..2)) {
  val puzzleName = "Y${year}D${day}"

  @Suppress("UNCHECKED_CAST")
  val puzzleClass = Class.forName("Puzzle$puzzleName").kotlin as KClass<out Puzzle>

  val puzzleCompanion = puzzleClass.companionObjectInstance
  if (puzzleCompanion != null) {
    for (testMethod in puzzleCompanion::class.functions.filter { it.name.startsWith("test") }) {
      timed("$puzzleName ${testMethod.name}") {
        try {
          testMethod.call(puzzleCompanion)
        } catch (e: InvocationTargetException) {
          throw e.cause ?: e
        }
      }
    }

    for (level in levels) {
      for (inputProperty in puzzleCompanion::class.memberProperties) {
        val match = Regex("testInput($level(_.+)?)").matchEntire(inputProperty.name) ?: continue
        val label = match.groups[1]!!.value
        val answerProperty =
          puzzleCompanion::class.memberProperties.firstOrNull { it.name == "testAnswer$label" }
            ?: fail("No testAnswer$label found.")

        val input = inputProperty.call(puzzleCompanion) as String
        val expectedAnswer = answerProperty.call(puzzleCompanion)

        timed("$puzzleName testInput$label") {
          val puzzle = puzzleClass.createInstance()
          puzzle.parse(input)
          val actualAnswer =
            when (level) {
              1 -> puzzle.solve1()
              2 -> puzzle.solve2()
              else -> fail()
            }
          println("Expected Answer: $expectedAnswer")
          println("Actual Answer  : $actualAnswer")
          if (actualAnswer.toString() != expectedAnswer.toString()) {
            fail("Test $label failed.")
          }
        }
      }
    }
  }

  val session = System.getenv("ADVENT_OF_CODE_SESSION")
  require(session != null && session.isNotBlank()) { "ADVENT_OF_CODE_SESSION not set." }

  val client = Client(session)
  val input = timed("$puzzleName  Download Input") {
    client.downloadInput(year, day).let {
      val lines = it.contents.lines()
      lines.take(10).forEach { line ->
        print(line.take(100))
        if (line.length > 100) print("...")
        println()
      }
      if (lines.size > 10) println("...")
      println("${it.contents.length} character(s)")
      println("${it.contents.count { c -> c == '\n' }} line(s)")
      println("Saved to ${it.file.absolutePath}")
      it.contents
    }
  }

  val puzzle = puzzleClass.createInstance()
  timed("$puzzleName Parse Input") { puzzle.parse(input) }

  for (level in levels) {
    val answer = timed("$puzzleName Solve $level") {
      when (level) {
        1 -> puzzle.solve1()
        2 -> puzzle.solve2()
        else -> fail()
      }.also {
        println("Answer: $it")
      }
    }
    timed("$puzzleName Upload Answer $level") {
      when (val result = client.uploadAnswer(year, day, level, answer).also { println(it) }) {
        is AnswerResult.Correct -> {}
        is AnswerResult.AlreadyAnswered -> check(result.answer.toString() == result.correctAnswer.toString())
        is AnswerResult.Incorrect -> fail(result)
        is AnswerResult.IncorrectTooLow -> fail(result)
        is AnswerResult.IncorrectTooHigh -> fail(result)
        is AnswerResult.AnsweredTooRecently -> fail(result)
        is AnswerResult.Unknown -> fail(result)
      }
    }
  }
}

private fun solveAll(args: List<String>) {
  require(args.isEmpty()) { "Too many args for `solveAll` command." }
  for (year in 2015..2022) {
    for (day in 1..25) {
      try {
        solve(year, day)
      } catch (e: ClassNotFoundException) {
        // Ignore puzzles with no solutions.
      }
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
    "solveAll" -> solveAll(args.drop(1))
    "clearCache" -> clearCache(args.drop(1))
    else -> throw IllegalArgumentException("Invalid command: $command")
  }
}
