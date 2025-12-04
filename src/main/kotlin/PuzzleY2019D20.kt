class PuzzleY2019D20 : Puzzle {

  private data class Portal(
    val label: String,
    val adjacent: Index,
    val entry: Index,
    var exit: Index? = null,
  )

  private lateinit var grid: Grid<Char>
  private lateinit var portals: Map<Index, Portal>

  private lateinit var start: Index
  private lateinit var end: Index

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()

    portals = grid.indices.mapNotNull { index ->
      val center = grid.getOrNull(index)
      val (up, right, down, left) = index.neighbors().map { grid.getOrNull(it) }

      when {
        left.isLetter() && center.isLetter() && right == '.' -> {
          Portal("$left$center", index.right(), index)
        }

        left == '.' && center.isLetter() && right.isLetter() -> {
          Portal("$center$right", index.left(), index)
        }

        up.isLetter() && center.isLetter() && down == '.' -> {
          Portal("$up$center", index.down(), index)
        }

        up == '.' && center.isLetter() && down.isLetter() -> {
          Portal("$center$down", index.up(), index)
        }

        else -> {
          null
        }
      }
    }.associateBy { it.entry }

    for ((first, second) in portals.values.groupedBy { it.label }.filter { it.size == 2 }) {
      first.exit = second.adjacent
      second.exit = first.adjacent
    }

    start = portals.values.single { it.label == "AA" }.adjacent
    end = portals.values.single { it.label == "ZZ" }.adjacent
  }

  override fun solve1(): Int {
    val queue = dequeOf(start to 0)
    val visited = hashSetOf<Index>()
    while (true) {
      val (index, steps) = queue.removeFirst()
      if (!visited.add(index)) continue
      if (index == end) return steps
      for (neighbor in index.neighborsIn(grid)) {
        val portalExit = portals[neighbor]?.exit
        if (portalExit != null) {
          queue += portalExit to (steps + 1)
        } else if (grid[neighbor] == '.') {
          queue += neighbor to (steps + 1)
        }
      }
    }
  }

  override fun solve2(): Int {
    val queue = dequeOf(Triple(start, 0, 0))
    val visited = hashSetOf<Pair<Index, Int>>()
    while (true) {
      val (index, level, steps) = queue.removeFirst()
      if (!visited.add(index to level)) continue
      if (index == end && level == 0) return steps
      for (neighbor in index.neighborsIn(grid)) {
        val portal = portals[neighbor]
        val portalExit = portal?.exit
        if (portalExit != null) {
          if (portal.isInner()) {
            queue += Triple(portalExit, level + 1, (steps + 1))
          } else if (level > 0) {
            queue += Triple(portalExit, level - 1, (steps + 1))
          }
        } else if (grid[neighbor] == '.') {
          queue += Triple(neighbor, level, (steps + 1))
        }
      }
    }
  }

  private fun Portal.isOuter(): Boolean =
    adjacent.row == 2 || adjacent.row == grid.numRows - 3 || adjacent.column == 2 || adjacent.column == grid.numColumns - 3

  private fun Portal.isInner(): Boolean = !isOuter()

  private fun Char?.isLetter(): Boolean = this != null && Character.isLetter(this)

  companion object {
    val testInput1_1 = """
               A
               A
        #######.#########
        #######.........#
        #######.#######.#
        #######.#######.#
        #######.#######.#
        #####  B    ###.#
      BC...##  C    ###.#
        ##.##       ###.#
        ##...DE  F  ###.#
        #####    G  ###.#
        #########.#####.#
      DE..#######...###.#
        #.#########.###.#
      FG..#########.....#
        ###########.#####
                   Z
                   Z
      """.trimIndent()
    val testAnswer1_1 = 23

    val testInput1_2 = """
                         A
                         A
        #################.#############
        #.#...#...................#.#.#
        #.#.#.###.###.###.#########.#.#
        #.#.#.......#...#.....#.#.#...#
        #.#########.###.#####.#.#.###.#
        #.............#.#.....#.......#
        ###.###########.###.#####.#.#.#
        #.....#        A   C    #.#.#.#
        #######        S   P    #####.#
        #.#...#                 #......VT
        #.#.#.#                 #.#####
        #...#.#               YN....#.#
        #.###.#                 #####.#
      DI....#.#                 #.....#
        #####.#                 #.###.#
      ZZ......#               QG....#..AS
        ###.###                 #######
      JO..#.#.#                 #.....#
        #.#.#.#                 ###.#.#
        #...#..DI             BU....#..LF
        #####.#                 #.#####
      YN......#               VT..#....QG
        #.###.#                 #.###.#
        #.#...#                 #.....#
        ###.###    J L     J    #.#.###
        #.....#    O F     P    #.#...#
        #.###.#####.#.#####.#####.###.#
        #...#.#.#...#.....#.....#.#...#
        #.#####.###.###.#.#.#########.#
        #...#.#.....#...#.#.#.#.....#.#
        #.###.#####.###.###.#.#.#######
        #.#.........#...#.............#
        #########.###.###.#############
                 B   J   C
                 U   P   P
      """.trimIndent()
    val testAnswer1_2 = 58

    val testInput2_1 = testInput1_1
    val testAnswer2_1 = 26

    val testInput2_2 = """
                   Z L X W       C
                   Z P Q B       K
        ###########.#.#.#.#######.###############
        #...#.......#.#.......#.#.......#.#.#...#
        ###.#.#.#.#.#.#.#.###.#.#.#######.#.#.###
        #.#...#.#.#...#.#.#...#...#...#.#.......#
        #.###.#######.###.###.#.###.###.#.#######
        #...#.......#.#...#...#.............#...#
        #.#########.#######.#.#######.#######.###
        #...#.#    F       R I       Z    #.#.#.#
        #.###.#    D       E C       H    #.#.#.#
        #.#...#                           #...#.#
        #.###.#                           #.###.#
        #.#....OA                       WB..#.#..ZH
        #.###.#                           #.#.#.#
      CJ......#                           #.....#
        #######                           #######
        #.#....CK                         #......IC
        #.###.#                           #.###.#
        #.....#                           #...#.#
        ###.###                           #.#.#.#
      XF....#.#                         RF..#.#.#
        #####.#                           #######
        #......CJ                       NM..#...#
        ###.#.#                           #.###.#
      RE....#.#                           #......RF
        ###.###        X   X       L      #.#.#.#
        #.....#        F   Q       P      #.#.#.#
        ###.###########.###.#######.#########.###
        #.....#...#.....#.......#...#.....#.#...#
        #####.#.###.#######.#######.###.###.#.#.#
        #.......#.......#.#.#.#.#...#...#...#.#.#
        #####.###.#####.#.#.#.#.###.###.#.###.###
        #.......#.....#.#...#...............#...#
        #############.#.#.###.###################
                     A O F   N
                     A A D   M
      """.trimIndent()
    val testAnswer2_2 = 396
  }
}
