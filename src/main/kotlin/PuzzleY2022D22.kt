import PuzzleY2022D22.Facing.DOWN
import PuzzleY2022D22.Facing.LEFT
import PuzzleY2022D22.Facing.RIGHT
import PuzzleY2022D22.Facing.UP

class PuzzleY2022D22 : Puzzle {

  private sealed interface Instruction

  private data object RotateClockwise : Instruction
  private data object RotateCounterclockwise : Instruction
  private data class Move(val distance: Int) : Instruction

  private enum class Facing { RIGHT, DOWN, LEFT, UP }

  private lateinit var map: Grid<Char>
  private lateinit var instructions: List<Instruction>
  private lateinit var startIndex: Index
  private lateinit var startFacing: Facing

  override fun parse(input: String) {
    val (mapString, instructionsString) = input.split("\n\n")
    map = mapString.parseDenseCharGrid()
    instructions = """\d+|L|R""".toRegex().findAll(instructionsString).toList().map {
      when (it.value) {
        "R" -> RotateClockwise
        "L" -> RotateCounterclockwise
        else -> Move(it.value.toInt())
      }
    }
    startIndex = map.indexOf('.')
    startFacing = RIGHT
  }

  override fun solve1(): Int {
    var index = startIndex
    var facing = startFacing

    for (instruction in instructions) {
      when (instruction) {
        is RotateClockwise -> facing = facing.rotateClockwise()
        is RotateCounterclockwise -> facing = facing.rotateCounterclockwise()
        is Move -> {
          repeat(instruction.distance) {
            var nextIndex = index
            do {
              nextIndex = facing.move(nextIndex).wrappedIn(map)
            } while (map[nextIndex] == ' ')
            if (map[nextIndex] != '#') {
              index = nextIndex
            }
          }
        }
      }
    }

    return passwordFrom(index, facing)
  }

  override fun solve2(): Int {
    // These only works for the sample input and the actual input shapes, not all possible cubes.
    val stiches = when (map.sideLength()) {
      4 -> listOf(
        Stitch(0, 0, 8, 11, UP, 4, 4, 3, 0, DOWN),
        Stitch(4, 4, 3, 0, UP, 0, 0, 8, 11, DOWN),

        Stitch(0, 3, 8, 8, LEFT, 4, 4, 4, 7, DOWN),
        Stitch(4, 4, 4, 7, UP, 0, 3, 8, 8, RIGHT),

        Stitch(0, 3, 11, 11, RIGHT, 11, 8, 15, 15, LEFT),
        Stitch(11, 8, 15, 15, RIGHT, 0, 3, 11, 11, LEFT),

        Stitch(4, 7, 11, 11, RIGHT, 8, 8, 15, 12, DOWN),
        Stitch(8, 8, 15, 12, UP, 4, 7, 11, 11, LEFT),

        Stitch(4, 7, 0, 0, LEFT, 11, 11, 15, 12, UP),
        Stitch(11, 11, 15, 12, DOWN, 4, 7, 0, 0, RIGHT),

        Stitch(7, 7, 0, 3, DOWN, 11, 11, 11, 8, UP),
        Stitch(11, 11, 11, 8, DOWN, 7, 7, 0, 3, UP),

        Stitch(8, 11, 8, 8, LEFT, 8, 8, 7, 4, UP),
        Stitch(8, 8, 7, 4, DOWN, 8, 11, 8, 8, RIGHT),
      )
      50 -> listOf(
        Stitch(50, 99, 50, 50, LEFT, 100, 100, 0, 49, DOWN),
        Stitch(100, 100, 0, 49, UP, 50, 99, 50, 50, RIGHT),

        Stitch(0, 49, 50, 50, LEFT, 149, 100, 0, 0, RIGHT),
        Stitch(149, 100, 0, 0, LEFT, 0, 49, 50, 50, RIGHT),

        Stitch(0, 0, 50, 99, UP, 150, 199, 0, 0, RIGHT),
        Stitch(150, 199, 0, 0, LEFT, 0, 0, 50, 99, DOWN),

        Stitch(0, 0, 100, 149, UP, 199, 199, 0, 49, UP),
        Stitch(199, 199, 0, 49, DOWN, 0, 0, 100, 149, DOWN),

        Stitch(49, 49, 100, 149, DOWN, 50, 99, 99, 99, LEFT),
        Stitch(50, 99, 99, 99, RIGHT, 49, 49, 100, 149, UP),

        Stitch(0, 49, 149, 149, RIGHT, 149, 100, 99, 99, LEFT),
        Stitch(149, 100, 99, 99, RIGHT, 0, 49, 149, 149, LEFT),

        Stitch(149, 149, 50, 99, DOWN, 150, 199, 49, 49, LEFT),
        Stitch(150, 199, 49, 49, RIGHT, 149, 149, 50, 99, UP),
      )
      else -> error("Unsupported input.")
    }

    var index = startIndex
    var facing = startFacing

    for (instruction in instructions) {
      when (instruction) {
        is RotateClockwise -> facing = facing.rotateClockwise()
        is RotateCounterclockwise -> facing = facing.rotateCounterclockwise()
        is Move -> {
          repeat(instruction.distance) {
            val stitch = stiches.firstOrNull {
              index.row in it.fromRows && index.column in it.fromColumns && facing == it.fromFacing
            }
            if (stitch == null) {
              val newIndex = facing.move(index)
              if (map[newIndex] == '.') {
                index = newIndex
              }
            } else {
              val newIndex = when (stitch.fromFacing) {
                stitch.toFacing, stitch.toFacing.rotateAround() -> {
                  Index(
                    index.row.remap(stitch.fromRows, stitch.toRows),
                    index.column.remap(stitch.fromColumns, stitch.toColumns),
                  )
                }
                else -> {
                  Index(
                    index.column.remap(stitch.fromColumns, stitch.toRows),
                    index.row.remap(stitch.fromRows, stitch.toColumns),
                  )
                }
              }
              if (map[newIndex] == '.') {
                index = newIndex
                facing = stitch.toFacing
              }
            }
          }
        }
      }
    }

    return passwordFrom(index, facing)
  }

  private fun passwordFrom(index: Index, facing: Facing) =
    (1000 * (index.row + 1)) + (4 * (index.column + 1)) + facing.ordinal

  private fun Facing.move(index: Index): Index {
    return when (this) {
      RIGHT -> index.right()
      DOWN -> index.down()
      LEFT -> index.left()
      UP -> index.up()
    }
  }

  private fun Facing.rotateAround() = Facing.entries[(ordinal + 2).mod(4)]

  private fun Facing.rotateClockwise() = Facing.entries[(ordinal + 1).mod(4)]

  private fun Facing.rotateCounterclockwise() = Facing.entries[(ordinal - 1).mod(4)]

  private fun Grid<Char>.sideLength() = sqrt(surfaceArea() / 6)

  private fun Grid<Char>.surfaceArea() = count { it != ' ' }

  private data class Stitch(
    val fromRows: IntProgression,
    val fromColumns: IntProgression,
    val fromFacing: Facing,
    val toRows: IntProgression,
    val toColumns: IntProgression,
    val toFacing: Facing,
  )

  private fun Stitch(
    fromRowsFirst: Int,
    fromRowsLast: Int,
    fromColumnsFirst: Int,
    fromColumnsLast: Int,
    fromFacing: Facing,
    toRowsFirst: Int,
    toRowsLast: Int,
    toColumnsFirst: Int,
    toColumnsLast: Int,
    toFacing: Facing,
  ): Stitch {
    return Stitch(
      fromRowsFirst upOrDownTo fromRowsLast,
      fromColumnsFirst upOrDownTo fromColumnsLast,
      fromFacing,
      toRowsFirst upOrDownTo toRowsLast,
      toColumnsFirst upOrDownTo toColumnsLast,
      toFacing,
    )
  }

  companion object {
    val testInput1 = """
              ...#
              .#..
              #...
              ....
      ...#.......#
      ........#...
      ..#....#....
      ..........#.
              ...#....
              .....#..
              .#......
              ......#.
      
      10R5L5R10L4R5L5
      """.trimIndent()
    val testAnswer1 = 6032

    val testInput2 = testInput1
    val testAnswer2 = 5031
  }
}
