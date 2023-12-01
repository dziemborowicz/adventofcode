class PuzzleY2023D1 : Puzzle {

  private val numbers = listOf(
    "one",
    "two",
    "three",
    "four",
    "five",
    "six",
    "seven",
    "eight",
    "nine",
  )

  private lateinit var lines: List<String>

  override fun parse(input: String) {
    lines = input.lines()
  }

  override fun solve1(): Int = lines.sumOf { 10 * it.firstDigit() + it.lastDigit() }

  override fun solve2(): Int = lines.sumOf { 10 * it.firstParsedDigit() + it.lastParsedDigit() }

  private fun String.firstDigit(): Int = firstNotNullOf { it.digitToIntOrNull() }

  private fun String.lastDigit(): Int = lastNotNullOf { it.digitToIntOrNull() }

  private fun String.firstParsedDigit(): Int = indices.firstNotNullOf { parsedDigitAtOrNull(it) }

  private fun String.lastParsedDigit(): Int = indices.lastNotNullOf { parsedDigitAtOrNull(it) }

  private fun String.parsedDigitAtOrNull(index: Int): Int? {
    this[index].digitToIntOrNull()?.let { return it }
    for ((i, number) in numbers.withIndex()) {
      if (startsWith(number, index)) {
        return i + 1
      }
    }
    return null
  }

  companion object {
    val testInput1 = """
      1abc2
      pqr3stu8vwx
      a1b2c3d4e5f
      treb7uchet
      """.trimIndent()
    val testAnswer1 = 142

    val testInput2 = """
      two1nine
      eightwothree
      abcone2threexyz
      xtwone3four
      4nineeightseven2
      zoneight234
      7pqrstsixteen
      """.trimIndent()
    val testAnswer2 = 281
  }
}
