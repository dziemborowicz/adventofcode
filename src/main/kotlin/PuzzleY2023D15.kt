class PuzzleY2023D15 : Puzzle {

  private sealed interface Step {
    val label: String
    val hash: Int
  }

  private data class AddLens(
    override val label: String,
    val focalLength: Int,
    override val hash: Int,
  ) : Step

  private data class RemoveLens(
    override val label: String,
    override val hash: Int,
  ) : Step

  private fun Step(str: String): Step {
    return when {
      '=' in str -> AddLens(str)
      '-' in str -> RemoveLens(str)
      else -> fail()
    }
  }

  private fun AddLens(str: String): Step {
    val (label, num) = str.split('=')
    return AddLens(label, num.toInt(), hash(str))
  }

  private fun RemoveLens(str: String): Step {
    val (label) = str.split('-')
    return RemoveLens(label, hash(str))
  }

  private lateinit var steps: List<Step>

  override fun parse(input: String) {
    steps = input.split(',').map { Step(it) }
  }

  override fun solve1(): Int {
    return steps.sumOf { it.hash }
  }

  override fun solve2(): Int {
    val boxes = List(256) { LinkedHashMap<String, Int>() }

    for (step in steps) {
      val boxNumber = hash(step.label)
      val lenses = boxes[boxNumber]
      when (step) {
        is AddLens -> lenses[step.label] = step.focalLength
        is RemoveLens -> lenses.remove(step.label)
      }
    }

    return boxes.withIndex().sumOf { (boxNumber, lenses) ->
      lenses.values.withIndex().sumOf { (lensNumber, focalLength) ->
        (boxNumber + 1) * (lensNumber + 1) * focalLength
      }
    }
  }

  fun hash(str: String) = str.fold(0) { acc, c -> ((acc + c.code) * 17).mod(256) }

  companion object {
    fun testHash() {
      PuzzleY2023D15().apply {
        check(hash("HASH") == 52)
      }
    }

    val testInput1 = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".trimIndent()
    val testAnswer1 = 1320

    val testInput2 = testInput1
    val testAnswer2 = 145
  }
}
