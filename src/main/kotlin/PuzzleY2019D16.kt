class PuzzleY2019D16 : Puzzle {

  private lateinit var input: String

  override fun parse(input: String) {
    this.input = input
  }

  override fun solve1(): String {
    var digits = input.map { it.digitToInt() }
    repeat(100) {
      digits = List(digits.size) { i ->
        digits.sumOfIndexed { j, d ->
          (d * repeatingPatternAt(i, j))
        }.lastDigit()
      }
    }
    return digits.take(8).joinToString("")
  }

  override fun solve2(): String {
    val offset = input.take(7).toInt()
    check(offset >= input.length / 2)
    val digits =
      (offset..<10_000 * input.length).map { input.getWrapped(it).digitToInt() }.toMutableList()
    repeat(100) {
      for (i in digits.indices.reversed()) {
        digits[i] = (digits[i] + digits.getOrDefault(i + 1, 0)).lastDigit()
      }
    }
    return digits.take(8).joinToString("")
  }

  private fun repeatingPatternAt(i: Int, j: Int): Int {
    val basePattern = listOf(0, 1, 0, -1)
    return basePattern.getWrapped((j + 1) / (i + 1))
  }

  private fun Int.lastDigit(): Int {
    return if (this < 0) {
      -(this % 10)
    } else {
      this % 10
    }
  }

  companion object {
    val testInput1_1 = "80871224585914546619083218645595"
    val testAnswer1_1 = 24176176

    val testInput1_2 = "19617804207202209144916044189917"
    val testAnswer1_2 = 73745418

    val testInput1_3 = "69317163492948606335995924319873"
    val testAnswer1_3 = 52432133

    val testInput2_1 = "03036732577212944063491565474664"
    val testAnswer2_1 = 84462026

    val testInput2_2 = "02935109699940807407585447034323"
    val testAnswer2_2 = 78725270

    val testInput2_3 = "03081770884921959731165446850517"
    val testAnswer2_3 = 53553731
  }
}
