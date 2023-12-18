private data class AsciiArtLettersSpec(val letters: String, val block: String)

private val asciiArtLettersSpecs = listOf(
  AsciiArtLettersSpec(
    "ABCEFGHIJKLOPRSUYZ",
    """
    .##..###...##..####.####..##..#..#.###...##.#..#.#.....##..###..###...###.#..#.#...#.####.
    #..#.#..#.#..#.#....#....#..#.#..#..#.....#.#.#..#....#..#.#..#.#..#.#....#..#.#...#....#.
    #..#.###..#....###..###..#....####..#.....#.##...#....#..#.#..#.#..#.#....#..#..#.#....#..
    ####.#..#.#....#....#....#.##.#..#..#.....#.#.#..#....#..#.###..###...##..#..#...#....#...
    #..#.#..#.#..#.#....#....#..#.#..#..#..#..#.#.#..#....#..#.#....#.#.....#.#..#...#...#....
    #..#.###...##..####.#.....###.#..#.###..##..#..#.####..##..#....#..#.###...##....#...####.
    """.trimIndent()
  ),
  AsciiArtLettersSpec(
    "ABCEFGHJKLNPRXZ",
    """
    ..##...#####...####..######.######..####..#....#....###.#....#.#......#....#.#####..#####..#....#.######.
    .#..#..#....#.#....#.#......#......#....#.#....#.....#..#...#..#......##...#.#....#.#....#.#....#......#.
    #....#.#....#.#......#......#......#......#....#.....#..#..#...#......##...#.#....#.#....#..#..#.......#.
    #....#.#....#.#......#......#......#......#....#.....#..#.#....#......#.#..#.#....#.#....#..#..#......#..
    #....#.#####..#......#####..#####..#......######.....#..##.....#......#.#..#.#####..#####....##......#...
    ######.#....#.#......#......#......#..###.#....#.....#..##.....#......#..#.#.#......#..#.....##.....#....
    #....#.#....#.#......#......#......#....#.#....#.....#..#.#....#......#..#.#.#......#...#...#..#...#.....
    #....#.#....#.#......#......#......#....#.#....#.#...#..#..#...#......#...##.#......#...#...#..#..#......
    #....#.#....#.#....#.#......#......#...##.#....#.#...#..#...#..#......#...##.#......#....#.#....#.#......
    #....#.#####...####..######.#.......###.#.#....#..###...#....#.######.#....#.#......#....#.#....#.######.
    """.trimIndent()
  ),
)

@JvmName("toStringFromAsciiArtCharGrid")
fun Grid<Char>.toStringFromAsciiArt(): String {
  val chars = values().toSet()
  check(chars.size == 2 && '.' in chars)
  return map { it != '.' }.toStringFromAsciiArt()
}

fun Grid<Boolean>.toStringFromAsciiArt(): String {
  render() // Prints in case the mapping doesn't work.
  val inputBlockLetters = splitByEmptyColumns()
  for (spec in asciiArtLettersSpecs) {
    val specBlockLetters = spec.block.parseDenseCharGrid().map { it != '.' }.splitByEmptyColumns()
    if (inputBlockLetters.all { it in specBlockLetters }) {
      return inputBlockLetters.map { spec.letters[specBlockLetters.indexOf(it)] }.joinToString("")
    }
  }
  fail("Could not parse string from ASCII art letters.")
}

private fun Grid<Boolean>.splitByEmptyColumns(): List<Grid<Boolean>> {
  val emptyColumns = listOf(-1) + columnIndices.filter { column(it).none { it } }
  return emptyColumns.windowed(2) { (a, b) -> subGrid(0, a + 1, -1, b - a - 1) }
}
