class PuzzleY2022D5 : Puzzle {

  private lateinit var stacks: MutableList<ArrayDeque<Char>>
  private lateinit var moves: List<Triple<Int, Int, Int>>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()

    val crates = parts[0].dropLast(1).reversed()
    val numStacks = parts[0].last().trim().split(' ').last().toInt()
    stacks = MutableList(numStacks) { i ->
      ArrayDeque(crates.mapNotNull { it.getOrNull((i * 4) + 1) }.filter { it != ' ' })
    }

    moves = parts[1].map {
      val match = Regex("move (\\d+) from (\\d+) to (\\d+)").matchEntire(it)!!
      Triple(
        match.groupValues[1].toInt(),
        match.groupValues[2].toInt() - 1,
        match.groupValues[3].toInt() - 1,
      )
    }
  }

  override fun solve1(): String {
    val stacks = stacks.copy()
    for ((count, from, to) in moves) {
      repeat(count) {
        val top = stacks[from].removeLast()
        stacks[to].add(top)
      }
    }
    return stacks.topsAsString()
  }

  override fun solve2(): String {
    val stacks = stacks.copy()
    for ((count, from, to) in moves) {
      val top = stacks[from].removeManyLast(count)
      stacks[to].addAll(top)
    }
    return stacks.topsAsString()
  }

  private fun MutableList<ArrayDeque<Char>>.copy() = map { ArrayDeque(it) }.toMutableList()

  private fun MutableList<ArrayDeque<Char>>.topsAsString() =
    mapNotNull { it.lastOrNull() }.joinToString("")

  companion object {
    val testInput1 = """
          [D]
      [N] [C]
      [Z] [M] [P]
       1   2   3

      move 1 from 2 to 1
      move 3 from 1 to 3
      move 2 from 2 to 1
      move 1 from 1 to 2
      """.trimIndent()
    val testAnswer1 = "CMZ"

    val testInput2 = testInput1
    val testAnswer2 = "MCD"
  }
}
