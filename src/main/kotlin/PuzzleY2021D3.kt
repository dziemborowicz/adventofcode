class PuzzleY2021D3 : Puzzle {

  private lateinit var report: List<String>

  private val bitLength: Int
    get() = report[0].length

  override fun parse(input: String) {
    report = input.parseStrings()
  }

  override fun solve1(): Int {
    var gammaRate = ""
    var epsilonRate = ""
    for (index in 0 until bitLength) {
      if (mostCommonChar(report, index) == '0') {
        gammaRate += "0"
        epsilonRate += "1"
      } else {
        gammaRate += "1"
        epsilonRate += "0"
      }
    }
    return gammaRate.toInt(2) * epsilonRate.toInt(2)
  }

  override fun solve2(): Int {
    val oxygenGeneratorRating = findRatingBy(::mostCommonChar)
    val co2ScrubberRating = findRatingBy(::leastCommonChar)
    return oxygenGeneratorRating * co2ScrubberRating
  }

  private fun findRatingBy(requiredBitProvider: (List<String>, Int) -> Char): Int {
    val candidates = report.toMutableList()
    for (index in 0 until bitLength) {
      if (candidates.size == 1) break
      val requiredBit = requiredBitProvider(candidates, index)
      candidates.removeIf { it[index] != requiredBit }
    }
    check(candidates.size == 1)
    return candidates.first().toInt(2)
  }

  private fun mostCommonChar(report: List<String>, index: Int): Char {
    val zeroes = report.count { it[index] == '0' }
    val ones = report.count { it[index] == '1' }
    return if (zeroes > ones) '0' else '1'
  }

  private fun leastCommonChar(report: List<String>, index: Int): Char {
    val zeroes = report.count { it[index] == '0' }
    val ones = report.count { it[index] == '1' }
    return if (zeroes > ones) '1' else '0'
  }

  companion object {
    val testInput1 = """
      00100
      11110
      10110
      10111
      10101
      01111
      00111
      11100
      10000
      11001
      00010
      01010
      """.trimIndent()
    val testAnswer1 = 198

    val testInput2 = testInput1
    val testAnswer2 = 230
  }
}
