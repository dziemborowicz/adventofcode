class PuzzleY2022D10 : Puzzle {

  private lateinit var cycles: List<Int>

  override fun parse(input: String) {
    cycles = compute(input.lines())
  }

  private fun compute(instructions: List<String>): List<Int> {
    var x = 1
    val cycles = mutableListOf(x)
    for (instruction in instructions) {
      when {
        instruction == "noop" -> {
          cycles.add(x)
        }
        instruction.startsWith("addx ") -> {
          cycles.add(x)
          cycles.add(x)
          x += instruction.removePrefix("addx ").toInt()
        }
        else -> fail()
      }
    }
    while (cycles.lastIndex < 240) cycles.add(x)
    return cycles
  }

  override fun solve1(): Int {
    return (20..220 step 40).sumOf { it * cycles[it] }
  }

  override fun solve2(): String {
    val grid = Grid(6, 40) { (row, column) ->
      val sprite = cycles[(row * 40) + column + 1]
      column in sprite - 1..sprite + 1
    }
    return grid.toStringFromAsciiArt()
  }

  companion object {
    val testInput1 = """
      addx 15
      addx -11
      addx 6
      addx -3
      addx 5
      addx -1
      addx -8
      addx 13
      addx 4
      noop
      addx -1
      addx 5
      addx -1
      addx 5
      addx -1
      addx 5
      addx -1
      addx 5
      addx -1
      addx -35
      addx 1
      addx 24
      addx -19
      addx 1
      addx 16
      addx -11
      noop
      noop
      addx 21
      addx -15
      noop
      noop
      addx -3
      addx 9
      addx 1
      addx -3
      addx 8
      addx 1
      addx 5
      noop
      noop
      noop
      noop
      noop
      addx -36
      noop
      addx 1
      addx 7
      noop
      noop
      noop
      addx 2
      addx 6
      noop
      noop
      noop
      noop
      noop
      addx 1
      noop
      noop
      addx 7
      addx 1
      noop
      addx -13
      addx 13
      addx 7
      noop
      addx 1
      addx -33
      noop
      noop
      noop
      addx 2
      noop
      noop
      noop
      addx 8
      noop
      addx -1
      addx 2
      addx 1
      noop
      addx 17
      addx -9
      addx 1
      addx 1
      addx -3
      addx 11
      noop
      noop
      addx 1
      noop
      addx 1
      noop
      noop
      addx -13
      addx -19
      addx 1
      addx 3
      addx 26
      addx -30
      addx 12
      addx -1
      addx 3
      addx 1
      noop
      noop
      noop
      addx -9
      addx 18
      addx 1
      addx 2
      noop
      noop
      addx 9
      noop
      noop
      noop
      addx -1
      addx 2
      addx -37
      addx 1
      addx 3
      noop
      addx 15
      addx -21
      addx 22
      addx -6
      addx 1
      noop
      addx 2
      addx 1
      noop
      addx -10
      noop
      noop
      addx 20
      addx 1
      addx 2
      addx 2
      addx -6
      addx -11
      noop
      noop
      noop
      """.trimIndent()
    val testAnswer1 = 13140
  }
}
