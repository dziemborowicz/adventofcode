import com.google.common.truth.Truth.assertThat

class PuzzleY2019D24 : Puzzle {

  private data class RecursiveIndex(val depth: Int, val index: Index)

  private lateinit var grid: Grid<Char>

  override fun parse(input: String) {
    grid = input.parseDenseCharGrid()
  }

  override fun solve1(): Long {
    var grid = grid
    val seen = hashSetOf<Grid<Char>>()

    while (seen.add(grid)) {
      grid = grid.mapIndexed { i, c ->
        val numNeighbors = i.neighborsIn(grid).count { grid[it] == '#' }
        when (c) {
          '#' if numNeighbors != 1 -> '.'
          '.' if numNeighbors in 1..2 -> '#'
          else -> c
        }
      }
    }

    return grid.values().sumOfIndexed { i, c ->
      if (c == '#') {
        1L shl i
      } else {
        0L
      }
    }
  }

  override fun solve2(): Int = solve2(grid, iterations = 200)

  companion object {
    private fun solve2(grid: Grid<Char>, iterations: Int): Int {
      var state = mapOf(0 to grid)

      repeat(iterations) {
        state = state.evolveEachIndexed { i, c ->
          val numNeighbors = i.neighborsIn(state).count { state[it] == '#' }
          when (c) {
            '#' if numNeighbors != 1 -> '.'
            '.' if numNeighbors in 1..2 -> '#'
            else -> c
          }
        }
      }

      return state.values.sumOf { it.count { it == '#' } }
    }

    private fun Map<Int, Grid<Char>>.evolveEachIndexed(
      transform: (RecursiveIndex, Char) -> Char,
    ): Map<Int, Grid<Char>> {
      val depths = keys + listOf(keys.min() - 1, keys.max() + 1)
      return depths.associateWith { depth ->
        Grid(numRows, numColumns) { index ->
          if (index == centerIndex) {
            '?'
          } else {
            val recursiveIndex = index.withDepth(depth)
            transform(recursiveIndex, this[recursiveIndex])
          }
        }
      }
    }

    private val Map<Int, Grid<Char>>.numRows: Int
      get() = values.first().numRows

    private val Map<Int, Grid<Char>>.numColumns: Int
      get() = values.first().numColumns

    private val Map<Int, Grid<Char>>.centerIndex: Index
      get() = values.first().centerIndex

    private val Map<Int, Grid<Char>>.indices: List<Index>
      get() = values.first().indices

    private operator fun Map<Int, Grid<Char>>.get(index: RecursiveIndex): Char =
      this[index.depth]?.get(index.index) ?: '.'

    private fun RecursiveIndex.neighborsIn(state: Map<Int, Grid<Char>>): List<RecursiveIndex> {
      return index.neighbors().flatMap { neighbor ->
        when (neighbor) {
          state.centerIndex -> {
            state.indices.filter {
              when (neighbor) {
                index.up() -> it.row == state.numRows - 1
                index.right() -> it.column == 0
                index.down() -> it.row == 0
                index.left() -> it.column == state.numColumns - 1
                else -> fail()
              }
            }.map { it.withDepth(depth + 1) }
          }

          !in state.indices -> listOf((state.centerIndex + neighbor - index).withDepth(depth - 1))

          else -> listOf(neighbor.withDepth(depth))
        }
      }
    }

    private fun Index.withDepth(depth: Int) = RecursiveIndex(depth, this)

    val testInput1 = """
      ....#
      #..#.
      #..##
      ..#..
      #....
      """.trimIndent()
    val testAnswer1 = 2129920

    fun test2() {
      val grid = testInput1.trim().parseDenseCharGrid()
      assertThat(solve2(grid, iterations = 10)).isEqualTo(99)
    }
  }
}
