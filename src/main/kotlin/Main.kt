private fun solve(args: List<String>) {
  require(args.size in 2..3) { "Wrong number of args for `solve` command." }
  val year = args[0].toInt()
  val day = args[1].toInt()
  val levels = if (args.size > 2) (args[2].toInt()..args[2].toInt()) else (1..2)

  val session = System.getenv("ADVENT_OF_CODE_SESSION")
  require(session != null && session.isNotBlank()) { "ADVENT_OF_CODE_SESSION not set." }

  val client = Client(session)
  val input = timed("Download Input") {
    client.downloadInput(year, day).also {
      if (it.length > 32) {
        println("${it.take(32)}...")
      } else {
        println(it)
      }
      println("${it.length} character(s)")
      println("${it.count { c -> c == '\n' }} line(s)")
    }
  }

  val puzzleClass = "PuzzleY${year}D${day}"
  val puzzle = Class.forName(puzzleClass).getDeclaredConstructor().newInstance() as Puzzle
  timed("Parse Input") { puzzle.parse(input) }

  for (level in levels) {
    val answer = timed("Solve $level") {
      try {
        when (level) {
          1 -> puzzle.solve1()
          2 -> puzzle.solve2()
          else -> throw AssertionError()
        }.also {
          println("Answer: $it")
        }
      } catch (e: NotImplementedError) {
        println("Not implemented yet.")
        return
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
