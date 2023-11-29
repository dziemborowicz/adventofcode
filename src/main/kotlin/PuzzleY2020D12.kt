class PuzzleY2020D12 : Puzzle {

  private lateinit var lines: List<String>

  override fun parse(input: String) {
    lines = input.lines()
  }

  override fun solve1(): Int {
    var location = Point.ZERO
    var direction = Point.RIGHT
    for (line in lines) {
      val number = line.drop(1).toInt()
      when (line.first()) {
        'N' -> location += Point.UP * number
        'S' -> location += Point.DOWN * number
        'E' -> location += Point.RIGHT * number
        'W' -> location += Point.LEFT * number
        'L' -> direction = direction.rotateCounterclockwise(number / 90)
        'R' -> direction = direction.rotateClockwise(number / 90)
        'F' -> location += direction * number
      }
    }
    return location.manhattanDistanceTo(Point.ZERO)
  }

  override fun solve2(): Int {
    var location = Point.ZERO
    var waypoint = Point(10, 1)
    for (line in lines) {
      val number = line.drop(1).toInt()
      when (line.first()) {
        'N' -> waypoint += Point.UP * number
        'S' -> waypoint += Point.DOWN * number
        'E' -> waypoint += Point.RIGHT * number
        'W' -> waypoint += Point.LEFT * number
        'L' -> waypoint = waypoint.rotateCounterclockwise(number / 90)
        'R' -> waypoint = waypoint.rotateClockwise(number / 90)
        'F' -> location += waypoint * number
      }
    }
    return location.manhattanDistanceTo(Point.ZERO)
  }

  companion object {
    val testInput1 = """
      F10
      N3
      F7
      R90
      F11
      """.trimIndent()
    val testAnswer1 = 25

    val testInput2 = testInput1
    val testAnswer2 = 286
  }
}
