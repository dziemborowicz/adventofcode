class PuzzleY2024D22 : Puzzle {

  private lateinit var secretNumbers: List<List<Long>>

  override fun parse(input: String) {
    secretNumbers = input.extractLongs().map {
      buildList {
        var current = it
        repeat(2001) {
          add(current)
          current = ((current * 64L) xor current).mod(16777216L)
          current = ((current / 32L) xor current).mod(16777216L)
          current = ((current * 2048L) xor current).mod(16777216L)
        }
      }
    }
  }

  override fun solve1(): Long = secretNumbers.sumOf { it.last() }

  override fun solve2(): Long {
    val counter = LongCounter<List<Long>>()
    secretNumbers.map { it.map { it.mod(10L) } }.forEach { prices ->
      val seen = hashSetOf<List<Long>>()
      prices.windowed(2).map { (a, b) -> b - a }.windowed(4).forEachIndexed { i, priceChanges ->
        if (seen.add(priceChanges)) {
          counter[priceChanges] += prices[i + 4]
        }
      }
    }
    return counter.max().count
  }

  companion object {
    val testInput1 = """
      1
      10
      100
      2024
      """.trimIndent()
    val testAnswer1 = 37327623

    val testInput2 = """
      1
      2
      3
      2024
      """.trimIndent()
    val testAnswer2 = 23
  }
}
