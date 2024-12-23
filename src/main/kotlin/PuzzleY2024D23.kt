class PuzzleY2024D23 : Puzzle {

  private class Computer(val name: String) {
    val connections = hashSetOf<Computer>()
  }

  private lateinit var computers: Map<String, Computer>

  override fun parse(input: String) {
    computers = input.extractStrings().associateWith { Computer(it) }
    input.extractStringLists().map { (a, b) ->
      computers.getValue(a).connections += computers.getValue(b)
      computers.getValue(b).connections += computers.getValue(a)
    }
  }

  override fun solve1(): Int {
    val parties = hashSetOf<HashSet<Computer>>()
    for (a in computers.values.filter { it.name.startsWith('t') }) {
      for (b in a.connections) {
        for (c in b.connections.filter { it in a.connections }) {
          parties += hashSetOf(a, b, c)
        }
      }
    }
    return parties.size
  }

  override fun solve2(): String {
    val parties = mutableListOf<Set<Computer>>()
    fun search(party: Set<Computer>) {
      if (parties.none { it.containsAll(party) }) {
        parties += party
        for (computer in party) {
          for (neighbor in computer.connections) {
            if (party.all { it in neighbor.connections }) {
              search(party + neighbor)
            }
          }
        }
      }
    }
    computers.values.forEach { search(setOf(it)) }
    return parties.maxBy { it.size }.map { it.name }.sorted().joinToString(",")
  }

  companion object {
    val testInput1 = """
      kh-tc
      qp-kh
      de-cg
      ka-co
      yn-aq
      qp-ub
      cg-tb
      vc-aq
      tb-ka
      wh-tc
      yn-cg
      kh-ub
      ta-co
      de-co
      tc-td
      tb-wq
      wh-td
      ta-ka
      td-qp
      aq-cg
      wq-ub
      ub-vc
      de-ta
      wq-aq
      wq-vc
      wh-yn
      ka-de
      kh-ta
      co-tc
      wh-qp
      tb-vc
      td-yn
      """.trimIndent()
    val testAnswer1 = 7

    val testInput2 = testInput1
    val testAnswer2 = "co,de,ka,ta"
  }
}
