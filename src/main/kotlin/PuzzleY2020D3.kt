class PuzzleY2020D3 : Puzzle {

  private lateinit var map: Grid<Char>

  override fun parse(input: String) {
    map = input.parseDenseCharGrid()
  }

  override fun solve1(): Long {
    return treesEncountered(1 rc 3)
  }

  override fun solve2(): Long {
    return listOf(
      1 rc 1,
      1 rc 3,
      1 rc 5,
      1 rc 7,
      2 rc 1,
    ).productOf { treesEncountered(it) }
  }

  private fun treesEncountered(offset: Index): Long {
    var index = Index.ZERO
    var numTrees = 0L
    while (index.row in map.rowIndices) {
      if (map.getWrapped(index) == '#') numTrees++
      index = index.translate(offset)
    }
    return numTrees
  }

  companion object {
    val testInput1 = """
      ..##.......
      #...#...#..
      .#....#..#.
      ..#.#...#.#
      .#...##..#.
      ..#.##.....
      .#.#.#....#
      .#........#
      #.##...#...
      #...##....#
      .#..#...#.#
      """.trimIndent()
    val testAnswer1 = 7

    val testInput2 = testInput1
    val testAnswer2 = 336
  }
}
