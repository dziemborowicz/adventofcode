class PuzzleY2020D17 : Puzzle {

  private lateinit var initialState: Set<Point>

  override fun parse(input: String) {
    initialState = input.parseDenseCharGrid().withPoint().mapNotNull { (point, c) ->
      if (c == '#') {
        point
      } else {
        null
      }
    }.toSet()
  }

  override fun solve1(): Int {
    var state = initialState.map { listOf(it.x, it.y, 0) }.toSet()
    repeat(6) { state = evolve(state) }
    return state.size
  }

  override fun solve2(): Int {
    var state = initialState.map { listOf(it.x, it.y, 0, 0) }.toSet()
    repeat(6) { state = evolve(state) }
    return state.size
  }

  private fun evolve(state: Set<List<Int>>): Set<List<Int>> {
    return (state.filter { point ->
      point.neighborsWithDiagonals().count { it in state } in 2..3
    } + state.flatMap { it.neighborsWithDiagonals() }.toSet().filter { point ->
      point !in state && point.neighborsWithDiagonals().count { it in state } == 3
    }).toSet()
  }

  private fun List<Int>.neighborsWithDiagonals(): List<List<Int>> {
    return listOf(-1, 0, 1).permutationsWithReplacement(size).toList().mapNotNull { deltas ->
      if (deltas.all { it == 0 }) {
        null
      } else {
        (this zip deltas).map { (a, b) -> a + b }
      }
    }
  }

  companion object {
    val testInput1 = """
      .#.
      ..#
      ###
      """.trimIndent()
    val testAnswer1 = 112

    val testInput2 = testInput1
    val testAnswer2 = 848
  }
}
