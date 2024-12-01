class PuzzleY2019D6 : Puzzle {

  private class SpaceObject {
    var parent: SpaceObject? = null
    val satellites = mutableSetOf<SpaceObject>()
    var level = -1
  }

  private lateinit var spaceObjects: MutableMap<String, SpaceObject>

  override fun parse(input: String) {
    spaceObjects = mutableMapOf()

    for ((a, b) in input.lines().map { it.split(')').toPair() }) {
      val aObj = spaceObjects.computeIfAbsent(a) { SpaceObject() }
      val bObj = spaceObjects.computeIfAbsent(b) { SpaceObject() }
      bObj.parent = aObj
      aObj.satellites += bObj
    }

    val queue = dequeOf(spaceObjects.getValue("COM") to 0)
    while (queue.isNotEmpty()) {
      val (obj, level) = queue.removeFirst()
      obj.level = level
      queue.addAll(obj.satellites.map { it to (level + 1) })
    }
  }

  override fun solve1(): Int = spaceObjects.values.sumOf { it.level }

  override fun solve2(): Int {
    val source = spaceObjects.getValue("YOU").parent!!
    val destination = spaceObjects.getValue("SAN").parent!!
    return source.level + destination.level - (commonParentOf(source, destination).level * 2)
  }

  private fun commonParentOf(a: SpaceObject, b: SpaceObject): SpaceObject {
    var aCurr = a
    var bCurr = b
    while (aCurr !== bCurr) {
      if (aCurr.level > bCurr.level) {
        aCurr = aCurr.parent!!
      } else {
        bCurr = bCurr.parent!!
      }
    }
    return aCurr
  }

  companion object {
    val testInput1 = """
      COM)B
      B)C
      C)D
      D)E
      E)F
      B)G
      G)H
      D)I
      E)J
      J)K
      K)L
      """.trimIndent()
    val testAnswer1 = 42

    val testInput2 = """
      COM)B
      B)C
      C)D
      D)E
      E)F
      B)G
      G)H
      D)I
      E)J
      J)K
      K)L
      K)YOU
      I)SAN
      """.trimIndent()
    val testAnswer2 = 4
  }
}
