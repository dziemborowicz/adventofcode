class PuzzleY2025D3 : Puzzle {

  private lateinit var banks: List<List<Int>>

  override fun parse(input: String) {
    banks = input.lines().map { line -> line.map { c -> c.digitToInt() } }
  }

  override fun solve1(): Long = banks.sumOf { it.maxJoltage(numBatteries = 2) }

  override fun solve2(): Long = banks.sumOf { it.maxJoltage(numBatteries = 12) }

  private fun List<Int>.maxJoltage(numBatteries: Int): Long {
    val digits = mutableListOf<Int>()
    var bank = this
    while (digits.size < numBatteries) {
      val maxDigit = bank.dropLast(numBatteries - digits.size - 1).max()
      digits += maxDigit
      bank = bank.drop(bank.indexOf(maxDigit) + 1)
    }
    return digits.joinToString("").toLong()
  }

  companion object {
    val testInput1 = """
      987654321111111
      811111111111119
      234234234234278
      818181911112111
      """.trimIndent()
    val testAnswer1 = 357

    val testInput2 = testInput1
    val testAnswer2 = 3121910778619L
  }
}
