import java.math.BigInteger

class PuzzleY2025D5 : Puzzle {

  private lateinit var ranges: LongRangeSet
  private lateinit var ingredients: List<Long>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()
    ranges = parts.first().map { it.extractLongs().toLongRange() }.toLongRangeSet()
    ingredients = parts.second().map { it.toLong() }
  }

  override fun solve1(): Int = ingredients.count { it in ranges }

  override fun solve2(): BigInteger = ranges.count()

  companion object {
    val testInput1 = """
      3-5
      10-14
      16-20
      12-18
      
      1
      5
      8
      11
      17
      32
      """.trimIndent()
    val testAnswer1 = 3

    val testInput2 = testInput1
    val testAnswer2 = 14
  }
}
