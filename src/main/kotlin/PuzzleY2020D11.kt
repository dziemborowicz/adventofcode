import Index.Companion.DIRECTIONS_WITH_DIAGONALS

class PuzzleY2020D11 : Puzzle {

  private lateinit var map: Grid<Char>

  override fun parse(input: String) {
    map = input.parseDenseCharGrid()
  }

  override fun solve1(): Int {
    var map = map
    while (true) {
      val next = Grid(map.numRows, map.numColumns) { it ->
        val prev = map[it]
        if (prev == 'L' && it.neighborsWithDiagonalsIn(map).none { map[it] == '#' }) {
          '#'
        } else if (prev == '#' && it.neighborsWithDiagonalsIn(map).count { map[it] == '#' } >= 4) {
          'L'
        } else {
          prev
        }
      }
      if (map == next) {
        return map.count { it == '#' }
      }
      map = next
    }
  }

  override fun solve2(): Int {
    var map = map
    while (true) {
      val next = Grid(map.numRows, map.numColumns) { it ->
        val prev = map[it]
        if (prev == 'L' && it.visibleNeighborsIn(map).none { map[it] == '#' }) {
          '#'
        } else if (prev == '#' && it.visibleNeighborsIn(map).count { map[it] == '#' } >= 5) {
          'L'
        } else {
          prev
        }
      }
      if (map == next) {
        return map.count { it == '#' }
      }
      map = next
    }
  }

  private fun Index.visibleNeighborsIn(grid: Grid<*>): List<Index> {
    return DIRECTIONS_WITH_DIAGONALS.mapNotNull { direction ->
      var index = this
      do {
        index += direction
      } while (grid.getOrNull(index) == '.')
      if (index in grid.indices) index else null
    }
  }

  companion object {
    val testInput1 = """
      L.LL.LL.LL
      LLLLLLL.LL
      L.L.L..L..
      LLLL.LL.LL
      L.LL.LL.LL
      L.LLLLL.LL
      ..L.L.....
      LLLLLLLLLL
      L.LLLLLL.L
      L.LLLLL.LL
      """.trimIndent()
    val testAnswer1 = 37

    val testInput2 = testInput1
    val testAnswer2 = 26
  }
}
