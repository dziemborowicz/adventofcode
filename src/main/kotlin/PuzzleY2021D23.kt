import kotlin.math.abs

class PuzzleY2021D23 : Puzzle {

  private lateinit var amphipods: MutableList<ArrayDeque<Char>>

  override fun parse(input: String) {
    val lines = input.lines()
    amphipods = MutableList(11) { i ->
      lines.map { line -> line.getOrElse(i + 1) { ' ' } }.filter { it.isLetter() }.asDeque()
    }
  }

  override fun solve1(): Int {
    return solve(amphipods)
  }

  override fun solve2(): Int {
    val amphipods = amphipods.deepCopy()
    amphipods[2].add(1, 'D')
    amphipods[2].add(2, 'D')
    amphipods[4].add(1, 'C')
    amphipods[4].add(2, 'B')
    amphipods[6].add(1, 'B')
    amphipods[6].add(2, 'A')
    amphipods[8].add(1, 'A')
    amphipods[8].add(2, 'C')
    return solve(amphipods)
  }

  private fun solve(initialState: MutableList<ArrayDeque<Char>>): Int {
    val burrowSize = initialState.maxOf { it.size }
    val finalState = MutableList(11) { i ->
      when (i) {
        0 -> ArrayDeque()
        1 -> ArrayDeque()
        2 -> ArrayDeque(List(burrowSize) { 'A' })
        3 -> ArrayDeque()
        4 -> ArrayDeque(List(burrowSize) { 'B' })
        5 -> ArrayDeque()
        6 -> ArrayDeque(List(burrowSize) { 'C' })
        7 -> ArrayDeque()
        8 -> ArrayDeque(List(burrowSize) { 'D' })
        9 -> ArrayDeque()
        10 -> ArrayDeque()
        else -> fail()
      }
    }
    val minEnergyUsed = hashMapOf<MutableList<ArrayDeque<Char>>, Int>()
    val queue = ArrayDeque<Pair<MutableList<ArrayDeque<Char>>, Int>>()
    queue.add(initialState to 0)
    queue@ while (queue.isNotEmpty()) {
      val (amphipods, energyUsed) = queue.removeFirst()
      if (minEnergyUsed.getOrDefault(amphipods, Int.MAX_VALUE) <= energyUsed) continue@queue
      minEnergyUsed[amphipods] = energyUsed

      // Try moving to home.
      indices@ for (i in amphipods.indices) {
        if (amphipods[i].all { it.home() == i }) continue@indices
        val home = amphipods[i].first().home()
        if (amphipods[home].any { it.home() != home }) continue@indices
        var j = if (i < home) i + 1 else i - 1
        while (j != home) {
          if (j.isHallway() && amphipods[j].isNotEmpty()) continue@indices
          j = if (j < home) j + 1 else j - 1
        }
        queue.add(amphipods.deepCopy().also {
          it[home].addFirst(it[i].removeFirst())
        } to energyUsed + energyToMove(from = i, to = home, with = amphipods, burrowSize))
      }

      // Try moving to hallway.
      indices@ for (i in amphipods.indices) {
        if (!i.isBurrow()) continue@indices
        if (amphipods[i].all { it.home() == i }) continue@indices

        // To the left.
        var dest = i
        while (--dest >= 0) {
          if (!dest.isHallway()) continue
          if (amphipods[dest].isNotEmpty()) break
          queue.add(amphipods.deepCopy().also {
            it[dest].addFirst(it[i].removeFirst())
          } to energyUsed + energyToMove(from = i, to = dest, with = amphipods, burrowSize))
        }

        // To the left.
        dest = i
        while (++dest < amphipods.size) {
          if (!dest.isHallway()) continue
          if (amphipods[dest].isNotEmpty()) break
          queue.add(amphipods.deepCopy().also {
            it[dest].addFirst(it[i].removeFirst())
          } to energyUsed + energyToMove(from = i, to = dest, with = amphipods, burrowSize))
        }
      }
    }
    return minEnergyUsed[finalState]!!
  }

  private fun Int.isHallway() = !isBurrow()

  private fun Int.isBurrow(): Boolean {
    return when (this) {
      0 -> false
      1 -> false
      2 -> true
      3 -> false
      4 -> true
      5 -> false
      6 -> true
      7 -> false
      8 -> true
      9 -> false
      10 -> false
      else -> fail()
    }
  }

  private fun energyToMove(
    from: Int,
    to: Int,
    with: MutableList<ArrayDeque<Char>>,
    burrowSize: Int,
  ): Int {
    check(burrowSize == 2 || burrowSize == 4)
    val distance =
      if (from.isBurrow()) {
        check(with[from].isNotEmpty())
        if (to.isHallway()) {
          check(with[to].isEmpty())
          1 + burrowSize - with[from].size + abs(from - to)
        } else {
          check(with[to].size < burrowSize)
          1 + burrowSize - with[from].size + abs(from - to) + burrowSize - with[to].size
        }
      } else {
        check(to.isBurrow())
        check(with[to].size < burrowSize)
        burrowSize - with[to].size + abs(from - to)
      }
    return with[from].first().energyToMove(distance)
  }

  private fun Char.energyToMove(distance: Int = 1): Int {
    return distance * when (this) {
      'A' -> 1
      'B' -> 10
      'C' -> 100
      'D' -> 1000
      else -> fail()
    }
  }

  private fun Char.home(): Int {
    return when (this) {
      'A' -> 2
      'B' -> 4
      'C' -> 6
      'D' -> 8
      else -> fail()
    }
  }

  private fun MutableList<ArrayDeque<Char>>.deepCopy() =
    MutableList(size) { this[it].asDeque() }

  companion object {
    val testInput1_1 = """
      #############
      #...........#
      ###B#C#B#D###
        #A#D#C#A#
        #########
      """.trimIndent()
    val testAnswer1_1 = 12521

    val testInput1_2 = """
      #############
      #...........#
      ###C#B#D#D###
        #B#C#A#A#
        #########
      """.trimIndent()
    val testAnswer1_2 = 10321

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 44169

    val testInput2_2 = testInput1_2
    val testAnswer2_2 = 46451
  }
}
