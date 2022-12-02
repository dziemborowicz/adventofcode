class PuzzleY2021D6 : Puzzle {

  private lateinit var daysUntilSpawn: List<Int>
  private val cache = mutableMapOf<Pair<Int, Int>, Long>()

  override fun parse(input: String) {
    daysUntilSpawn = input.parseIntLists().first()
  }

  override fun solve1(): Long {
    return daysUntilSpawn.sumOf { countFish(it, 80) }
  }

  override fun solve2(): Long {
    return daysUntilSpawn.sumOf { countFish(it, 256) }
  }

  private fun countFish(daysUntilSpawn: Int, daysLeft: Int): Long {
    val cachedResult = cache[Pair(daysUntilSpawn, daysLeft)]
    if (cachedResult != null) return cachedResult

    return when {
      daysLeft < 1 -> 1
      daysUntilSpawn == 0 -> countFish(6, daysLeft - 1) + countFish(8, daysLeft - 1)
      else -> countFish(0, daysLeft - daysUntilSpawn)
    }.also {
      cache[Pair(daysUntilSpawn, daysLeft)] = it
    }
  }

  companion object {
    val testInput1 = """
      3,4,3,1,2
      """.trimIndent()
    val testAnswer1 = 5934

    val testInput2 = testInput1
    val testAnswer2 = 26984457539
  }
}
