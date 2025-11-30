class PuzzleY2024D25 : Puzzle {

  private lateinit var locks: List<Grid<Boolean>>
  private lateinit var keys: List<Grid<Boolean>>

  override fun parse(input: String) {
    val (locks, keys) = input.splitByBlank().map { lines -> lines.parseDenseBooleanGrid() }
      .partition { grid -> grid.row(0).all() }
    this.locks = locks
    this.keys = keys
  }

  override fun solve1(): Int {
    return locks.sumOf { lock ->
      keys.count { key -> key.allIndexed { index, c -> !c || !lock[index] } }
    }
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  companion object {
    val testInput1 = """
      #####
      .####
      .####
      .####
      .#.#.
      .#...
      .....
      
      #####
      ##.##
      .#.##
      ...##
      ...#.
      ...#.
      .....
      
      .....
      #....
      #....
      #...#
      #.#.#
      #.###
      #####
      
      .....
      .....
      #.#..
      ###..
      ###.#
      ###.#
      #####
      
      .....
      .....
      .....
      #....
      #.#..
      #.#.#
      #####
      """.trimIndent()
    val testAnswer1 = 3
  }
}
