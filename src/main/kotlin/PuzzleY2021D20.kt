class PuzzleY2021D20 : Puzzle {

  private lateinit var imageEnhancementAlgorithm: String
  private lateinit var inputImage: Grid<Char>

  override fun parse(input: String) {
    val parts = input.lines().splitByBlank()
    imageEnhancementAlgorithm = parts[0].single()
    inputImage = parts[1].parseDenseCharGrid()
  }

  override fun solve1(): Int {
    return inputImage.enhance(2).count { it == '#' }
  }

  override fun solve2(): Int {
    return inputImage.enhance(50).count { it == '#' }
  }

  private fun Grid<Char>.enhance(times: Int): Grid<Char> {
    var enhanced = this
    var padding = '.'
    repeat(times) {
      enhanced = enhanced.enhance(padding)
      padding = if (padding == '#') {
        imageEnhancementAlgorithm.last()
      } else {
        imageEnhancementAlgorithm.first()
      }
    }
    return enhanced
  }

  private fun Grid<Char>.enhance(padding: Char): Grid<Char> {
    return Grid(numRows + 4, numColumns + 4) { (row, column) ->
      val index = (row - 3..row - 1).flatMap { r ->
        (column - 3..column - 1).map { c -> getOrElse(r, c) { padding } }
      }.joinToString("") { if (it == '#') "1" else "0" }.toInt(2)
      imageEnhancementAlgorithm[index]
    }
  }

  companion object {
    val testInput1 = """
      ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
      
      #..#.
      #....
      ##..#
      ..#..
      ..###
      """.trimIndent()
    val testAnswer1 = 35

    val testInput2 = testInput1
    val testAnswer2 = 3351
  }
}
