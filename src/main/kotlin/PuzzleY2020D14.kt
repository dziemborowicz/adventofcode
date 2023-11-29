class PuzzleY2020D14 : Puzzle {

  private sealed interface Instruction

  private data class Mask(val string: String) : Instruction {

    fun apply(value: Long): Long {
      val mask = string.replace('1', '0').replace('X', '1').toLong(2)
      val set = string.replace('X', '0').toLong(2)
      return (value and mask) or set
    }

    fun decode(address: Long): Sequence<Long> = sequence {
      val mask = string.replace('0', '1').replace('X', '0').toLong(2)
      val set = string.replace('X', '0').toLong(2)
      val xBits = string.reversed().indicesOf('X')
      repeat(2 pow xBits.size) { xBitValues ->
        val floating = xBits.mapIndexed { index, bit ->
          xBitValues.getBit(index).toLong() shl bit
        }.reduce { a, b -> a or b }
        yield((address and mask) or set or floating)
      }
    }
  }

  private data class Store(val address: Long, val value: Long) : Instruction

  private lateinit var instructions: List<Instruction>

  override fun parse(input: String) {
    instructions = input.lines().map { line ->
      if (line.startsWith("mask = ")) {
        Mask(line.substring("mask = ".length))
      } else {
        val (address, value) = line.extractLongs()
        Store(address, value)
      }
    }
  }

  override fun solve1(): Long {
    val memory = hashMapOf<Long, Long>()
    lateinit var mask: Mask
    for (instruction in instructions) {
      when (instruction) {
        is Mask -> mask = instruction
        is Store -> memory[instruction.address] = mask.apply(instruction.value)
      }
    }
    return memory.values.sum()
  }

  override fun solve2(): Long {
    val memory = hashMapOf<Long, Long>()
    lateinit var mask: Mask
    for (instruction in instructions) {
      when (instruction) {
        is Mask -> mask = instruction
        is Store -> mask.decode(instruction.address).forEach { memory[it] = instruction.value }
      }
    }
    return memory.values.sum()
  }

  companion object {
    val testInput1 = """
      mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
      mem[8] = 11
      mem[7] = 101
      mem[8] = 0
      """.trimIndent()
    val testAnswer1 = 165

    val testInput2 = """
      mask = 000000000000000000000000000000X1001X
      mem[42] = 100
      mask = 00000000000000000000000000000000X0XX
      mem[26] = 1
      """.trimIndent()
    val testAnswer2 = 208
  }
}
