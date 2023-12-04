class PuzzleY2023D5 : Puzzle {

  private data class Mapping(val source: LongRange, val destination: LongRange)

  private lateinit var seeds: List<Long>
  private lateinit var allMappings: List<List<Mapping>>

  override fun parse(input: String) {
    val sections = input.lines().splitByBlank()
    seeds = sections.first().single().extractLongs()
    allMappings = sections.drop(1).map { lines ->
      lines.drop(1).map { line ->
        val (destinationStart, sourceStart, count) = line.extractLongs()
        Mapping(sourceStart..<sourceStart + count, destinationStart..<destinationStart + count)
      }.withDefaultMappings().sortedBy { it.source.first }
    }
  }

  override fun solve1(): Long = solve(seeds.map { it..it })

  override fun solve2(): Long = solve(seeds.chunked(2) { (a, b) -> a..<a + b })

  private fun solve(seedRanges: List<LongRange>): Long {
    var values = seedRanges.toLongRangeSet()
    for (mappings in allMappings) {
      values = remap(values, mappings)
    }
    return values.min()
  }

  private fun remap(source: LongRangeSet, mappings: List<Mapping>): LongRangeSet {
    val result = LongRangeSet()
    for (range in source.ranges()) {
      var rangeLeft = range
      while (!rangeLeft.isEmpty()) {
        val mapping = mappings.first { rangeLeft.start in it.source }
        val intersection = mapping.source intersect rangeLeft
        result += intersection.remap(mapping)
        rangeLeft = intersection.last + 1..range.last
      }
    }
    return result
  }

  private fun LongRange.remap(mapping: Mapping): LongRange {
    val first = first.remap(mapping.source, mapping.destination)
    val last = last.remap(mapping.source, mapping.destination)
    return first..last
  }

  private fun List<Mapping>.withDefaultMappings(): List<Mapping> {
    val defaultMappings = map { it.source }.union().inverted().ranges().map { Mapping(it, it) }
    return this + defaultMappings
  }

  companion object {
    val testInput1 = """
      seeds: 79 14 55 13
      
      seed-to-soil map:
      50 98 2
      52 50 48
      
      soil-to-fertilizer map:
      0 15 37
      37 52 2
      39 0 15
      
      fertilizer-to-water map:
      49 53 8
      0 11 42
      42 0 7
      57 7 4
      
      water-to-light map:
      88 18 7
      18 25 70
      
      light-to-temperature map:
      45 77 23
      81 45 19
      68 64 13
      
      temperature-to-humidity map:
      0 69 1
      1 0 69
      
      humidity-to-location map:
      60 56 37
      56 93 4
      """.trimIndent()
    val testAnswer1 = 35

    val testInput2 = testInput1
    val testAnswer2 = 46
  }
}
