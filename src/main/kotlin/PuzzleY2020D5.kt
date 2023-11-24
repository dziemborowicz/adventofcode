class PuzzleY2020D5 : Puzzle {

  private lateinit var boardingPasses: Set<Int>

  override fun parse(input: String) {
    boardingPasses = input.lines().map {
      it.replace('B', '1').replace('F', '0').replace('R', '1').replace('L', '0').toInt(2)
    }.toHashSet()
  }

  override fun solve1(): Int {
    return boardingPasses.max()
  }

  override fun solve2(): Int {
    var seatId = boardingPasses.min()
    do {
      seatId++
    } while (seatId in boardingPasses)
    return seatId
  }

  companion object {
    val testInput1_1 = "FBFBBFFRLR"
    val testAnswer1_1 = 357

    val testInput1_2 = "BFFFBBFRRR"
    val testAnswer1_2 = 567

    val testInput1_3 = "FFFBBBFRRR"
    val testAnswer1_3 = 119

    val testInput1_4 = "BBFFBBFRLL"
    val testAnswer1_4 = 820
  }
}
