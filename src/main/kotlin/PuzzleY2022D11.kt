class PuzzleY2022D11 : Puzzle {

  private data class Monkey(
    val items: ArrayDeque<Long>,
    val operation: (Long) -> Long,
    val divisor: Long,
    val monkeyIfTrue: Int,
    val monkeyIfFalse: Int,
    var inspectedCount: Long = 0,
  )

  private lateinit var monkeys: List<Monkey>

  override fun parse(input: String) {
    monkeys = input.lines().splitByBlank().map { lines ->
      Monkey(
        lines[1].removePrefix("  Starting items: ").split(", ").map { it.toLong() }.toDeque(),
        lines[2].removePrefix("  Operation: new = ").toOperation(),
        lines[3].removePrefix("  Test: divisible by ").toLong(),
        lines[4].removePrefix("    If true: throw to monkey ").toInt(),
        lines[5].removePrefix("    If false: throw to monkey ").toInt(),
      )
    }
  }

  override fun solve1(): Long {
    val monkeys = monkeys.copy()
    repeat(20) {
      monkeys.forEach {
        while (it.items.isNotEmpty()) {
          val worryLevel = it.operation(it.items.removeFirst()) / 3
          if (worryLevel % it.divisor == 0L) {
            monkeys[it.monkeyIfTrue].items.add(worryLevel)
          } else {
            monkeys[it.monkeyIfFalse].items.add(worryLevel)
          }
          it.inspectedCount++
        }
      }
    }
    return monkeys.sortedBy { it.inspectedCount }.takeLast(2).productOf { it.inspectedCount }
  }

  override fun solve2(): Long {
    val monkeys = monkeys.copy()
    val divisorProduct = monkeys.lcmOf { it.divisor }
    repeat(10_000) {
      monkeys.forEach {
        while (it.items.isNotEmpty()) {
          val worryLevel = it.operation(it.items.removeFirst()) % divisorProduct
          if (worryLevel % it.divisor == 0L) {
            monkeys[it.monkeyIfTrue].items.add(worryLevel)
          } else {
            monkeys[it.monkeyIfFalse].items.add(worryLevel)
          }
          it.inspectedCount++
        }
      }
    }
    return monkeys.sortedBy { it.inspectedCount }.takeLast(2).productOf { it.inspectedCount }
  }

  private fun String.toOperation(): (Long) -> Long {
    val (operator, operand) = Regex("""old (.) (-?\d+|old)""").matchEntire(this)!!.destructured
    return when (operator) {
      "+" -> { old -> old + (operand.toLongOrNull() ?: old) }
      "-" -> { old -> old - (operand.toLongOrNull() ?: old) }
      "*" -> { old -> old * (operand.toLongOrNull() ?: old) }
      "/" -> { old -> old / (operand.toLongOrNull() ?: old) }
      else -> fail()
    }
  }

  private fun List<Monkey>.copy() = map { it.copy(items = it.items.toDeque()) }

  companion object {
    val testInput1 = """
      Monkey 0:
      Starting items: 79, 98
      Operation: new = old * 19
      Test: divisible by 23
        If true: throw to monkey 2
        If false: throw to monkey 3
    
    Monkey 1:
      Starting items: 54, 65, 75, 74
      Operation: new = old + 6
      Test: divisible by 19
        If true: throw to monkey 2
        If false: throw to monkey 0
    
    Monkey 2:
      Starting items: 79, 60, 97
      Operation: new = old * old
      Test: divisible by 13
        If true: throw to monkey 1
        If false: throw to monkey 3
    
    Monkey 3:
      Starting items: 74
      Operation: new = old + 3
      Test: divisible by 17
        If true: throw to monkey 0
        If false: throw to monkey 1
      """.trimIndent()
    val testAnswer1 = 10605

    val testInput2 = testInput1
    val testAnswer2 = 2713310158
  }
}
