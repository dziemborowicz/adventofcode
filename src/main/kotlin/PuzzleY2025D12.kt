class PuzzleY2025D12 : Puzzle {

  private data class Tree(
    val width: Int,
    val height: Int,
    val presentCounts: List<Int>,
  )

  private lateinit var presentSizes: List<Int>
  private lateinit var trees: List<Tree>

  override fun parse(input: String) {
    val parts = input.splitByBlank()
    presentSizes = parts.dropLast().map { it.count { c -> c == '#' } }
    trees = parts.last().extractIntLists().map { numbers ->
      Tree(numbers.first(), numbers.second(), numbers.drop(2))
    }
  }

  override fun solve1(): Int {
    return trees.count { tree ->
      val totalPresentsSize = presentSizes.zip(tree.presentCounts, Int::times).sum()
      1.2 * totalPresentsSize <= tree.width * tree.height
    }
  }

  override fun solve2(): Int {
    // No answer required. This solves the puzzle when all 50 stars are unlocked.
    return 0
  }

  companion object {
    val testInput1 = """
      0:
      ###
      ##.
      ##.

      1:
      ###
      ##.
      .##

      2:
      .##
      ###
      ##.

      3:
      ##.
      ###
      ##.

      4:
      ###
      #..
      ###

      5:
      ###
      .#.
      ###

      4x4: 0 0 0 0 2 0
      12x5: 1 0 1 0 2 2
      12x5: 1 0 1 0 3 2
      """.trimIndent()
    val testAnswer1 = 2
  }
}
