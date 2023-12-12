class PuzzleY2023D13 : Puzzle {

  private data class Reflections(val verticalReflection: Int?, val horizontalReflection: Int?)

  private lateinit var patterns: List<Grid<Char>>

  override fun parse(input: String) {
    patterns = input.lines().splitByBlank().map { it.parseDenseCharGrid() }
  }

  override fun solve1(): Int {
    return patterns.sumOf { pattern -> pattern.reflections()?.value ?: 0 }
  }

  override fun solve2(): Int {
    return patterns.sumOf { pattern ->
      val oldReflections = pattern.reflections()
      for (index in pattern.indices) {
        pattern.smudgeAt(index)
        val newReflections = pattern.reflections(exclude = oldReflections)
        pattern.unsmudgeAt(index)
        if (newReflections != null) {
          return@sumOf newReflections.value
        }
      }
      fail()
    }
  }

  private fun Grid<Char>.smudgeAt(index: Index) {
    if (this[index] == '#') {
      this[index] = '.'
    } else {
      this[index] = '#'
    }
  }

  private fun Grid<Char>.unsmudgeAt(index: Index) = smudgeAt(index)

  private fun Grid<Char>.reflections(exclude: Reflections? = null): Reflections? {
    return Reflections(
      verticalReflection(exclude = exclude?.verticalReflection),
      horizontalReflection(exclude = exclude?.horizontalReflection),
    ).also { if (it.verticalReflection == null && it.horizontalReflection == null) return null }
  }

  private fun Grid<Char>.verticalReflection(exclude: Int? = null): Int? {
    return columnIndices.windowed(2).filter { (_, b) -> b != exclude }.firstOrNull { (a, b) ->
      val leftSide = (a downTo 0)
      val rightSide = (b..columnIndices.last)
      (leftSide zip rightSide).all { (i, j) -> column(i) == column(j) }
    }?.get(1)
  }

  private fun Grid<Char>.horizontalReflection(exclude: Int? = null): Int? {
    return transposed().verticalReflection(exclude)
  }

  private val Reflections.value: Int
    get() = (verticalReflection ?: 0) + 100 * (horizontalReflection ?: 0)

  companion object {
    val testInput1 = """
      #.##..##.
      ..#.##.#.
      ##......#
      ##......#
      ..#.##.#.
      ..##..##.
      #.#.##.#.
      
      #...##..#
      #....#..#
      ..##..###
      #####.##.
      #####.##.
      ..##..###
      #....#..#
      """.trimIndent()
    val testAnswer1 = 405

    val testInput2 = testInput1
    val testAnswer2 = 400
  }
}
