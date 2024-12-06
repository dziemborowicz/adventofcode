class PuzzleY2024D6 : Puzzle {

  private lateinit var map: Grid<Char>
  private lateinit var route: Set<Index>

  override fun parse(input: String) {
    map = input.parseDenseCharGrid()
    route = patrol(map).route
  }

  override fun solve1(): Int = route.size

  override fun solve2(): Int {
    return route.filter { map[it] == '.' }.count { index ->
      val newMap = map.copy().apply { this[index] = '#' }
      patrol(newMap).isLoop
    }
  }

  private fun patrol(map: Grid<Char>): PatrolResult {
    var position = map.indexOf('^')
    var direction = Index.UP
    val visited = hashSetOf<Pair<Index, Index>>()
    while (visited.add(position to direction)) {
      val nextPosition = position + direction
      when {
        nextPosition !in map.indices -> return PatrolResult(visited.map { it.first }.toSet(), false)
        map[nextPosition] == '#' -> direction = direction.rotateClockwise()
        else -> position = nextPosition
      }
    }
    return PatrolResult(visited.map { it.first }.toSet(), true)
  }

  private data class PatrolResult(val route: Set<Index>, val isLoop: Boolean)

  companion object {
    val testInput1 = """
      ....#.....
      .........#
      ..........
      ..#.......
      .......#..
      ..........
      .#..^.....
      ........#.
      #.........
      ......#...
      """.trimIndent()
    val testAnswer1 = 41

    val testInput2 = testInput1
    val testAnswer2 = 6
  }
}
