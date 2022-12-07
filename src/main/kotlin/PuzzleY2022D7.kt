class PuzzleY2022D7 : Puzzle {

  data class Directory(val parent: Directory? = null) {
    val subDirectories = mutableMapOf<String, Directory>()
    val files = mutableMapOf<String, File>()
    var size: Long = 0
  }

  data class File(val parent: Directory, val size: Long)

  private val root = Directory()

  override fun parse(input: String) {
    lateinit var currentDirectory: Directory
    for (line in input.lines()) {
      when {
        line.startsWith("$ cd ") -> {
          currentDirectory =
            when (val path = line.removePrefix("$ cd ")) {
              "/" -> root
              ".." -> currentDirectory.parent!!
              else -> currentDirectory.subDirectories.getOrPut(path) { Directory(currentDirectory) }
            }
        }
        line.first().isDigit() -> {
          val (size, name) = line.split(' ')
          currentDirectory.files.computeIfAbsent(name) {
            File(currentDirectory, size.toLong()).also { file ->
              file.parents().forEach { it.size += file.size }
            }
          }
        }
      }
    }
  }

  override fun solve1(): Long {
    return root.allDirectories().filter { it.size <= 100_000 }.sumOf { it.size }
  }

  override fun solve2(): Long {
    val totalSpace = 70_000_000
    val updateSize = 30_000_000
    val available = totalSpace - root.size
    val required = updateSize - available
    check(required > 0)
    return root.allDirectories().filter { it.size >= required }.minOf { it.size }
  }

  private fun File.parents(): Sequence<Directory> {
    return sequence {
      var directory: Directory? = parent
      while (directory != null) {
        yield(directory)
        directory = directory.parent
      }
    }
  }

  private fun Directory.allDirectories(): Sequence<Directory> {
    val stack = mutableListOf(this)
    return sequence {
      while (stack.isNotEmpty()) {
        val directory = stack.removeLast()
        yield(directory)
        stack.addAll(directory.subDirectories.values)
      }
    }
  }

  companion object {
    val testInput1 = """
      $ cd /
      $ ls
      dir a
      14848514 b.txt
      8504156 c.dat
      dir d
      $ cd a
      $ ls
      dir e
      29116 f
      2557 g
      62596 h.lst
      $ cd e
      $ ls
      584 i
      $ cd ..
      $ cd ..
      $ cd d
      $ ls
      4060174 j
      8033020 d.log
      5626152 d.ext
      7214296 k
      """.trimIndent()
    val testAnswer1 = 95437

    val testInput2 = testInput1
    val testAnswer2 = 24933642
  }
}
