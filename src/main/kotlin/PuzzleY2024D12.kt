class PuzzleY2024D12 : Puzzle {

  private lateinit var farm: Grid<Char>

  override fun parse(input: String) {
    farm = input.parseDenseCharGrid()
  }

  override fun solve1(): Int = farm.connectedSubgraphs().sumOf { it.size * it.perimeter }

  override fun solve2(): Int = farm.connectedSubgraphs().sumOf { it.size * it.numSides }

  private val Set<Index>.perimeter: Int
    get() = sumOf { index -> index.neighbors().count { neighbor -> neighbor !in this } }

  private val Set<Index>.numSides: Int
    get() = sumOf {
      it.corners().count { (a, b, c) ->
        (a in this && b !in this && c in this) || (a !in this && c !in this)
      }
    }

  companion object {
    val testInput1_1 = """
      AAAA
      BBCD
      BBCC
      EEEC
      """.trimIndent()
    val testAnswer1_1 = 140

    val testInput1_2 = """
      OOOOO
      OXOXO
      OOOOO
      OXOXO
      OOOOO
      """.trimIndent()
    val testAnswer1_2 = 772

    val testInput1_3 = """
      RRRRIICCFF
      RRRRIICCCF
      VVRRRCCFFF
      VVRCCCJFFF
      VVVVCJJCFE
      VVIVCCJJEE
      VVIIICJJEE
      MIIIIIJJEE
      MIIISIJEEE
      MMMISSJEEE
      """.trimIndent()
    val testAnswer1_3 = 1930

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 80

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 436

    val testInput2_3 = testInput1_3
    val testAnswer2_3 = 1206

    val testInput2_4 = """
      EEEEE
      EXXXX
      EEEEE
      EXXXX
      EEEEE
      """.trimIndent()
    val testAnswer2_4 = 236

    val testInput2_5 = """
      AAAAAA
      AAABBA
      AAABBA
      ABBAAA
      ABBAAA
      AAAAAA
      """.trimIndent()
    val testAnswer2_5 = 368
  }
}
