class PuzzleY2023D10 : Puzzle {

  private lateinit var grid: Grid<Char>
  private lateinit var start: Index

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
    start = grid.indexOf('S')
    grid.patchPipeAt(start)
  }

  override fun solve1(): Int {
    return grid.loopIncluding(start).size / 2
  }

  override fun solve2(): Int {
    val grid = grid.grow(1, '.').double('.')
    val start = start.plus(1 rc 1).times(2)
    val loop = grid.loopIncluding(start).toSet()

    fun indicesReachableFrom(start: Index): Set<Index> {
      return buildSet {
        val queue = dequeOf(start)
        while (queue.isNotEmpty()) {
          val index = queue.removeFirst()
          if (index in loop || !add(index)) continue
          queue.addAll(index.neighborsIn(grid))
        }
      }
    }

    val reachability = grid.map<Boolean?> { null }
    for (index in reachability.indices) {
      if (reachability[index] == null) {
        val group = indicesReachableFrom(index)
        if (group.any { it.isOnAnyEdgeOf(grid) }) {
          group.forEach { reachability[it] = true }
        } else {
          group.forEach { reachability[it] = false }
        }
      }
    }
    return reachability.halve().count { it == false }
  }

  private fun Grid<Char>.double(c: Char): Grid<Char> {
    return Grid(numRows * 2, numColumns * 2) { (row, column) ->
      if (row % 2 == 0 && column % 2 == 0) {
        this[row / 2, column / 2]
      } else {
        c
      }
    }.apply { indices.filter { it.row.isOdd || it.column.isOdd }.forEach { patchPipeAt(it) } }
  }

  private fun <T> Grid<T>.halve(): Grid<T> {
    return Grid(numRows / 2, numColumns / 2) { (row, column) ->
      this[row * 2, column * 2]
    }
  }

  private fun Grid<Char>.loopIncluding(start: Index): List<Index> {
    val grid = this
    return buildList {
      var index = start
      do {
        val nextIndex = index.pipeNeighborsIn(grid).first { it != lastOrNull() }
        add(index)
        index = nextIndex
      } while (index != start)
    }
  }

  private fun Grid<Char>.patchPipeAt(index: Index) {
    val grid = this
    val connectable = buildList {
      if (grid.getOrDefault(index.up(), '.') in "|7F") add(index.up())
      if (grid.getOrDefault(index.right(), '.') in "-7J") add(index.right())
      if (grid.getOrDefault(index.down(), '.') in "|JL") add(index.down())
      if (grid.getOrDefault(index.left(), '.') in "-LF") add(index.left())
    }
    grid[index] = when {
      index.up() in connectable && index.down() in connectable -> '|'
      index.up() in connectable && index.left() in connectable -> 'J'
      index.up() in connectable && index.right() in connectable -> 'L'
      index.down() in connectable && index.left() in connectable -> '7'
      index.down() in connectable && index.right() in connectable -> 'F'
      index.left() in connectable && index.right() in connectable -> '-'
      else -> grid[index]
    }
  }

  private fun Index.pipeNeighborsIn(grid: Grid<Char>): List<Index> {
    return when (grid[this]) {
      '-' -> listOf(left(), right())
      '|' -> listOf(up(), down())
      '7' -> listOf(left(), down())
      'F' -> listOf(right(), down())
      'J' -> listOf(left(), up())
      'L' -> listOf(right(), up())
      else -> listOf()
    }
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
