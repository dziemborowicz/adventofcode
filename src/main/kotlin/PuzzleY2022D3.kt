class PuzzleY2022D3 : Puzzle {

  private lateinit var rucksacks: List<Pair<Set<Char>, Set<Char>>>

  override fun parse(input: String) {
    rucksacks = input.lines().map {
      val half = it.length / 2
      Pair(it.take(half).toSet(), it.takeLast(half).toSet())
    }
  }

  override fun solve1(): Int {
    return rucksacks.sumOf { (it.first intersect it.second).single().priority() }
  }

  override fun solve2(): Int {
    return rucksacks.chunked(3).sumOf {
      it.map { it.first union it.second }.reduce { a, b -> a intersect b }.single().priority()
    }
  }

  private fun Char.priority(): Int {
    return if (this.isLowerCase()) {
      this - 'a' + 1
    } else {
      this - 'A' + 27
    }
  }

  companion object {
    val testInput1 = """
      vJrwpWtwJgWrhcsFMMfFFhFp
      jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
      PmmdzqPrVvPwwTWBwg
      wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
      ttgJtRGJQctTZtZT
      CrZsJsPPZsGzwwsLwLmpwMDw
      """.trimIndent()
    val testAnswer1 = 157

    val testInput2 = testInput1
    val testAnswer2 = 70
  }
}
