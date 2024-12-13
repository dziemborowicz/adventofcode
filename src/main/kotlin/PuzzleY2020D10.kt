class PuzzleY2020D10 : Puzzle {

  private lateinit var allAdapters: Set<Int>

  private lateinit var adapters: Set<Int>
  private var builtInAdapter = -1
  private var chargingOutlet = 0

  override fun parse(input: String) {
    adapters = input.extractInts().toSet()
    builtInAdapter = adapters.max() + 3
    allAdapters = adapters + builtInAdapter + chargingOutlet
  }

  override fun solve1(): Int {
    val differenceCounts = allAdapters.sorted().windowed(2).map { (a, b) -> b - a }.toCounter()
    return differenceCounts[1] * differenceCounts[3]
  }

  override fun solve2(): Long {
    val cache = hashMapOf<Int, Long>()
    fun numArrangementsFrom(adapter: Int): Long {
      return cache.getOrPut(adapter) {
        when (adapter) {
          builtInAdapter -> 1
          !in allAdapters -> 0
          else -> (1..3).sumOf { numArrangementsFrom(adapter + it) }
        }
      }
    }
    return numArrangementsFrom(chargingOutlet)
  }

  companion object {
    val testInput1_1 = """
      16
      10
      15
      5
      1
      11
      7
      19
      6
      12
      4
      """.trimIndent()
    val testAnswer1_1 = 7 * 5

    val testInput1_2 = """
      28
      33
      18
      42
      31
      14
      46
      20
      48
      47
      24
      23
      49
      45
      19
      38
      39
      11
      1
      32
      25
      35
      8
      17
      7
      9
      4
      2
      34
      10
      3
      """.trimIndent()
    val testAnswer1_2 = 22 * 10

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 8L

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 19208L
  }
}
