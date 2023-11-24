class PuzzleY2022D17 : Puzzle {

  private lateinit var rocks: List<Grid<Boolean>>
  private lateinit var jetPattern: String

  override fun parse(input: String) {
    val rockInput = """
      ####
      
      .#.
      ###
      .#.
      
      ..#
      ..#
      ###
      
      #
      #
      #
      #
      
      ##
      ##
      """.trimIndent()

    rocks = rockInput.lines().splitByBlank().map { lines ->
      lines.parseDenseCharGrid().map { it == '#' }
    }
    jetPattern = input
  }

  override fun solve1(): Int {
    return towerHeightAfterEachRockUpTo(2022).last()
  }

  override fun solve2(): Long {
    val heights = towerHeightAfterEachRockUpTo(10_000)
    val deltas = heights.windowed(2).map { (a, b) -> b - a }
    val cycleLength = (deltas.size / 2 downTo 1).firstNotNullOf { cycleLength ->
      val a = deltas.subList(deltas.size - cycleLength, deltas.size)
      val b = deltas.subList(deltas.size - (cycleLength * 2), deltas.size - cycleLength)
      if (a == b) cycleLength else null
    }

    val numRocks = 1_000_000_000_000
    val deltasPrefix = deltas.take(deltas.size - (cycleLength * 2))
    val deltasCycle = deltas.takeLast(cycleLength)
    val numCycles = (numRocks - deltasPrefix.size) / deltasCycle.size
    val suffixLength = (numRocks - deltasPrefix.size) % deltasCycle.size
    return deltasPrefix.sum() +
      (deltasCycle.sum() * numCycles) +
      deltasCycle.take(suffixLength.toInt()).sum()
  }

  private fun towerHeightAfterEachRockUpTo(n: Int): List<Int> {
    val chamber = Grid(n * rocks.maxOf { it.numRows }, 7, false)
    val heights = mutableListOf(0)
    var rockIndex = 0
    var jetPatternIndex = 0
    repeat(n) {
      val rock = rocks[rockIndex]
      rockIndex = (rockIndex + 1) % rocks.size

      var offset = Point(2, heights.last() + 3)
      while (true) {
        // Try to move left or right.
        val jet = jetPattern[jetPatternIndex]
        jetPatternIndex = (jetPatternIndex + 1) % jetPattern.length
        if (jet == '<' && chamber.isValidLocation(rock, offset.left())) {
          offset = offset.left()
        } else if (jet == '>' && chamber.isValidLocation(rock, offset.right())) {
          offset = offset.right()
        }

        // Try to move down.
        if (!chamber.isValidLocation(rock, offset.down())) break
        offset = offset.down()
      }

      chamber.place(rock, offset)
      heights += maxOf(heights.last(), rock.numRows + offset.y)
    }
    return heights
  }

  private fun Grid<Boolean>.isValidLocation(rock: Grid<Boolean>, offset: Point): Boolean {
    rock.forEachIndexed { (row, column), isSet ->
      if (isSet) {
        val x = column + offset.x
        val y = rock.numRows - row + 1 + offset.y
        if (this.getOrNull(this.numRows - y + 1, x) != false) return false
      }
    }
    return true
  }

  private fun Grid<Boolean>.place(rock: Grid<Boolean>, offset: Point) {
    rock.forEachIndexed { (row, column), isSet ->
      if (isSet) {
        val x = column + offset.x
        val y = rock.numRows - row + 1 + offset.y
        this[this.numRows - y + 1, x] = true
      }
    }
  }

  companion object {
    val testInput1 = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
    val testAnswer1 = 3068

    val testInput2 = testInput1
    val testAnswer2 = 1514285714288
  }
}
