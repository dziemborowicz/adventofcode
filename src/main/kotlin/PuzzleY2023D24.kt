import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status

class PuzzleY2023D24 : Puzzle {

  private data class Hailstone(
    val x: Long,
    val y: Long,
    val z: Long,
    val vx: Long,
    val vy: Long,
    val vz: Long,
  )

  private lateinit var hailstones: List<Hailstone>

  override fun parse(input: String) {
    hailstones = input.lines().map { line ->
      val (x, y, z, vx, vy, vz) = line.extractLongs()
      Hailstone(x, y, z, vx, vy, vz)
    }
  }

  override fun solve1(): Int {
    val range = if (hailstones.isTestInput) {
      7L..27L
    } else {
      200000000000000L..400000000000000L
    }

    return hailstones.combinations(2).count { (a, b) ->
      val intersection = intersectionOf(a.toLine2D(), b.toLine2D())
      intersection != null &&
        intersection.x.toLong() in range &&
        intersection.y.toLong() in range &&
        (intersection.x - a.x) * a.vx > 0 &&
        (intersection.y - a.y) * a.vy > 0 &&
        (intersection.x - b.x) * b.vx > 0 &&
        (intersection.y - b.y) * b.vy > 0
    }
  }

  override fun solve2(): Long {
    Context().use { ctx ->
      val opt = ctx.mkSolver()

      val x0 = ctx.mkIntConst("x{0}")
      val y0 = ctx.mkIntConst("y{0}")
      val z0 = ctx.mkIntConst("z{0}")

      val vx = ctx.mkIntConst("vx")
      val vy = ctx.mkIntConst("vy")
      val vz = ctx.mkIntConst("vz")

      hailstones.take(3).forEachIndexed { hailstoneIndex, hailstone ->
        val t = ctx.mkIntConst("t{$hailstoneIndex}")

        opt.add(
          ctx.mkEq(
            ctx.mkAdd(x0, ctx.mkMul(vx, t)),
            ctx.mkAdd(ctx.mkInt(hailstone.x), ctx.mkMul(ctx.mkInt(hailstone.vx), t))
          )
        )

        opt.add(
          ctx.mkEq(
            ctx.mkAdd(y0, ctx.mkMul(vy, t)),
            ctx.mkAdd(ctx.mkInt(hailstone.y), ctx.mkMul(ctx.mkInt(hailstone.vy), t))
          )
        )

        opt.add(
          ctx.mkEq(
            ctx.mkAdd(z0, ctx.mkMul(vz, t)),
            ctx.mkAdd(ctx.mkInt(hailstone.z), ctx.mkMul(ctx.mkInt(hailstone.vz), t))
          )
        )
      }

      check(opt.check() == Status.SATISFIABLE)

      val x0Solution = (opt.model.eval(x0, false) as IntNum).int64
      val y0Solution = (opt.model.eval(y0, false) as IntNum).int64
      val z0Solution = (opt.model.eval(z0, false) as IntNum).int64

      return x0Solution + y0Solution + z0Solution
    }
  }

  /** A line in the standard form `ax + by = c`. */
  private data class Line2D(val a: Double, val b: Double, val c: Double)

  private fun Hailstone.toLine2D(): Line2D {
    val a = vy
    val b = -vx
    val c = a * x + b * y
    return Line2D(a.toDouble(), b.toDouble(), c.toDouble())
  }

  private fun intersectionOf(first: Line2D, second: Line2D): DoublePoint? {
    val determinant = first.a * second.b - second.a * first.b
    if (determinant == 0.0) return null

    return DoublePoint(
      (second.b * first.c - first.b * second.c) / determinant,
      (first.a * second.c - second.a * first.c) / determinant,
    )
  }

  companion object {
    private val List<Hailstone>.isTestInput: Boolean
      get() = size == 5

    val testInput1 = """
      19, 13, 30 @ -2,  1, -2
      18, 19, 22 @ -1, -1, -2
      20, 25, 34 @ -2, -2, -4
      12, 31, 28 @ -1, -2, -1
      20, 19, 15 @  1, -5, -3
      """.trimIndent()
    val testAnswer1 = 2

    val testInput2 = testInput1
    val testAnswer2 = 47
  }
}
