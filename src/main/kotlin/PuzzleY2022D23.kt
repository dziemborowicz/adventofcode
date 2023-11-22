import PuzzleY2022D23.Movement.DONE
import PuzzleY2022D23.Movement.EAST
import PuzzleY2022D23.Movement.NONE
import PuzzleY2022D23.Movement.NORTH
import PuzzleY2022D23.Movement.SOUTH
import PuzzleY2022D23.Movement.WEST

typealias Elf = Grid.Index

class PuzzleY2022D23 : Puzzle {

  private lateinit var elves: Set<Elf>

  override fun parse(input: String) {
    elves = buildSet {
      input.lines().forEachIndexed { row, line ->
        line.forEachIndexed { column, character ->
          if (character == '#') {
            add(Elf(row, column))
          }
        }
      }
    }
  }

  override fun solve1(): Int {
    val elves = elves.toMutableSet()
    repeat(10) { round -> evolve(elves, round) }
    return emptySpaceBetween(elves)
  }

  override fun solve2(): Int {
    val elves = elves.toMutableSet()
    var round = 0
    while (evolve(elves, round++)) {
      // Loop.
    }
    return round
  }


  private fun evolve(elves: MutableSet<Elf>, round: Int): Boolean {
    val movements = movementsOrderedFor(round)

    val proposals = mutableMapOf<Elf, MutableList<Elf>>()
    for (elf in elves) {
      val movedElf = movements.first { it.checks(elf).all { it !in elves } }.move(elf)
      proposals.getOrPut(movedElf) { mutableListOf() }.add(elf)
    }

    var changed = false
    for ((destination, sources) in proposals) {
      if (sources.size == 1 && sources.single() != destination) {
        elves.remove(sources.single())
        elves.add(destination)
        changed = true
      }
    }
    return changed
  }

  private fun emptySpaceBetween(elves: Set<Elf>): Int {
    val height = elves.maxOf { it.row } - elves.minOf { it.row } + 1
    val width = elves.maxOf { it.column } - elves.minOf { it.column } + 1
    return (height * width) - elves.size
  }

  private fun movementsOrderedFor(round: Int): List<Movement> {
    return when (round % 4) {
      0 -> listOf(DONE, NORTH, SOUTH, WEST, EAST, NONE)
      1 -> listOf(DONE, SOUTH, WEST, EAST, NORTH, NONE)
      2 -> listOf(DONE, WEST, EAST, NORTH, SOUTH, NONE)
      3 -> listOf(DONE, EAST, NORTH, SOUTH, WEST, NONE)
      else -> error()
    }
  }

  private enum class Movement(val checks: (Elf) -> List<Elf>, val move: (Elf) -> Elf) {
    DONE(
      checks = { it.adjacentWithDiagonals() },
      move = { it },
    ),
    NORTH(
      checks = { listOf(it.upLeft(), it.up(), it.upRight()) },
      move = { it.up() },
    ),
    SOUTH(
      checks = { listOf(it.downLeft(), it.down(), it.downRight()) },
      move = { it.down() },
    ),
    WEST(
      checks = { listOf(it.upLeft(), it.left(), it.downLeft()) },
      move = { it.left() },
    ),
    EAST(
      checks = { listOf(it.upRight(), it.right(), it.downRight()) },
      move = { it.right() },
    ),
    NONE(
      checks = { listOf() },
      move = { it },
    ),
  }

  companion object {
    val testInput1 = """
      ....#..
      ..###.#
      #...#.#
      .#...##
      #.###..
      ##.#.##
      .#..#..
      """.trimIndent()
    val testAnswer1 = 110

    val testInput2 = testInput1
    val testAnswer2 = 20
  }
}
