class PuzzleY2022D9 : Puzzle {

  private lateinit var motions: List<Pair<Char, Int>>

  override fun parse(input: String) {
    motions = input.extractList(String::toChar, String::toInt)
  }

  override fun solve1(): Int {
    return solve(length = 2)
  }

  override fun solve2(): Int {
    return solve(length = 10)
  }

  private fun solve(length: Int): Int {
    val uniqueTailPositions = hashSetOf(Point(0, 0))
    val rope = MutableList(length) { Point(0, 0) }
    for ((direction, distance) in motions) {
      repeat(distance) {
        rope[0] = rope[0].offset(direction)
        for (i in 1 until length) {
          rope[i] = rope[i].moveTowards(rope[i - 1])
        }
        uniqueTailPositions.add(rope.last())
      }
    }
    return uniqueTailPositions.size
  }

  private fun Point.offset(direction: Char): Point {
    return when (direction) {
      'U' -> up()
      'D' -> down()
      'L' -> left()
      'R' -> right()
      else -> fail()
    }
  }

  private fun Point.moveTowards(other: Point): Point {
    return if (xDistanceTo(other) > 1 || yDistanceTo(other) > 1) {
      Point(x + (other.x - x).coerceIn(-1, 1), y + (other.y - y).coerceIn(-1, 1))
    } else {
      this
    }
  }

  companion object {
    val testInput1 = """
      R 4
      U 4
      L 3
      D 1
      R 4
      D 1
      L 5
      R 2
      """.trimIndent()
    val testAnswer1 = 13

    val testInput2_1 = testInput1
    val testAnswer2_1 = 1

    val testInput2_2 = """
      R 5
      U 8
      L 8
      D 3
      R 17
      D 10
      L 25
      U 20
      """.trimIndent()
    val testAnswer2_2 = 36
  }
}
