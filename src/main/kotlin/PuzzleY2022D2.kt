class PuzzleY2022D2 : Puzzle {

  lateinit var strategyGuide: List<Pair<Char, Char>>

  override fun parse(input: String) {
    strategyGuide = input.parseCharChars()
  }

  override fun solve1(): Int {
    return strategyGuide.sumOf {
      val opponentsShape = it.first - 'A' + 1
      val yourShape = it.second - 'X' + 1
      val outcome = (yourShape - opponentsShape + 1).mod(3) * 3
      yourShape + outcome
    }
  }

  override fun solve2(): Int {
    return strategyGuide.sumOf {
      val opponentsShape = it.first - 'A' + 1
      val outcome = (it.second - 'X') * 3
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
