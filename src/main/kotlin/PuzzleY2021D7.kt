import kotlin.math.abs

class PuzzleY2021D7 : Puzzle {

  private lateinit var crabs: List<Int>

  override fun parse(input: String) {
    crabs = input.extractInts()
  }

  override fun solve1(): Int {
    return (crabs.min()..crabs.max()).minOf { position ->
      crabs.sumOf { abs(it - position) }
    }
  }

  override fun solve2(): Int {
    return (crabs.min()..crabs.max()).minOf { position ->
      crabs.sumOf {
        val distance = abs(it - position)
        distance * (distance + 1) / 2
      }
    }
  }

  companion object {
    val testInput1 = """
      16,1,2,0,4,2,7,1,2,14
      """.trimIndent()
    val testAnswer1 = 37

    val testInput2 = testInput1
    val testAnswer2 = 168
  }
}
