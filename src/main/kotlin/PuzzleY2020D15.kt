class PuzzleY2020D15 : Puzzle {

  private lateinit var numbers: List<Int>

  override fun parse(input: String) {
    numbers = input.extractInts()
  }

  override fun solve1(): Int {
    return nthSpokenNumber(2020)
  }

  override fun solve2(): Int {
    return nthSpokenNumber(30_000_000)
  }

  private fun nthSpokenNumber(turns: Int): Int {
    val largestPossibleNumber = maxOf(turns, numbers.max())
    val spokenNumbers = Array(largestPossibleNumber) { -1 }
    var lastSpokenNumber = -1
    var whenLastSpokenNumberWasSpokenBefore = -1
    repeat(turns) { turn ->
      lastSpokenNumber = if (turn in numbers.indices) {
        numbers[turn]
      } else if (whenLastSpokenNumberWasSpokenBefore == -1) {
        0
      } else {
        turn - whenLastSpokenNumberWasSpokenBefore - 1
      }
      whenLastSpokenNumberWasSpokenBefore = spokenNumbers[lastSpokenNumber]
      spokenNumbers[lastSpokenNumber] = turn
    }
    return lastSpokenNumber
  }

  companion object {
    val testInput1_1 = "0,3,6"
    val testAnswer1_1 = 436

    val testInput1_2 = "1,3,2"
    val testAnswer1_2 = 1

    val testInput1_3 = "2,1,3"
    val testAnswer1_3 = 10

    val testInput1_4 = "1,2,3"
    val testAnswer1_4 = 27

    val testInput1_5 = "2,3,1"
    val testAnswer1_5 = 78

    val testInput1_6 = "3,2,1"
    val testAnswer1_6 = 438

    val testInput1_7 = "3,1,2"
    val testAnswer1_7 = 1836

    val testInput2_1 = "0,3,6"
    val testAnswer2_1 = 175594

    val testInput2_2 = "1,3,2"
    val testAnswer2_2 = 2578

    val testInput2_3 = "2,1,3"
    val testAnswer2_3 = 3544142

    val testInput2_4 = "1,2,3"
    val testAnswer2_4 = 261214

    val testInput2_5 = "2,3,1"
    val testAnswer2_5 = 6895259

    val testInput2_6 = "3,2,1"
    val testAnswer2_6 = 18

    val testInput2_7 = "3,1,2"
    val testAnswer2_7 = 362
  }
}
