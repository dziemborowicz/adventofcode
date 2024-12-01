import kotlin.math.atan2

class PuzzleY2019D10 : Puzzle {

  private lateinit var map: Grid<Char>
  private lateinit var asteroids: List<Index>
  private lateinit var station: Index

  override fun parse(input: String) {
    map = input.parseDenseCharGrid()
    asteroids = map.indices.filter { map[it] == '#' }
    station = asteroids.maxBy { asteroidsFrom(it).size }
  }

  override fun solve1(): Int = asteroidsFrom(station).size

  override fun solve2(): Int {
    val asteroids = asteroidsFrom(station).map { it.second.toMutableList() }
    val vaporized = mutableListOf<Index>()
    var i = 0
    while (vaporized.size < 200) {
      asteroids.getWrapped(i++).removeFirstOrNull()?.let { vaporized.add(it) }
    }
    return vaporized.last().row + (100 * vaporized.last().column)
  }

  private fun asteroidsFrom(index: Index): List<Pair<Double, List<Index>>> {
    return asteroids.filter { it != index }.sortedBy { index manhattanDistanceTo it }
      .groupBy { angleBetween(index, it) }.toList().sortedBy { (angle, _) -> angle }
  }

  private fun angleBetween(source: Index, destination: Index): Double {
    val direction = destination - source
    return -atan2(direction.column.toDouble(), direction.row.toDouble())
  }

  companion object {
    val testInput1_1 = """
      .#..#
      .....
      #####
      ....#
      ...##
      """.trimIndent()
    val testAnswer1_1 = 8

    val testInput1_2 = """
      ......#.#.
      #..#.#....
      ..#######.
      .#.#.###..
      .#..#.....
      ..#....#.#
      #..#....#.
      .##.#..###
      ##...#..#.
      .#....####
      """.trimIndent()
    val testAnswer1_2 = 33

    val testInput1_3 = """
      #.#...#.#.
      .###....#.
      .#....#...
      ##.#.#.#.#
      ....#.#.#.
      .##..###.#
      ..#...##..
      ..##....##
      ......#...
      .####.###.
      """.trimIndent()
    val testAnswer1_3 = 35

    val testInput1_4 = """
      .#..#..###
      ####.###.#
      ....###.#.
      ..###.##.#
      ##.##.#.#.
      ....###..#
      ..#.#..#.#
      #..#.#.###
      .##...##.#
      .....#.#..
      """.trimIndent()
    val testAnswer1_4 = 41

    val testInput1_5 = """
      .#..##.###...#######
      ##.############..##.
      .#.######.########.#
      .###.#######.####.#.
      #####.##.#.##.###.##
      ..#####..#.#########
      ####################
      #.####....###.#.#.##
      ##.#################
      #####.##.###..####..
      ..######..##.#######
      ####.##.####...##..#
      .#####..#.######.###
      ##...#.##########...
      #.##########.#######
      .####.#.###.###.#.##
      ....##.##.###..#####
      .#.#.###########.###
      #.#.#.#####.####.###
      ###.##.####.##.#..##
      """.trimIndent()
    val testAnswer1_5 = 210

    val testInput2 = testInput1_5
    val testAnswer2 = 802
  }
}
