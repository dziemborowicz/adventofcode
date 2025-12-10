class PuzzleY2025D10 : Puzzle {

  private data class Schematic(
    val goal: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltageRequirement: List<Int>,
  )

  private lateinit var schematics: List<Schematic>

  override fun parse(input: String) {
    schematics = input.lines().map { line ->
      val parts = line.split(' ')

      val goal = parts.first().drop(1).dropLast(1).map { it == '#' }
      val buttons = parts.drop(1).dropLast(1).map { it.extractInts() }
      val joltageRequirement = parts.last().extractInts()

      Schematic(goal, buttons, joltageRequirement)
    }
  }

  override fun solve1(): Int {
    return schematics.sumOf { schematic ->
      val queue = dequeOf(List(schematic.goal.size) { false } to 0)

      while (queue.isNotEmpty()) {
        val (state, presses) = queue.removeFirst()

        if (state == schematic.goal) return@sumOf presses

        for (machines in schematic.buttons) {
          val newState = state.mapIndexed { i, value ->
            if (i in machines) {
              !value
            } else {
              value
            }
          }
          queue += newState to (presses + 1)
        }
      }

      fail()
    }
  }

  /** Solved using equation solver. */
  override fun solve2() = NoAnswer

  companion object {
    val testInput1 = """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
      """.trimIndent()
    val testAnswer1 = 7

    val testInput2 = testInput1
    val testAnswer2 = 33
  }
}
