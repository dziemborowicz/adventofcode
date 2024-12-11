class PuzzleY2024D11 : Puzzle {

  private lateinit var stones: List<Long>

  override fun parse(input: String) {
    stones = input.extractLongs()
  }

  override fun solve1(): Long = solve(25)

  override fun solve2(): Long = solve(75)

  private fun solve(rounds: Int): Long {
    var counter = stones.toLongCounter()
    repeat(rounds) {
      val nextCounter = LongCounter<Long>()
      for ((stone, count) in counter.counts()) {
        val stoneDigits = stone.toString()
        when {
          stone == 0L -> nextCounter[1L] += count

          stoneDigits.length.isEven -> stoneDigits.chunked(stoneDigits.length / 2).forEach {
            nextCounter[it.toLong()] += count
          }

          else -> nextCounter[stone * 2024L] += count
        }
      }
      counter = nextCounter
    }
    return counter.countAll()
  }

  companion object {
    val testInput1 = "125 17"
    val testAnswer1 = 55312
  }
}
