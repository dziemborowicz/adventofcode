class PuzzleY2021D1 : Puzzle {

  private lateinit var depths: List<Int>

  override fun parse(input: String) {
    depths = input.parseInts()
  }

  override fun solve1(): Int {
    return depths.windowed(2).count { it[1] > it[0] }
  }

  override fun solve2(): Int {
    return depths.windowed(3).map { it.sum() }.windowed(2).count { it[1] > it[0] }
  }
}
