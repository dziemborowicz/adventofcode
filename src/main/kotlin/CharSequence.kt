fun CharSequence.countAll(str: String, startIndex: Int = 0): Int = findAll(str, startIndex).count()

fun CharSequence.countAllRegex(regex: String, startIndex: Int = 0): Int =
  findAllRegex(regex, startIndex).count()

fun CharSequence.findAll(str: String, startIndex: Int = 0): Sequence<MatchResult> =
  findAllRegex(Regex.escape(str), startIndex)

fun CharSequence.findAllRegex(regex: String, startIndex: Int = 0): Sequence<MatchResult> =
  Regex(regex).findAll(this, startIndex)

infix fun CharSequence.matchesRegex(str: String): Boolean = Regex(str).matches(this)
