class PuzzleY2020D20 : Puzzle {

  private lateinit var images: Map<Long, Grid<Char>>

  override fun parse(input: String) {
    images = input.lines().splitByBlank().associate { lines ->
      val id = lines.first().extractLongs().single()
      val image = lines.drop(1).parseDenseCharGrid()
      id to image
    }
  }

  override fun solve1(): Long {
    return findCornerIds().product()
  }

  override fun solve2(): Int {
    val dragon = """
                        # 
      #    ##    ##    ###
       #  #  #  #  #  #   
      """.trimIndent().parseDenseCharGrid()
    val dragonIndices = dragon.indices.filter { dragon[it] == '#' }

    val size = sqrt(images.size)
    val grid = Grid<Grid<Char>?>(size, size, initialValue = null)
    for (index in grid.indices) {
      grid[index] = when {
        index == Index.ZERO -> findTopLeftCorner()
        index.row > 0 -> imageBelow(grid[index.up()]!!)
        else -> imageRightOf(grid[index.left()]!!)
      }
    }

    val completeImage = grid.map { it!!.shrink(1) }.flattenToGrid()
    return completeImage.flipsAndRotations().onEach { image ->
      for (row in image.rowIndices) {
        for (column in image.columnIndices) {
          val translatedDragon = dragonIndices.map { it.translate(row, column) }
          if (translatedDragon.all { image.getOrDefault(it, '.') != '.' }) {
            translatedDragon.forEach { image[it] = 'O' }
          }
        }
      }
    }.first { image -> image.any { it == 'O' } }.count { it == '#' }
  }

  private fun Grid<Char>.allEdges() = edges().flatMap { listOf(it, it.reversed()) }.toSet()

  private fun Grid<Char>.edges() =
    setOf(rowWrapped(0), rowWrapped(-1), columnWrapped(0), columnWrapped(-1))

  private fun findCorner(): Grid<Char> =
    images.values.first { image -> image.edges().count { it.isBoundary() } == 2 }

  private fun findCornerIds(): List<Long> =
    images.entries.filter { it.value.edges().count { it.isBoundary() } == 2 }.map { it.key }

  private fun findTopLeftCorner() = findCorner().rotationsAndFlips().first { it.isTopLeftCorner() }

  private fun imageBelow(image: Grid<Char>): Grid<Char> {
    return images.values.filter { it isNotRotationOrFlipOf image }
      .flatMap { it.rotationsAndFlips() }.first { it.row(0) == image.rowWrapped(-1) }
  }

  private fun imageRightOf(image: Grid<Char>): Grid<Char> {
    return images.values.filter { it isNotRotationOrFlipOf image }
      .flatMap { it.rotationsAndFlips() }.first { it.column(0) == image.columnWrapped(-1) }
  }

  private fun List<Char>.isBoundary(): Boolean = images.values.count { this in it.allEdges() } == 1

  private infix fun Grid<Char>.isNotRotationOrFlipOf(image: Grid<Char>): Boolean =
    image.rotationsAndFlips().none { it == this }

  private fun Grid<Char>.isTopLeftCorner(): Boolean = row(0).isBoundary() && column(0).isBoundary()

  companion object {
    val testInput1 = """
      Tile 2311:
      ..##.#..#.
      ##..#.....
      #...##..#.
      ####.#...#
      ##.##.###.
      ##...#.###
      .#.#.#..##
      ..#....#..
      ###...#.#.
      ..###..###
      
      Tile 1951:
      #.##...##.
      #.####...#
      .....#..##
      #...######
      .##.#....#
      .###.#####
      ###.##.##.
      .###....#.
      ..#.#..#.#
      #...##.#..
      
      Tile 1171:
      ####...##.
      #..##.#..#
      ##.#..#.#.
      .###.####.
      ..###.####
      .##....##.
      .#...####.
      #.##.####.
      ####..#...
      .....##...
      
      Tile 1427:
      ###.##.#..
      .#..#.##..
      .#.##.#..#
      #.#.#.##.#
      ....#...##
      ...##..##.
      ...#.#####
      .#.####.#.
      ..#..###.#
      ..##.#..#.
      
      Tile 1489:
      ##.#.#....
      ..##...#..
      .##..##...
      ..#...#...
      #####...#.
      #..#.#.#.#
      ...#.#.#..
      ##.#...##.
      ..##.##.##
      ###.##.#..
      
      Tile 2473:
      #....####.
      #..#.##...
      #.##..#...
      ######.#.#
      .#...#.#.#
      .#########
      .###.#..#.
      ########.#
      ##...##.#.
      ..###.#.#.
      
      Tile 2971:
      ..#.#....#
      #...###...
      #.#.###...
      ##.##..#..
      .#####..##
      .#..####.#
      #..#.#..#.
      ..####.###
      ..#.#.###.
      ...#.#.#.#
      
      Tile 2729:
      ...#.#.#.#
      ####.#....
      ..#.#.....
      ....#..#.#
      .##..##.#.
      .#.####...
      ####.#.#..
      ##.####...
      ##..#.##..
      #.##...##.
      
      Tile 3079:
      #.#.#####.
      .#..######
      ..#.......
      ######....
      ####.#..#.
      .#...#.##.
      #.#####.##
      ..#.###...
      ..#.......
      ..#.###...
      """.trimIndent()
    val testAnswer1 = 20899048083289L

    val testInput2 = testInput1
    val testAnswer2 = 273
  }
}
