class PuzzleY2022D13 : Puzzle {

  private lateinit var pairs: List<Pair<List<Any>, List<Any>>>

  override fun parse(input: String) {
    pairs = input.lines().splitByBlank().map { lineGroup ->
      lineGroup.map { it.parseNestedIntList() }.toPair()
    }
  }

  override fun solve1(): Int {
    return pairs.sumOfIndexed { index, pair ->
      if (compare(pair.first, pair.second) < 0) {
        index + 1
      } else {
        0
      }
    }
  }

  override fun solve2(): Int {
    val separatorPackets = listOf("[[2]]".parseNestedIntList(), "[[6]]".parseNestedIntList())
    val allPackets = (pairs.flatten() + separatorPackets).sortedWith { a, b -> compare(a, b) }
    return separatorPackets.productOf { allPackets.indexOfOrThrow(it) + 1 }
  }

  private fun compare(a: Any?, b: Any?): Int {
    if (a is Int && b is Int) return a - b
    val firstList = if (a !is List<*>) listOf(a) else a
    val secondList = if (b !is List<*>) listOf(b) else b
    for ((first, second) in firstList.zip(secondList)) {
      val result = compare(first, second)
      if (result != 0) return result
    }
    return firstList.size - secondList.size
  }

  companion object {
    val testInput1 = """
      [1,1,3,1,1]
      [1,1,5,1,1]
      
      [[1],[2,3,4]]
      [[1],4]
      
      [9]
      [[8,7,6]]
      
      [[4,4],4,4]
      [[4,4],4,4,4]
      
      [7,7,7,7]
      [7,7,7]
      
      []
      [3]
      
      [[[]]]
      [[]]
      
      [1,[2,[3,[4,[5,6,7]]]],8,9]
      [1,[2,[3,[4,[5,6,0]]]],8,9]
      """.trimIndent()
    val testAnswer1 = 13

    val testInput2 = testInput1
    val testAnswer2 = 140
  }
}
