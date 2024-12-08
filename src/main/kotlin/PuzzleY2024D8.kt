class PuzzleY2024D8 : Puzzle {

  private lateinit var roof: Grid<Char>
  private lateinit var antennasByFrequency: Map<Char, List<Index>>

  override fun parse(input: String) {
    roof = input.parseDenseCharGrid()
    antennasByFrequency = roof.indices.filter { roof[it] != '.' }.groupBy { roof[it] }
  }

  override fun solve1(): Int = solve(::singleAntinode)

  override fun solve2(): Int = solve(::allAntinodes)

  private fun solve(antinodes: (Index, Index) -> List<Index>): Int {
    return antennasByFrequency.flatMap { (_, locations) ->
      locations.permutations(2).flatMap { (a, b) -> antinodes(a, b) }
    }.countDistinct()
  }

  private fun singleAntinode(a: Index, b: Index): List<Index> {
    val antinode = a + a - b
    return if (antinode in roof.indices) listOf(antinode) else listOf()
  }

  private fun allAntinodes(a: Index, b: Index): List<Index> {
    return buildList {
      var antinode = a
      while (antinode in roof.indices) {
        add(antinode)
        antinode += a - b
      }
    }
  }

  companion object {
    val testInput1 = """
      ............
      ........0...
      .....0......
      .......0....
      ....0.......
      ......A.....
      ............
      ............
      ........A...
      .........A..
      ............
      ............
      """.trimIndent()
    val testAnswer1 = 14

    val testInput2 = testInput1
    val testAnswer2 = 34
  }
}
