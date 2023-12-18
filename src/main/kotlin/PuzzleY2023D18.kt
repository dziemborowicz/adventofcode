import LongPoint.Companion.DOWN
import LongPoint.Companion.LEFT
import LongPoint.Companion.RIGHT
import LongPoint.Companion.UP

class PuzzleY2023D18 : Puzzle {

  private val instructionRegex = Regex("""(\w) (\d+) \(#(\w+)\)""")

  private lateinit var input: String

  override fun parse(input: String) {
    this.input = input
  }

  override fun solve1(): Long {
    var current = LongPoint.ZERO
    val polygon = mutableListOf(current)
    for (instruction in input.lines()) {
      val (direction, distance, _) = instructionRegex.matchEntire(instruction)!!.destructured
      current += when (direction) {
        "R" -> RIGHT * distance.toLong()
        "D" -> DOWN * distance.toLong()
        "L" -> LEFT * distance.toLong()
        "U" -> UP * distance.toLong()
        else -> fail()
      }
      polygon.add(current)
    }
    return polygon.areaIncludingIntegralBoundary()
  }

  override fun solve2(): Long {
    var current = LongPoint.ZERO
    val polygon = mutableListOf(current)
    for (instruction in input.lines()) {
      val (_, _, hexValues) = instructionRegex.matchEntire(instruction)!!.destructured
      val distance = hexValues.take(5).toLong(16)
      current += when (hexValues.last()) {
        '0' -> RIGHT * distance
        '1' -> DOWN * distance
        '2' -> LEFT * distance
        '3' -> UP * distance
        else -> fail()
      }
      polygon.add(current)
    }
    return polygon.areaIncludingIntegralBoundary()
  }

  companion object {
    val testInput1 = """
      R 6 (#70c710)
      D 5 (#0dc571)
      L 2 (#5713f0)
      D 2 (#d2c081)
      R 2 (#59c680)
      D 2 (#411b91)
      L 5 (#8ceee2)
      U 2 (#caa173)
      L 1 (#1b58a2)
      U 2 (#caa171)
      R 2 (#7807d2)
      U 3 (#a77fa3)
      L 2 (#015232)
      U 2 (#7a21e3)
      """.trimIndent()
    val testAnswer1 = 62

    val testInput2 = testInput1
    val testAnswer2 = 952408144115
  }
}
