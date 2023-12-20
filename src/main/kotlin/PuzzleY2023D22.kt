class PuzzleY2023D22 : Puzzle {

  private class Brick(var x: IntRange, var y: IntRange, var z: IntRange)

  private lateinit var bricks: List<Brick>
  private lateinit var supportsMap: Map<Brick, Set<Brick>>
  private lateinit var supportedMap: Map<Brick, Set<Brick>>

  override fun parse(input: String) {
    bricks = input.lines().map { line ->
      val (start, end) = line.split('~')
      val (x1, y1, z1) = start.extractInts()
      val (x2, y2, z2) = end.extractInts()
      Brick(
        minOf(x1, x2)..maxOf(x1, x2),
        minOf(y1, y2)..maxOf(y1, y2),
        minOf(z1, z2)..maxOf(z1, z2),
      )
    }.also { it.settle() }

    supportsMap = bricks.associateWith { brick ->
      val brickMovedDown = brick.movedDown()
      bricks.filter { it !== brick && it intersects brickMovedDown }.toSet()
    }

    supportedMap = bricks.associateWith { brick ->
      val brickMovedUp = brick.movedUp()
      bricks.filter { it !== brick && it intersects brickMovedUp }.toSet()
    }
  }

  override fun solve1(): Int = bricks.count { it.allSupportedBricks().isEmpty() }

  override fun solve2(): Int = bricks.sumOf { it.allSupportedBricks().size }

  private fun Brick.allSupportedBricks(): Set<Brick> {
    val removed = hashSetOf<Brick>()
    val queue = dequeOf(this)
    while (queue.isNotEmpty()) {
      val brick = queue.removeFirst()
      if (removed.add(brick)) {
        queue += brick.supported.filter { it.isNotBase && (it.supports - removed).isEmpty() }
      }
    }
    return removed - this
  }

  private fun List<Brick>.settle() {
    do {
      var moved = false
      for (brick in this) {
        while (brick.canMoveDownAmong(this)) {
          brick.moveDown()
          moved = true
        }
      }
    } while (moved)
  }

  private fun Brick.canMoveDownAmong(bricks: List<Brick>): Boolean {
    val thisMovedDown = this.movedDown()
    return this.isNotBase && bricks.none { it !== this && it intersects thisMovedDown }
  }

  private infix fun Brick.intersects(other: Brick): Boolean =
    x.containsAny(other.x) && y.containsAny(other.y) && z.containsAny(other.z)

  private fun Brick.moveDown() {
    z = (z.first - 1)..(z.last - 1)
  }

  private fun Brick.movedDown() = Brick(x, y, (z.first - 1)..(z.last - 1))

  private fun Brick.movedUp() = Brick(x, y, (z.first + 1)..(z.last + 1))

  private val Brick.isNotBase: Boolean
    get() = z.first > 1

  private val Brick.supports: Set<Brick>
    get() = supportsMap.getValue(this)

  private val Brick.supported: Set<Brick>
    get() = supportedMap.getValue(this)

  companion object {
    val testInput1 = """
      1,0,1~1,2,1
      0,0,2~2,0,2
      0,2,3~2,2,3
      0,0,4~0,2,4
      2,0,5~2,2,5
      0,1,6~2,1,6
      1,1,8~1,1,9
      """.trimIndent()
    val testAnswer1 = 5

    val testInput2 = testInput1
    val testAnswer2 = 7
  }
}
