class PuzzleY2019D3 : Puzzle {

  private lateinit var panel: Map<Point, Map<Int, Int>>

  override fun parse(input: String) {
    val panel = mutableMapOf<Point, MutableMap<Int, Int>>()

    for ((index, line) in input.lines().withIndex()) {
      var position = Point(0, 0)
      var steps = 0
      for (movement in line.split(',')) {
        val direction = movement.first()
        val distance = movement.drop(1).toInt()
        repeat(distance) {
          position += when (direction) {
            'L' -> Point.LEFT
            'R' -> Point.RIGHT
            'U' -> Point.UP
            'D' -> Point.DOWN
            else -> fail()
          }
          steps++
          panel.compute(position) { _, prev ->
            val map = prev ?: mutableMapOf()
            map.putIfAbsent(index, steps)
            map
          }
        }
      }
    }

    this.panel = panel
  }

  override fun solve1(): Int =
    panel.filter { (position, steps) -> position != Point.ZERO && steps.size == 2 }
      .minOf { (position, _) -> Point.ZERO manhattanDistanceTo position }

  override fun solve2(): Int =
    panel.filter { (position, steps) -> position != Point.ZERO && steps.size == 2 }
      .minOf { (_, steps) -> steps.values.sum() }

  companion object {
    val testInput1_1 = """
      R8,U5,L5,D3
      U7,R6,D4,L4
      """.trimIndent()
    val testAnswer1_1 = 6

    val testInput1_2 = """
      R75,D30,R83,U83,L12,D49,R71,U7,L72
      U62,R66,U55,R34,D71,R55,D58,R83
      """.trimIndent()
    val testAnswer1_2 = 159

    val testInput1_3 = """
      R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
      U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
      """.trimIndent()
    val testAnswer1_3 = 135

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 30

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 610

    val testInput2_3 = testInput1_3
    val testAnswer2_3 = 410
  }
}
