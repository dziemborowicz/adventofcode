class PuzzleY2023D19 : Puzzle {

  private data class Rule(val index: Int, val range: IntRange, val target: String)

  private lateinit var workflows: Map<String, List<Rule>>
  private lateinit var parts: List<List<Int>>

  override fun parse(input: String) {
    val (workflowStrings, partStrings) = input.lines().splitByBlank()
    workflows = workflowStrings.associate { line ->
      val (label, rules) = Regex("""(\w+)\{(.*)}""").matchEntire(line)!!.destructured
      label to rules.split(",").map { rule ->
        val match = Regex("""(\w)([<>])(\d+):(\w+)""").matchEntire(rule)
        if (match != null) {
          val (field, op, value, target) = match.destructured
          Rule(
            "xmas".indexOf(field.single()),
            if (op == ">") (value.toInt() + 1)..Int.MAX_VALUE else Int.MIN_VALUE..(value.toInt() - 1),
            target,
          )
        } else {
          Rule(0, Int.RANGE, rule)
        }
      }
    }
    parts = partStrings.map { it.extractInts() }
  }

  override fun solve1(): Int = parts.filter { isAccepted(it) }.sumOf { it.sum() }

  override fun solve2(): Long {
    val range = (1..4000).toIntRangeSet()
    return countAccepted(listOf(range, range, range, range))
  }

  private fun isAccepted(values: List<Int>): Boolean {
    return countAccepted(values.map { it.toIntRangeSet() }) != 0L
  }

  private fun countAccepted(values: List<IntRangeSet>, label: String = "in"): Long {
    if (label == "R") return 0L
    if (label == "A") return values.productOf { it.count() }
    val remaining = values.copy()
    return workflows.getValue(label).sumOf { rule ->
      val next = remaining.copy().also { it[rule.index].retainAll(rule.range) }
      remaining[rule.index] -= rule.range
      countAccepted(next, rule.target)
    }
  }

  private fun List<IntRangeSet>.copy() = map { it.copy() }

  companion object {
    val testInput1 = """
      px{a<2006:qkq,m>2090:A,rfg}
      pv{a>1716:R,A}
      lnx{m>1548:A,A}
      rfg{s<537:gd,x>2440:R,A}
      qs{s>3448:A,lnx}
      qkq{x<1416:A,crn}
      crn{x>2662:A,R}
      in{s<1351:px,qqz}
      qqz{s>2770:qs,m<1801:hdj,R}
      gd{a>3333:R,R}
      hdj{m>838:A,pv}
      
      {x=787,m=2655,a=1222,s=2876}
      {x=1679,m=44,a=2067,s=496}
      {x=2036,m=264,a=79,s=2244}
      {x=2461,m=1339,a=466,s=291}
      {x=2127,m=1623,a=2188,s=1013}
      """.trimIndent()
    val testAnswer1 = 19114

    val testInput2 = testInput1
    val testAnswer2 = 167409079868000
  }
}
