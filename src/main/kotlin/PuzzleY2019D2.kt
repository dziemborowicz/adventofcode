import com.google.common.truth.Truth.assertThat

class PuzzleY2019D2 : Puzzle {

  private lateinit var memory: List<Int>

  override fun parse(input: String) {
    memory = input.extractInts()
  }

  override fun solve1(): Int = execute(memory, 12, 2)

  override fun solve2(): Int {
    for (noun in (0..99)) {
      for (verb in (0..99)) {
        if (execute(memory, noun, verb) == 19690720) {
          return (100 * noun) + verb
        }
      }
    }
    fail()
  }

  companion object {
    private fun execute(memory: MutableList<Int>): Int {
      var ip = 0
      while (true) {
        when (memory[ip]) {
          1 -> memory[memory[ip + 3]] = memory[memory[ip + 1]] + memory[memory[ip + 2]]
          2 -> memory[memory[ip + 3]] = memory[memory[ip + 1]] * memory[memory[ip + 2]]
          99 -> return memory[0]
          else -> fail()
        }
        ip += 4
      }
    }

    private fun execute(memory: List<Int>, noun: Int, verb: Int): Int {
      val mutableMemory = memory.toMutableList()
      mutableMemory[1] = noun
      mutableMemory[2] = verb
      return execute(mutableMemory)
    }

    fun test1_1() {
      test(
        initialMemory = listOf(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50),
        finalMemory = listOf(3500, 9, 10, 70, 2, 3, 11, 0, 99, 30, 40, 50)
      )
    }

    fun test1_2() {
      test(
        initialMemory = listOf(1, 0, 0, 0, 99),
        finalMemory = listOf(2, 0, 0, 0, 99)
      )
    }

    fun test1_3() {
      test(
        initialMemory = listOf(2, 3, 0, 3, 99),
        finalMemory = listOf(2, 3, 0, 6, 99)
      )
    }

    fun test1_4() {
      test(
        initialMemory = listOf(2, 4, 4, 5, 99, 0),
        finalMemory = listOf(2, 4, 4, 5, 99, 9801)
      )
    }

    fun test1_5() {
      test(
        initialMemory = listOf(1, 1, 1, 4, 99, 5, 6, 0, 99),
        finalMemory = listOf(30, 1, 1, 4, 2, 5, 6, 0, 99)
      )
    }

    private fun test(initialMemory: List<Int>, finalMemory: List<Int>) {
      val memory = initialMemory.toMutableList()
      execute(memory)
      assertThat(memory).isEqualTo(finalMemory)
    }
  }
}
