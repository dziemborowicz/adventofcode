class PuzzleY2022D16 : Puzzle {

  private data class Valve(val index: Int, val name: String, val rate: Int) {
    val neighbors: MutableList<Valve> = mutableListOf()
  }

  private lateinit var valvesByName: Map<String, Valve>
  private lateinit var valves: Collection<Valve>

  private val cache = Array((2 shl 6 + 5 + 15) - 1) { -1 }

  override fun parse(input: String) {
    val regex = Regex("""Valve (\w+) has flow rate=(-?\d+); tunnels? leads? to valves? (.*)""")
    valvesByName = input.lines()
      .map { regex.matchEntire(it)!!.destructured }
      .sortedByDescending { (_, rate, _) -> rate.toInt() }
      .mapIndexed { index, (name, rate, _) -> name to Valve(index, name, rate.toInt()) }
      .toMap()
    valves = valvesByName.values
    input.lines()
      .map { regex.matchEntire(it)!!.destructured }
      .forEach { (name, _, neighborNames) ->
        val value = valvesByName.getValue(name)
        value.neighbors.addAll(neighborNames.split(", ").map { valvesByName.getValue(it) })
      }
  }

  override fun solve1(): Int {
    val aa = valvesByName.getValue("AA")
    val canOpen = (2 shl valves.count { it.rate > 0 }) - 1
    return maxPressureReleased(aa, 30, canOpen)
  }

  override fun solve2(): Int {
    val aa = valvesByName.getValue("AA")
    val canOpen = (2 shl valves.count { it.rate > 0 }) - 1
    return (0..canOpen).maxOf { youCanOpen ->
      if (youCanOpen % 100 == 0) println("Working (${100 * youCanOpen / canOpen}%)...")
      val elephantCanOpen = youCanOpen.inv() and canOpen
      maxPressureReleased(aa, 26, youCanOpen) + maxPressureReleased(aa, 26, elephantCanOpen)
    }
  }

  private fun maxPressureReleased(valve: Valve, timeLeft: Int, canOpen: Int): Int {
    if (timeLeft <= 0) return 0
    val prev = cache[cacheKeyFor(valve, timeLeft, canOpen)]
    if (prev != -1) return prev

    var max = valve.neighbors.maxOf { maxPressureReleased(it, timeLeft - 1, canOpen) }
    if (valve.rate > 0 && valve in canOpen) {
      max = maxOf(max, ((timeLeft - 1) * valve.rate) + valve.neighbors.maxOf {
        maxPressureReleased(it, timeLeft - 2, canOpen - valve)
      })
    }
    cache[cacheKeyFor(valve, timeLeft, canOpen)] = max
    return max
  }

  private operator fun Int.contains(valve: Valve): Boolean {
    require(valve.index < 15) { "valve.index too large." }
    return ((1 shl valve.index) and this) == (1 shl valve.index)
  }

  private operator fun Int.minus(valve: Valve): Int {
    require(valve.index < 15) { "valve.index too large." }
    return (1 shl valve.index).inv() and this
  }

  private fun cacheKeyFor(valve: Valve, timeLeft: Int, leftToOpen: Int): Int {
    require(valve.index < 2 shl 6) { "valve.index too large." }
    require(timeLeft < 2 shl 5) { "timeLeft too large." }
    require(leftToOpen < 2 shl 15) { "leftToOpen too large." }
    return (valve.index shl (15 + 5)) or (timeLeft shl 15) or leftToOpen
  }

  companion object {
    val testInput1 = """
      Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
      Valve BB has flow rate=13; tunnels lead to valves CC, AA
      Valve CC has flow rate=2; tunnels lead to valves DD, BB
      Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
      Valve EE has flow rate=3; tunnels lead to valves FF, DD
      Valve FF has flow rate=0; tunnels lead to valves EE, GG
      Valve GG has flow rate=0; tunnels lead to valves FF, HH
      Valve HH has flow rate=22; tunnel leads to valve GG
      Valve II has flow rate=0; tunnels lead to valves AA, JJ
      Valve JJ has flow rate=21; tunnel leads to valve II
      """.trimIndent()
    val testAnswer1 = 1651

    val testInput2 = testInput1
    val testAnswer2 = 1707
  }
}
