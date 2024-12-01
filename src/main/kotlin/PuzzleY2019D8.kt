class PuzzleY2019D8 : Puzzle {

  private lateinit var layers: List<Grid<Boolean?>>

  override fun parse(input: String) {
    layers = input.chunked(25 * 6).map { layer ->
      Grid(6, 25) { (r, c) ->
        when (layer[c + (25 * r)]) {
          '0' -> false
          '1' -> true
          else -> null
        }
      }
    }
  }

  override fun solve1(): Any? {
    val layer = layers.minBy { it.count { it == false } }
    return layer.count { it == true } * layer.count { it == null }
  }

  override fun solve2(): String {
    val image = Grid(6, 25) { index -> layers.firstNotNullOf { it[index] } }
    return image.toStringFromAsciiArt()
  }
}
