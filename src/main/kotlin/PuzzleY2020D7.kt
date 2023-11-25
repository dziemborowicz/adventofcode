class PuzzleY2020D7 : Puzzle {

  private data class Rule(
    val color: String,
    val contents: List<Pair<Int, String>>,
  )

  private lateinit var rules: Map<String, Rule>

  override fun parse(input: String) {
    rules = input.lines().map { line ->
      Rule(
        color = Regex("""\w+ \w+""").find(line)!!.value,
        contents = Regex("""(\d+) (\w+ \w+) bags?""").findAll(line).map {
          val (count, color) = it.destructured
          count.toInt() to color
        }.toList()
      )
    }.associateBy { it.color }
  }

  override fun solve1(): Int {
    return colorsContaining("shiny gold").size
  }

  override fun solve2(): Int {
    return countContents("shiny gold")
  }

  private fun colorsDirectlyContaining(color: String): List<String> {
    return rules.values.filter { it.contents.any { it.second == color } }.map { it.color }
  }

  private val colorsContaining = DeepRecursiveFunction<String, Set<String>> {
    colorsDirectlyContaining(it).unionOf { callRecursive(it) + it }
  }

  private val countContents = DeepRecursiveFunction<String, Int> {
    rules.getValue(it).contents.sumOf { (count, color) -> count * (1 + callRecursive(color)) }
  }

  companion object {
    val testInput1 = """
      light red bags contain 1 bright white bag, 2 muted yellow bags.
      dark orange bags contain 3 bright white bags, 4 muted yellow bags.
      bright white bags contain 1 shiny gold bag.
      muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
      shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
      dark olive bags contain 3 faded blue bags, 4 dotted black bags.
      vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
      faded blue bags contain no other bags.
      dotted black bags contain no other bags.
      """.trimIndent()
    val testAnswer1 = 4

    val testInput2_1 = testInput1
    val testAnswer2_1 = 32

    val testInput2_2 = """
      shiny gold bags contain 2 dark red bags.
      dark red bags contain 2 dark orange bags.
      dark orange bags contain 2 dark yellow bags.
      dark yellow bags contain 2 dark green bags.
      dark green bags contain 2 dark blue bags.
      dark blue bags contain 2 dark violet bags.
      dark violet bags contain no other bags.
      """.trimIndent()
    val testAnswer2_2 = 126
  }
}
