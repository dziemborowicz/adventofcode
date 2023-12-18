class PuzzleY2023D10 : Puzzle {

  private lateinit var loop: List<Index>

  override fun parse(input: String) {
    val grid = input.parseDenseCharGrid()
    val start = grid.indexOf('S')
    grid.patchPipeAt(start)
    loop = grid.loopIncluding(start)
  }

  override fun solve1(): Int = loop.size / 2

  override fun solve2(): Int = loop.areaEnclosedByIntegralBoundary()

  private fun Grid<Char>.loopIncluding(start: Index): List<Index> {
    val grid = this
    return buildList {
      add(start)
      while (true) {
        add(last().pipeNeighborsIn(grid).filter { it !in this }.firstOrNull() ?: break)
      }
    }
  }

  private fun Grid<Char>.patchPipeAt(index: Index) {
    val possible = mutableSetOf('-', '|', '7', 'F', 'J', 'L')
    if (index !in index.up().pipeNeighborsIn(this)) possible -= listOf('|', '7', 'F')
    if (index !in index.right().pipeNeighborsIn(this)) possible -= listOf('-', 'F', 'L')
    if (index !in index.down().pipeNeighborsIn(this)) possible -= listOf('|', 'J', 'L')
    if (index !in index.left().pipeNeighborsIn(this)) possible -= listOf('-', '7', 'J')
    this[index] = possible.single()
  }

  private fun Index.pipeNeighborsIn(grid: Grid<Char>): List<Index> {
    return when (grid.getOrNull(this)) {
      '-' -> listOf(left(), right())
      '|' -> listOf(up(), down())
      '7' -> listOf(left(), down())
      'F' -> listOf(right(), down())
      'J' -> listOf(left(), up())
      'L' -> listOf(right(), up())
      else -> listOf()
    }.filter { it in grid.indices }
  }

  companion object {
    val testInput1_1 = """
      .....
      .S-7.
      .|.|.
      .L-J.
      .....
      """.trimIndent()
    val testAnswer1_1 = 4

    val testInput1_2 = """
      ..F7.
      .FJ|.
      SJ.L7
      |F--J
      LJ...
      """.trimIndent()
    val testAnswer1_2 = 8

    val testInput2_1 = """
      ...........
      .S-------7.
      .|F-----7|.
      .||.....||.
      .||.....||.
      .|L-7.F-J|.
      .|..|.|..|.
      .L--J.L--J.
      ...........
      """.trimIndent()
    val testAnswer2_1 = 4

    val testInput2_2 = """
      ..........
      .S------7.
      .|F----7|.
      .||....||.
      .||....||.
      .|L-7F-J|.
      .|..||..|.
      .L--JL--J.
      ..........
      """.trimIndent()
    val testAnswer2_2 = 4

    val testInput2_3 = """
      .F----7F7F7F7F-7....
      .|F--7||||||||FJ....
      .||.FJ||||||||L7....
      FJL7L7LJLJ||LJ.L-7..
      L--J.L7...LJS7F-7L7.
      ....F-J..F7FJ|L7L7L7
      ....L7.F7||L7|.L7L7|
      .....|FJLJ|FJ|F7|.LJ
      ....FJL-7.||.||||...
      ....L---J.LJ.LJLJ...
      """.trimIndent()
    val testAnswer2_3 = 8
  }
}
