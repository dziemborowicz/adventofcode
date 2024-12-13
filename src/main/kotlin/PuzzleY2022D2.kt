class PuzzleY2022D2 : Puzzle {

  lateinit var strategyGuide: List<List<Char>>

  override fun parse(input: String) {
    strategyGuide = input.extractCharLists()
  }

  override fun solve1(): Int {
    return strategyGuide.sumOf { (first, second) ->
      val opponentsShape = first - 'A' + 1
      val yourShape = second - 'X' + 1
      val outcome = (yourShape - opponentsShape + 1).mod(3) * 3
      yourShape + outcome
    }
  }

  override fun solve2(): Int {
    return strategyGuide.sumOf { (first, second) ->
      val opponentsShape = first - 'A' + 1
      val outcome = (second - 'X') * 3
      val yourShape = (opponentsShape - 1 + (outcome / 3) - 1).mod(3) + 1
      yourShape + outcome
    }
  }

  companion object {
    val testInput1 = """
      A Y
      B X
      C Z
      """.trimIndent()
    val testAnswer1 = 15

    val testInput2 = testInput1
    val testAnswer2 = 12
  }
}
