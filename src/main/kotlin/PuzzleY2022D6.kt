class PuzzleY2022D6 : Puzzle {

  private lateinit var input: String

  override fun parse(input: String) {
    this.input = input
  }

  override fun solve1(): Int {
    return input.windowed(4).indexOfFirst { it.toSet().size == 4 } + 4
  }

  override fun solve2(): Int {
    return input.windowed(14).indexOfFirst { it.toSet().size == 14 } + 14
  }

  companion object {
    val testInput1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    val testAnswer1 = 7

    val testInput2_1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    val testAnswer2_1 = 19

    val testInput2_2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
    val testAnswer2_2 = 23

    val testInput2_3 = "nppdvjthqldpwncqszvftbrmjlhg"
    val testAnswer2_3 = 23

    val testInput2_4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
    val testAnswer2_4 = 29

    val testInput2_5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    val testAnswer2_5 = 26
  }
}
