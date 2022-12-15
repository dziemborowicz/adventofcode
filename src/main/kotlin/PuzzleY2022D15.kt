import kotlin.math.abs

class PuzzleY2022D15 : Puzzle {

  private lateinit var input: String
  private lateinit var sensorBeacons: List<Pair<Point, Point>>
  private lateinit var beacons: List<Point>

  override fun parse(input: String) {
    this.input = input
    sensorBeacons = input.lines().map { line ->
      val regex =
        Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")
      val (sensorX, sensorY, beaconX, beaconY) = regex.matchEntire(line)!!.destructured
      Pair(Point(sensorX.toInt(), sensorY.toInt()), Point(beaconX.toInt(), beaconY.toInt()))
    }
    beacons = sensorBeacons.map { it.second }
  }

  override fun solve1(): Int {
    val y = if (input == testInput1) 10 else 2_000_000
    return xCoordinatesOfDefinitelyEmptyPointsWith(y = y).size
  }

  override fun solve2(): Long {
    val max = if (input == testInput2) 20 else 4_000_000
    val distressedBeacon = (0..max).firstNotNullOf { y ->
      val possibleXCoordinates = (0..max) intersect xCoordinatesOfUnknownPointsWith(y = y)
      possibleXCoordinates.singleOrNull()?.let { x -> Point(x, y) }
    }
    return (distressedBeacon.x * 4_000_000L) + distressedBeacon.y
  }

  private fun xCoordinatesOfDefinitelyEmptyPointsWith(y: Int): IntRangeSet {
    return xCoordinatesOfScannedPointsWith(y = y).also {
      it.removeAll(beacons.filter { beacon -> beacon.y == y }.map { beacon -> beacon.x })
    }
  }

  private fun xCoordinatesOfScannedPointsWith(y: Int): IntRangeSet {
    return IntRangeSet(sensorBeacons.map { (sensor, beacon) ->
      val yDistance = abs(sensor.y - y)
      val xDistance = (sensor manhattanDistanceTo beacon) - yDistance
      sensor.x - xDistance..sensor.x + xDistance
    })
  }

  private fun xCoordinatesOfUnknownPointsWith(y: Int): IntRangeSet {
    return xCoordinatesOfScannedPointsWith(y = y).inverted()
  }

  companion object {
    val testInput1 = """
      Sensor at x=2, y=18: closest beacon is at x=-2, y=15
      Sensor at x=9, y=16: closest beacon is at x=10, y=16
      Sensor at x=13, y=2: closest beacon is at x=15, y=3
      Sensor at x=12, y=14: closest beacon is at x=10, y=16
      Sensor at x=10, y=20: closest beacon is at x=10, y=16
      Sensor at x=14, y=17: closest beacon is at x=10, y=16
      Sensor at x=8, y=7: closest beacon is at x=2, y=10
      Sensor at x=2, y=0: closest beacon is at x=2, y=10
      Sensor at x=0, y=11: closest beacon is at x=2, y=10
      Sensor at x=20, y=14: closest beacon is at x=25, y=17
      Sensor at x=17, y=20: closest beacon is at x=21, y=22
      Sensor at x=16, y=7: closest beacon is at x=15, y=3
      Sensor at x=14, y=3: closest beacon is at x=15, y=3
      Sensor at x=20, y=1: closest beacon is at x=15, y=3
      """.trimIndent()
    val testAnswer1 = 26

    val testInput2 = testInput1
    val testAnswer2 = 56000011
  }
}
