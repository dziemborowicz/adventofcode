class PuzzleY2020D23 : Puzzle {

  private data class Node(val label: Int, var next: Node? = null)

  private lateinit var cups: List<Int>

  override fun parse(input: String) {
    cups = input.map { it.digitToInt() }
  }

  override fun solve1(): String {
    return solve(rounds = 100, numCups = cups.size).joinToString("")
  }

  override fun solve2(): Long {
    return solve(rounds = 10_000_000, numCups = 1_000_000).take(2).productOf { it.toLong() }
  }

  private fun solve(rounds: Int, numCups: Int): List<Int> {
    val nodes = hashMapOf<Int, Node>()
    var head: Node? = null
    var tail: Node? = null

    val allCups = cups + (cups.max() + 1..numCups)
    for (label in allCups.reversed()) {
      head = Node(label, head).also { current ->
        if (tail == null) {
          tail = current.also { current.next = current }
        } else {
          tail.next = current
        }
        nodes[label] = current
      }
    }

    repeat(rounds) {
      var removed: Node? = null
      repeat(3) {
        val previous = removed
        removed = head.next
        head.next = head.next.next
        removed.next = previous
      }

      val removedLabels = labelsFrom(removed)
      var destinationLabel = head.label
      do {
        destinationLabel = (destinationLabel - 1 - 1).mod(numCups) + 1
      } while (destinationLabel in removedLabels)

      val destination = nodes[destinationLabel]
      while (removed != null) {
        val removedNext = removed.next
        destination.next = removed.also { removed.next = destination.next }
        removed = removedNext
      }

      head = head.next
    }

    return labelsFrom(nodes[1]).drop(1)
  }

  private fun labelsFrom(node: Node?): List<Int> {
    return buildList {
      var current: Node? = node
      while (current != null && (isEmpty() || current !== node)) {
        add(current.label)
        current = current.next
      }
    }
  }

  private val Node?.label: Int
    get() = this!!.label

  private var Node?.next: Node?
    get() = this!!.next
    set(next) {
      this!!.next = next
    }

  companion object {
    val testInput1 = "389125467"
    val testAnswer1 = 67384529

    val testInput2 = testInput1
    val testAnswer2 = 149245887792
  }
}
