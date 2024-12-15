import Index.Companion.DOWN
import Index.Companion.LEFT
import Index.Companion.RIGHT
import Index.Companion.UP

class PuzzleY2024D15 : Puzzle {

  private lateinit var warehouse: Grid<Char>
  private lateinit var movements: List<Index>

  override fun parse(input: String) {
    val (warehouseLines, movementsLines) = input.lines().splitByBlank()
    warehouse = warehouseLines.parseDenseCharGrid()
    movements = movementsLines.joinToString("").map {
      when (it) {
        '>' -> RIGHT
        '<' -> LEFT
        '^' -> UP
        'v' -> DOWN
        else -> fail()
      }
    }
  }

  override fun solve1(): Int = solve(warehouse)

  override fun solve2(): Int {
    val widenedWarehouse = warehouse.map {
      when (it) {
        '.' -> "..".parseDenseCharGrid()
        '#' -> "##".parseDenseCharGrid()
        'O' -> "[]".parseDenseCharGrid()
        '@' -> "@.".parseDenseCharGrid()
        else -> fail()
      }
    }.flattenToGrid()
    return solve(widenedWarehouse)
  }

  private fun solve(warehouse: Grid<Char>): Int {
    var warehouse = warehouse.copy()
    for (movement in movements) {
      val updatedWarehouse = warehouse.copy()
      if (move(updatedWarehouse, warehouse.indexOf('@'), movement)) {
        warehouse = updatedWarehouse
      }
    }
    return warehouse.indices.filter { warehouse[it] in "O[" }.sumOf {
      (it.row * 100) + it.column
    }
  }

  private fun move(warehouse: Grid<Char>, index: Index, movement: Index): Boolean {
    when (warehouse[index]) {
      '.' -> return true

      '#' -> return false

      '@', 'O' -> {
        if (!move(warehouse, index + movement, movement)) return false
        warehouse[index + movement] = warehouse[index]
        warehouse[index] = '.'
      }

      '[' -> {
        if (movement in listOf(LEFT, RIGHT)) {
          if (!move(warehouse, index + movement, movement)) return false
          warehouse[index + movement] = '['
          warehouse[index] = '.'
        } else {
          if (!move(warehouse, index + movement, movement)) return false
          if (!move(warehouse, index + RIGHT + movement, movement)) return false
          warehouse[index + movement] = '['
          warehouse[index + RIGHT + movement] = ']'
          warehouse[index] = '.'
          warehouse[index + RIGHT] = '.'
        }
      }

      ']' -> {
        if (movement in listOf(LEFT, RIGHT)) {
          if (!move(warehouse, index + movement, movement)) return false
          warehouse[index + movement] = ']'
          warehouse[index] = '.'
        } else {
          if (!move(warehouse, index + movement, movement)) return false
          if (!move(warehouse, index + LEFT + movement, movement)) return false
          warehouse[index + movement] = ']'
          warehouse[index + LEFT + movement] = '['
          warehouse[index] = '.'
          warehouse[index + LEFT] = '.'
        }
      }

      else -> fail()
    }
    return true
  }

  companion object {
    val testInput1 = """
      ##########
      #..O..O.O#
      #......O.#
      #.OO..O.O#
      #..O@..O.#
      #O#..O...#
      #O..O..O.#
      #.OO.O.OO#
      #....O...#
      ##########
      
      <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
      vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
      ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
      <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
      ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
      ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
      >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
      <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
      ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
      v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^
      """.trimIndent()
    val testAnswer1 = 10092

    val testInput2 = testInput1
    val testAnswer2 = 9021
  }
}
