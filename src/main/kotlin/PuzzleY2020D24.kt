class PuzzleY2020D24 : Puzzle {

  private val directionDeltas = mapOf(
    "e" to DoublePoint(1.0, 0.0),
    "se" to DoublePoint(0.5, -1.0),
    "sw" to DoublePoint(-0.5, -1.0),
    "w" to DoublePoint(-1.0, 0.0),
    "nw" to DoublePoint(-0.5, 1.0),
    "ne" to DoublePoint(0.5, 1.0),
  )

  private lateinit var directionsLists: List<List<String>>

  override fun parse(input: String) {
    directionsLists = input.lines().map { line ->
      Regex("e|se|sw|w|nw|ne").findAll(line).map { it.value }.toList()
    }
  }

  override fun solve1(): Int {
    val blackTiles = renovate()
    return blackTiles.size
  }

  override fun solve2(): Int {
    var blackTiles = renovate()
    repeat(100) { blackTiles = evolve(blackTiles) }
    return blackTiles.size
  }

  private fun renovate(): Set<DoublePoint> {
    val blackTiles = mutableSetOf<DoublePoint>()
    for (directions in directionsLists) {
      val tile = directions.fold(DoublePoint.ZERO) { tile, direction ->
        tile + directionDeltas.getValue(direction)
      }
      if (tile in blackTiles) {
        blackTiles -= tile
      } else {
        blackTiles += tile
      }
    }
    return blackTiles
  }

  private fun evolve(blackTiles: Set<DoublePoint>): Set<DoublePoint> {
    val nearByWhiteTiles =
      blackTiles.flatMap { it.tileNeighbors() }.toSet().filter { it !in blackTiles }

    val tilesToAdd = nearByWhiteTiles.filter { it.countTileNeighborsIn(blackTiles) == 2 }
    val tilesToRemove =
      blackTiles.filter { it.countTileNeighborsIn(blackTiles).let { it == 0 || it > 2 } }

    return blackTiles + tilesToAdd - tilesToRemove
  }

  private fun DoublePoint.countTileNeighborsIn(set: Set<DoublePoint>): Int =
    tileNeighbors().count { it in set }

  private fun DoublePoint.tileNeighbors(): List<DoublePoint> =
    directionDeltas.values.map { this + it }

  companion object {
    val testInput1 = """
      sesenwnenenewseeswwswswwnenewsewsw
      neeenesenwnwwswnenewnwwsewnenwseswesw
      seswneswswsenwwnwse
      nwnwneseeswswnenewneswwnewseswneseene
      swweswneswnenwsewnwneneseenw
      eesenwseswswnenwswnwnwsewwnwsene
      sewnenenenesenwsewnenwwwse
      wenwwweseeeweswwwnwwe
      wsweesenenewnwwnwsenewsenwwsesesenwne
      neeswseenwwswnwswswnw
      nenwswwsewswnenenewsenwsenwnesesenew
      enewnwewneswsewnwswenweswnenwsenwsw
      sweneswneswneneenwnewenewwneswswnese
      swwesenesewenwneswnwwneseswwne
      enesenwswwswneneswsenwnewswseenwsese
      wnwnesenesenenwwnenwsewesewsesesew
      nenewswnwewswnenesenwnesewesw
      eneswnwswnwsenenwnwnwwseeswneewsenese
      neswnwewnwnwseenwseesewsenwsweewe
      wseweeenwnesenwwwswnew
      """.trimIndent()
    val testAnswer1 = 10

    val testInput2 = testInput1
    val testAnswer2 = 2208
  }
}
