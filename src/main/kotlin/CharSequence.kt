fun CharSequence.countAll(str: String, startIndex: Int = 0): Int = findAll(str, startIndex).size

fun CharSequence.countAllRegex(regex: String, startIndex: Int = 0): Int =
  findAllRegex(regex, startIndex).size

fun CharSequence.findAll(str: String, startIndex: Int = 0): List<MatchResult> =
  findAllRegex(Regex.escape(str), startIndex)

fun CharSequence.findAllRegex(regex: String, startIndex: Int = 0): List<MatchResult> =
  Regex(regex).findAll(this, startIndex).toList()

infix fun CharSequence.matchesRegex(str: String): Boolean = Regex(str).matches(this)
