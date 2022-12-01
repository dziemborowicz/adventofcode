class PuzzleY2021D2 : Puzzle {

  private lateinit var commands: List<Pair<String, Int>>

  override fun parse(input: String) {
    commands = input.parseStringInts()
  }

  override fun solve1(): Int {
    var horizontalPosition = 0
    var depth = 0
    commands.forEach {
      when (it.first) {
        "forward" -> horizontalPosition += it.second
        "down" -> depth += it.second
        "up" -> depth -= it.second
        else -> throw AssertionError()
      }
    }
    return horizontalPosition * depth
  }

  override fun solve2(): Int {
    var horizontalPosition = 0
    var depth = 0
    var aim = 0
    commands.forEach {
      when (it.first) {
        "forward" -> {
          horizontalPosition += it.second
          depth += aim * it.second
        }
        "down" -> aim += it.second
        "up" -> aim -= it.second
        else -> throw AssertionError()
      }
    }
    return horizontalPosition * depth
  }

  companion object {
    val testInput1 = """
      forward 5
      down 5
      forward 8
      up 3
      down 8
      forward 2
      """.trimIndent()
    val testAnswer1 = 150

    val testInput2 = testInput1
    val testAnswer2 = 900
  }
}
