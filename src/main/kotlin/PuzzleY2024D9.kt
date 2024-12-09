class PuzzleY2024D9 : Puzzle {

  private data class File(val id: Int, var range: IntRange)

  private lateinit var files: List<File>

  override fun parse(input: String) {
    files = input.map { it.digitToInt() }.runningFold(0, Int::plus).windowed(2)
      .filterIndexed { i, _ -> i.isEven }.mapIndexed { id, (a, b) -> File(id, a..<b) }
  }

  override fun solve1(): Long = files.blocks().defragment().checksum()

  override fun solve2(): Long = files.defragment().checksum()

  private fun List<File>.blocks(): List<File> = flatMap { (id, range) ->
    range.map { File(id, it..it) }
  }

  private fun List<File>.freeSpace(): IntRangeSet =
    IntRangeSet(0..Int.MAX_VALUE).also { it.removeAll(map { it.range }) }

  private fun List<File>.defragment(): List<File> {
    val files = map { it.copy() }.reversed()
    val freeSpace = files.freeSpace()
    for (file in files) {
      val newStart = freeSpace.ranges()
        .firstOrNull { it.first < file.range.first && it.count >= file.range.count }?.first
      if (newStart != null) {
        freeSpace += file.range
        file.range = file.range.moveToStartAt(newStart)
        freeSpace -= file.range
      }
    }
    return files
  }

  private fun List<File>.checksum(): Long = sumOf { it.id * it.range.sum() }

  companion object {
    val testInput1 = "2333133121414131402"
    val testAnswer1 = 1928

    val testInput2 = testInput1
    val testAnswer2 = 2858
  }
}
