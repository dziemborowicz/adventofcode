class PuzzleY2020D8 : Puzzle {

  private lateinit var program: List<Instruction>

  override fun parse(input: String) {
    program = input.lines().map { line ->
      val (operation, argument) = line.split(' ')
      Instruction(operation, argument.toInt())
    }
  }

  override fun solve1(): Int {
    return runProgram().accumulator
  }

  override fun solve2(): Int {
    for (instruction in program) {
      instruction.flipJmpNop()
      val result = runProgram()
      instruction.flipJmpNop()
      if (result.terminates) {
        return result.accumulator
      }
    }
    fail()
  }

  private fun runProgram(): ProgramResult {
    var accumulator = 0
    var instructionPointer = 0
    val visited = hashSetOf<Int>()
    while (visited.add(instructionPointer) && instructionPointer in program.indices) {
      val instruction = program[instructionPointer++]
      when (instruction.operation) {
        "acc" -> accumulator += instruction.argument
        "jmp" -> instructionPointer += instruction.argument - 1
        "nop" -> {}
        else -> error()
      }
    }
    return ProgramResult(accumulator, terminates = instructionPointer == program.size)
  }

  private fun Instruction.flipJmpNop() {
    operation = when (operation) {
      "jmp" -> "nop"
      "nop" -> "jmp"
      else -> operation
    }
  }

  private data class Instruction(var operation: String, val argument: Int)

  private data class ProgramResult(val accumulator: Int, val terminates: Boolean)

  companion object {
    val testInput1 = """
      nop +0
      acc +1
      jmp +4
      acc +3
      jmp -3
      acc -99
      acc +1
      jmp -4
      acc +6
      """.trimIndent()
    val testAnswer1 = 5

    val testInput2 = testInput1
    val testAnswer2 = 8
  }
}
