class PuzzleY2023D12 : Puzzle {

  private data class Record(val conditions: String, val groups: List<Long>)

  private lateinit var records: List<Record>

  override fun parse(input: String) {
    records = input.lines().map { line ->
      val (conditions, groups) = line.split(' ')
      Record(conditions, groups.extractLongs())
    }
  }

  override fun solve1(): Long {
    return records.sumOf { possibleConfigurations(it) }
  }


  override fun solve2(): Long {
    return records.sumOf { possibleConfigurations(it.unfold()) }
  }

  private fun possibleConfigurations(record: Record): Long {
    val visited = hashMapOf<Triple<Int, Int, Long>, Long>()

    fun possibleConfigurations(conditions: String, groups: List<Long>, count: Long): Long {
      return visited.computeIfAbsentInline(Triple(conditions.length, groups.size, count)) {
        if (conditions.isEmpty()) {
          when {
            count == 0L && groups.isEmpty() -> 1L
            groups.singleOrNull() == count -> 1L
            else -> 0L
          }
        } else {
          val possibleConditions = when (conditions.first()) {
            '.' -> listOf('.')
            '#' -> listOf('#')
            '?' -> listOf('.', '#')
            else -> fail()
          }
          possibleConditions.sumOf { condition ->
            when (condition) {
              '.' -> {
                when {
                  count == 0L ->
                    possibleConfigurations(conditions.substring(1), groups, 0L)
                  count == groups.firstOrNull() ->
                    possibleConfigurations(conditions.substring(1), groups.drop(1), 0L)
                  else -> 0L
                }
              }
              '#' -> possibleConfigurations(conditions.substring(1), groups, count + 1)
              else -> fail()
            }
          }
        }
      }!!
    }

    return possibleConfigurations(record.conditions, record.groups, 0L)
  }

  private fun Record.unfold() = Record(
    "$conditions?$conditions?$conditions?$conditions?$conditions",
    groups + groups + groups + groups + groups,
  )

  companion object {
    val testInput1 = """
      ???.### 1,1,3
      .??..??...?##. 1,1,3
      ?#?#?#?#?#?#?#? 1,3,1,6
      ????.#...#... 4,1,1
      ????.######..#####. 1,6,5
      ?###???????? 3,2,1
      """.trimIndent()
    val testAnswer1 = 21

    val testInput2 = testInput1
    val testAnswer2 = 525152
  }
}
