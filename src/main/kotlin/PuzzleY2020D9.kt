class PuzzleY2020D9 : Puzzle {

  private lateinit var numbers: List<Long>

  private var invalidNumber: Long = -1

  override fun parse(input: String) {
    numbers = input.extractLongs()
    val preambleLength = if (numbers.size > 20) 25 else 5

    invalidNumber = numbers.firstIndexed { i, number ->
      i >= preambleLength && numbers.subList(i - preambleLength..i - 1).combinations(2)
        .none { (a, b) -> a + b == number && a != b }
    }
  }

  override fun solve1(): Long {
    return invalidNumber
  }

  override fun solve2(): Long {
    var first = 0
    var last = 1
    var sum = numbers[0] + numbers[1]
    while (true) {
      if (sum < invalidNumber) {
        sum += numbers[++last]
      } else if (sum > invalidNumber) {
        sum -= numbers[first++]
      } else {
        break
      }
    }
    val range = numbers.subList(first..last)
    return range.min() + range.max()
  }

  companion object {
    val testInput1 = """
      35
      20
      15
      25
      47
      40
      62
      55
      65
      95
      102
      117
      150
      182
      127
      219
      299
      277
      309
      576
      """.trimIndent()
    val testAnswer1 = 127

    val testInput2 = testInput1
    val testAnswer2 = 62
  }
}
