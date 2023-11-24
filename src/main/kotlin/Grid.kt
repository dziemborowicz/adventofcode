class Grid<T>(val numRows: Int, val numColumns: Int, init: (Index) -> T) {

  val height: Int = numRows
  val width: Int = numColumns
  val count: Int = numRows * numColumns

  val rowIndices: IntRange = 0..<numRows
  val columnIndices: IntRange = 0..<numColumns
  val indices: List<Index> by lazy(LazyThreadSafetyMode.NONE) {
    val innerIndices = List(numRows * numColumns) { Index(it / numColumns, it.mod(numColumns)) }

    object : List<Index> by innerIndices {
      override fun contains(element: Index): Boolean =
        element.row >= 0 && element.row < numRows && element.column >= 0 && element.column < numColumns

      override fun containsAll(elements: Collection<Index>): Boolean = elements.all { contains(it) }
    }
  }

  val xIndices: IntRange = 0..<numColumns
  val yIndices: IntRange = 0..<numRows
  val points: List<Point> by lazy(LazyThreadSafetyMode.NONE) {
    val innerPoints = indices.map { it.toPointIn(this) }

    object : List<Point> by innerPoints {
      override fun contains(element: Point): Boolean =
        element.row >= 0 && element.row < numRows && element.column >= 0 && element.column < numColumns

      override fun containsAll(elements: Collection<Point>): Boolean = elements.all { contains(it) }
    }
  }

  @PublishedApi internal val data = MutableList(numRows * numColumns) { init(indices[it]) }

  inline fun all(predicate: (T) -> Boolean): Boolean = data.all(predicate)

  inline fun allIndexed(predicate: (Index, T) -> Boolean): Boolean =
    indices.all { predicate(it, this[it]) }

  inline fun allPointed(predicate: (Point, T) -> Boolean): Boolean =
    points.all { predicate(it, this[it]) }

  inline fun any(predicate: (T) -> Boolean): Boolean = data.any(predicate)

  inline fun anyIndexed(predicate: (Index, T) -> Boolean): Boolean =
    indices.any { predicate(it, this[it]) }

  inline fun anyPointed(predicate: (Point, T) -> Boolean): Boolean =
    points.any { predicate(it, this[it]) }

  fun columns(): List<List<T>> =
    List(numColumns) { column -> List(numRows) { row -> get(row, column) } }

  fun column(column: Int): List<T> {
    if (column !in columnIndices) throw IndexOutOfBoundsException("column")
    return List(numRows) { row -> get(row, column) }
  }

  fun columnWrapped(column: Int): List<T> = column(column.mod(numColumns))

  operator fun contains(element: T): Boolean = element in data

  fun containsAll(elements: Iterable<T>): Boolean = elements.all { it in this }

  fun containsAny(elements: Iterable<T>): Boolean = elements.any { it in this }

  fun copy(): Grid<T> = toGrid()

  inline fun count(predicate: (T) -> Boolean): Int = data.count(predicate)

  inline fun countIndexed(predicate: (Index, T) -> Boolean): Int =
    indices.count { predicate(it, this[it]) }

  inline fun countPointed(predicate: (Point, T) -> Boolean): Int =
    points.count { predicate(it, this[it]) }

  fun diagonals(): List<List<T>> = listOf(diagonalDown(), diagonalUp())

  fun diagonalDown(): List<T> {
    check(numRows == numColumns) { "Grid must be a square to have a diagonal." }
    return rowIndices.map { this[it, it] }
  }

  fun diagonalUp(): List<T> {
    check(numRows == numColumns) { "Grid must be a square to have a diagonal." }
    return rowIndices.map { this[numRows - 1 - it, it] }
  }

  override fun equals(other: Any?): Boolean {
    if (other !is Grid<*>) return false
    if (numRows != other.numRows) return false
    if (numColumns != other.numColumns) return false
    return data == other.data
  }

  inline fun first(predicate: (T) -> Boolean): T = data.first(predicate)

  inline fun firstIndexed(predicate: (Index, T) -> Boolean): T =
    this[indices.first { predicate(it, this[it]) }]

  inline fun firstPointed(predicate: (Point, T) -> Boolean): T =
    this[points.first { predicate(it, this[it]) }]

  inline fun forEach(action: (T) -> Unit) = data.forEach(action)

  inline fun forEachIndexed(action: (Index, T) -> Unit) = indices.forEach { action(it, get(it)) }

  inline fun forEachPointed(action: (Point, T) -> Unit) = points.forEach { action(it, get(it)) }

  inline fun forEachNeighbor(index: Index, action: (T) -> Unit) {
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column >= 0 && index.column < numColumns) {
      action(this[index.up()])
    }
    if (index.row >= 0 && index.row < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      action(this[index.right()])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column >= 0 && index.column < numColumns) {
      action(this[index.down()])
    }
    if (index.row >= 0 && index.row < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      action(this[index.left()])
    }
  }

  inline fun forEachNeighbor(point: Point, action: (T) -> Unit) {
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column >= 0 && point.column < numColumns) {
      action(this[point.up()])
    }
    if (point.row >= 0 && point.row < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      action(this[point.right()])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column >= 0 && point.column < numColumns) {
      action(this[point.down()])
    }
    if (point.row >= 0 && point.row < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      action(this[point.left()])
    }
  }

  inline fun forEachNeighborIndexed(index: Index, action: (Index, T) -> Unit) {
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column >= 0 && index.column < numColumns) {
      val up = index.up()
      action(up, this[up])
    }
    if (index.row >= 0 && index.row < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      val right = index.right()
      action(right, this[right])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column >= 0 && index.column < numColumns) {
      val down = index.down()
      action(down, this[down])
    }
    if (index.row >= 0 && index.row < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      val left = index.left()
      action(left, this[left])
    }
  }

  inline fun forEachNeighborPointed(point: Point, action: (Point, T) -> Unit) {
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column >= 0 && point.column < numColumns) {
      val up = point.up()
      action(up, this[up])
    }
    if (point.row >= 0 && point.row < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      val right = point.right()
      action(right, this[right])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column >= 0 && point.column < numColumns) {
      val down = point.down()
      action(down, this[down])
    }
    if (point.row >= 0 && point.row < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      val left = point.left()
      action(left, this[left])
    }
  }

  inline fun forEachNeighborWrapped(index: Index, action: (T) -> Unit) {
    action(this[index.up().wrappedIn(this)])
    action(this[index.right().wrappedIn(this)])
    action(this[index.down().wrappedIn(this)])
    action(this[index.left().wrappedIn(this)])
  }

  inline fun forEachNeighborWrapped(point: Point, action: (T) -> Unit) {
    action(this[point.up().wrappedIn(this)])
    action(this[point.right().wrappedIn(this)])
    action(this[point.down().wrappedIn(this)])
    action(this[point.left().wrappedIn(this)])
  }

  inline fun forEachNeighborWrappedIndexed(index: Index, action: (Index, T) -> Unit) {
    index.up().wrappedIn(this).let { action(it, this[it]) }
    index.right().wrappedIn(this).let { action(it, this[it]) }
    index.down().wrappedIn(this).let { action(it, this[it]) }
    index.left().wrappedIn(this).let { action(it, this[it]) }
  }

  inline fun forEachNeighborWrappedPointed(point: Point, action: (Point, T) -> Unit) {
    point.up().wrappedIn(this).let { action(it, this[it]) }
    point.right().wrappedIn(this).let { action(it, this[it]) }
    point.down().wrappedIn(this).let { action(it, this[it]) }
    point.left().wrappedIn(this).let { action(it, this[it]) }
  }

  inline fun forEachNeighborWithDiagonals(index: Index, action: (T) -> Unit) {
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column >= 0 && index.column < numColumns) {
      action(this[index.up()])
    }
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      action(this[index.upRight()])
    }
    if (index.row >= 0 && index.row < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      action(this[index.right()])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      action(this[index.downRight()])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column >= 0 && index.column < numColumns) {
      action(this[index.down()])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      action(this[index.downLeft()])
    }
    if (index.row >= 0 && index.row < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      action(this[index.left()])
    }
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      action(this[index.upLeft()])
    }
  }

  inline fun forEachNeighborWithDiagonals(point: Point, action: (T) -> Unit) {
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column >= 0 && point.column < numColumns) {
      action(this[point.up()])
    }
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      action(this[point.upRight()])
    }
    if (point.row >= 0 && point.row < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      action(this[point.right()])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      action(this[point.downRight()])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column >= 0 && point.column < numColumns) {
      action(this[point.down()])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      action(this[point.downLeft()])
    }
    if (point.row >= 0 && point.row < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      action(this[point.left()])
    }
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      action(this[point.upLeft()])
    }
  }

  inline fun forEachNeighborWithDiagonalsIndexed(index: Index, action: (Index, T) -> Unit) {
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column >= 0 && index.column < numColumns) {
      val up = index.up()
      action(up, this[up])
    }
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      val upRight = index.upRight()
      action(upRight, this[upRight])
    }
    if (index.row >= 0 && index.row < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      val right = index.right()
      action(right, this[right])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column + 1 >= 0 && index.column + 1 < numColumns) {
      val downRight = index.downRight()
      action(downRight, this[downRight])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column >= 0 && index.column < numColumns) {
      val down = index.down()
      action(down, this[down])
    }
    if (index.row + 1 >= 0 && index.row + 1 < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      val downLeft = index.downLeft()
      action(downLeft, this[downLeft])
    }
    if (index.row >= 0 && index.row < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      val left = index.left()
      action(left, this[left])
    }
    if (index.row - 1 >= 0 && index.row - 1 < numRows && index.column - 1 >= 0 && index.column - 1 < numColumns) {
      val upLeft = index.upLeft()
      action(upLeft, this[upLeft])
    }
  }

  inline fun forEachNeighborWithDiagonalsPointed(point: Point, action: (Point, T) -> Unit) {
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column >= 0 && point.column < numColumns) {
      val up = point.up()
      action(up, this[up])
    }
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      val upRight = point.upRight()
      action(upRight, this[upRight])
    }
    if (point.row >= 0 && point.row < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      val right = point.right()
      action(right, this[right])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column + 1 >= 0 && point.column + 1 < numColumns) {
      val downRight = point.downRight()
      action(downRight, this[downRight])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column >= 0 && point.column < numColumns) {
      val down = point.down()
      action(down, this[down])
    }
    if (point.row + 1 >= 0 && point.row + 1 < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      val downLeft = point.downLeft()
      action(downLeft, this[downLeft])
    }
    if (point.row >= 0 && point.row < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      val left = point.left()
      action(left, this[left])
    }
    if (point.row - 1 >= 0 && point.row - 1 < numRows && point.column - 1 >= 0 && point.column - 1 < numColumns) {
      val upLeft = point.upLeft()
      action(upLeft, this[upLeft])
    }
  }

  inline fun forEachNeighborWithDiagonalsWrapped(index: Index, action: (T) -> Unit) {
    action(this[index.up().wrappedIn(this)])
    action(this[index.upRight().wrappedIn(this)])
    action(this[index.right().wrappedIn(this)])
    action(this[index.downRight().wrappedIn(this)])
    action(this[index.down().wrappedIn(this)])
    action(this[index.downLeft().wrappedIn(this)])
    action(this[index.left().wrappedIn(this)])
    action(this[index.upLeft().wrappedIn(this)])
  }

  inline fun forEachNeighborWithDiagonalsWrapped(point: Point, action: (T) -> Unit) {
    action(this[point.up().wrappedIn(this)])
    action(this[point.upRight().wrappedIn(this)])
    action(this[point.right().wrappedIn(this)])
    action(this[point.downRight().wrappedIn(this)])
    action(this[point.down().wrappedIn(this)])
    action(this[point.downLeft().wrappedIn(this)])
    action(this[point.left().wrappedIn(this)])
    action(this[point.upLeft().wrappedIn(this)])
  }

  inline fun forEachNeighborWithDiagonalsWrappedIndexed(index: Index, action: (Index, T) -> Unit) {
    index.up().wrappedIn(this).let { action(it, this[it]) }
    index.upRight().wrappedIn(this).let { action(it, this[it]) }
    index.right().wrappedIn(this).let { action(it, this[it]) }
    index.downRight().wrappedIn(this).let { action(it, this[it]) }
    index.down().wrappedIn(this).let { action(it, this[it]) }
    index.downLeft().wrappedIn(this).let { action(it, this[it]) }
    index.left().wrappedIn(this).let { action(it, this[it]) }
    index.upLeft().wrappedIn(this).let { action(it, this[it]) }
  }

  inline fun forEachNeighborWithDiagonalsWrappedPointed(point: Point, action: (Point, T) -> Unit) {
    point.up().wrappedIn(this).let { action(it, this[it]) }
    point.upRight().wrappedIn(this).let { action(it, this[it]) }
    point.right().wrappedIn(this).let { action(it, this[it]) }
    point.downRight().wrappedIn(this).let { action(it, this[it]) }
    point.down().wrappedIn(this).let { action(it, this[it]) }
    point.downLeft().wrappedIn(this).let { action(it, this[it]) }
    point.left().wrappedIn(this).let { action(it, this[it]) }
    point.upLeft().wrappedIn(this).let { action(it, this[it]) }
  }

  fun flatten(): List<T> = toList()

  operator fun get(row: Int, column: Int): T = data[(row * numColumns) + column]

  operator fun get(index: Index): T = get(index.row, index.column)

  operator fun get(point: Point): T = get(point.row, point.column)

  fun getOrNull(row: Int, column: Int): T? {
    return if (row in rowIndices && column in columnIndices) {
      get(row, column)
    } else {
      null
    }
  }

  fun getOrNull(index: Index): T? = getOrNull(index.row, index.column)

  fun getOrNull(point: Point): T? = getOrNull(point.row, point.column)

  fun getOrDefault(row: Int, column: Int, defaultValue: T): T =
    getOrElse(row, column) { defaultValue }

  fun getOrDefault(index: Index, defaultValue: T): T =
    getOrDefault(index.row, index.column, defaultValue)

  fun getOrDefault(point: Point, defaultValue: T): T =
    getOrDefault(point.row, point.column, defaultValue)

  inline fun getOrElse(row: Int, column: Int, defaultValue: (Index) -> T): T {
    return if (row in rowIndices && column in columnIndices) {
      get(row, column)
    } else {
      defaultValue(Index(row, column))
    }
  }

  inline fun getOrElse(index: Index, defaultValue: (Index) -> T): T =
    getOrElse(index.row, index.column, defaultValue)

  inline fun getOrElse(point: Point, defaultValue: (Point) -> T): T =
    getOrElse(point.row, point.column) { (row, column) -> defaultValue(Point(column, row)) }

  fun getWrapped(row: Int, column: Int): T = get(row.mod(numRows), column.mod(numColumns))

  fun getWrapped(index: Index): T = getWrapped(index.row, index.column)

  fun getWrapped(point: Point): T = getWrapped(point.row, point.column)

  override fun hashCode(): Int {
    var result = 1
    result = (result * 31) + numRows
    result = (result * 31) + numColumns
    result = (result * 31) + data.hashCode()
    return result
  }

  fun indexOf(element: T): Index = indexOfFirst { it == element }

  fun indexOfFirst(element: T): Index = indexOfFirst { it == element }

  inline fun indexOfFirst(predicate: (T) -> Boolean): Index = indices.first { predicate(this[it]) }

  fun indexOfFirstOrNull(element: T): Index? = indexOfFirstOrNull { it == element }

  inline fun indexOfFirstOrNull(predicate: (T) -> Boolean): Index? =
    indices.firstOrNull { predicate(this[it]) }

  fun lastIndexOf(element: T): Index = indexOfLast { it == element }

  fun indexOfLast(element: T): Index = indexOfLast { it == element }

  inline fun indexOfLast(predicate: (T) -> Boolean): Index = indices.last { predicate(this[it]) }

  fun indexOfLastOrNull(element: T): Index? = indexOfLastOrNull { it == element }

  inline fun indexOfLastOrNull(predicate: (T) -> Boolean): Index? =
    indices.lastOrNull { predicate(this[it]) }

  inline fun last(predicate: (T) -> Boolean): T = data.last(predicate)

  inline fun lastIndexed(predicate: (Index, T) -> Boolean): T =
    this[indices.last { predicate(it, this[it]) }]

  inline fun lastPointed(predicate: (Point, T) -> Boolean): T =
    this[points.last { predicate(it, this[it]) }]

  inline fun <R> map(crossinline transform: (T) -> R): Grid<R> =
    Grid(numRows, numColumns) { transform(this[it]) }

  inline fun <R> mapIndexed(crossinline transform: (Index, T) -> R): Grid<R> =
    Grid(numRows, numColumns) { transform(it, this[it]) }

  inline fun <R> mapPointed(crossinline transform: (Point, T) -> R): Grid<R> =
    Grid(numRows, numColumns) { transform(it.toPointIn(this), this[it]) }

  fun rows(): List<List<T>> =
    List(numRows) { row -> List(numColumns) { column -> get(row, column) } }

  fun row(row: Int): List<T> {
    if (row !in rowIndices) throw IndexOutOfBoundsException("row")
    return List(numColumns) { column -> get(row, column) }
  }

  fun rowWrapped(row: Int): List<T> = row(row.mod(numRows))

  operator fun set(row: Int, column: Int, value: T): T =
    value.also { data[(row * numColumns) + column] = value }

  operator fun set(index: Index, value: T): T = set(index.row, index.column, value)

  operator fun set(point: Point, value: T): T = set(point.row, point.column, value)

  fun setWrapped(row: Int, column: Int, value: T): T =
    set(row.mod(numRows), column.mod(numColumns), value)

  fun setWrapped(index: Index, value: T): T = setWrapped(index.row, index.column, value)

  fun setWrapped(point: Point, value: T): T = setWrapped(point.row, point.column, value)

  inline fun single(predicate: (T) -> Boolean): T = data.single(predicate)

  inline fun singleIndexed(predicate: (Index, T) -> Boolean): T =
    this[indices.single { predicate(it, this[it]) }]

  inline fun singlePointed(predicate: (Point, T) -> Boolean): T =
    this[points.single { predicate(it, this[it]) }]

  fun swap(aRow: Int, aColumn: Int, bRow: Int, bColumn: Int) {
    this[aRow, aColumn] = this[bRow, bColumn].also { this[bRow, bColumn] = this[aRow, aColumn] }
  }

  fun swap(a: Index, b: Index) = swap(a.row, a.column, b.row, b.column)

  fun swap(a: Point, b: Point) = swap(a.row, a.column, b.row, b.column)

  fun toGrid(): Grid<T> = Grid(numRows, numColumns) { this[it] }

  fun toList(): List<T> = data.toList()

  fun toMutableList(): List<T> = data.toMutableList()

  fun toSet(): Set<T> = data.toSet()

  fun toMutableSet(): Set<T> = data.toMutableSet()

  fun values(): List<T> = toList()

  override fun toString(): String = data.joinToString(",\n", "[\n", "\n]") { "  $it" }

  fun transpose(): Grid<T> = Grid(numColumns, numRows) { (row, column) -> this[column, row] }

  @PublishedApi internal val Point.row: Int
    get() = numRows - 1 - y

  @PublishedApi internal val Point.column: Int
    get() = x
}

fun <T> Grid(size: Index, init: (Index) -> T): Grid<T> = Grid(size.row, size.column, init)

inline fun <T> Grid(size: Point, crossinline init: (Point) -> T): Grid<T> =
  Grid(size.y, size.x) { (row, column) -> init(Point(column, size.y - 1 - row)) }

fun <T> Grid(numRows: Int, numColumns: Int, initialValue: T): Grid<T> =
  Grid(numRows, numColumns) { initialValue }

fun <T> Grid(size: Index, initialValue: T): Grid<T> = Grid(size.row, size.column, initialValue)

fun <T> Grid(size: Point, initialValue: T): Grid<T> = Grid(size.y, size.x, initialValue)

fun <T> Grid(numRows: Int, numColumns: Int, initialValues: Iterable<T>): Grid<T> {
  val list = if (initialValues is List<T>) initialValues else initialValues.toList()
  require(list.size == numRows * numColumns) { "Initial values has incorrect size." }
  return Grid(numRows, numColumns) { (row, column) -> list[(row * numColumns) + column] }
}

fun <T> Grid(size: Index, initialValues: Iterable<T>): Grid<T> =
  Grid(size.row, size.column, initialValues)

fun <T> Grid(size: Point, initialValues: Iterable<T>): Grid<T> = Grid(size.y, size.x, initialValues)

fun <T> Grid(data: List<List<T>>): Grid<T> {
  require(data.isNotEmpty()) { "Data must not be empty." }
  require(data.all { it.size == data.first().size }) { "Data must be rectangular." }
  return Grid(data.size, data.first().size) { (row, column) -> data[row][column] }
}

@JvmName("copyListOfGrid")
fun <T> List<Grid<T>>.copy(): List<Grid<T>> = map { it.toGrid() }

@JvmName("copyListOfListOfGrid")
fun <T> List<List<Grid<T>>>.copy(): List<List<Grid<T>>> = map { it.copy() }

fun Grid<Boolean>.render(on: Char = '█', off: Char = '·'): String = render { if (it) on else off }

fun Grid<Char>.render(): String = render { it }

inline fun <T> Grid<T>.render(renderer: (T) -> Char): String {
  val grid = this
  return buildString {
    for (row in rowIndices) {
      for (column in columnIndices) {
        append(renderer(grid[row, column]))
      }
      append('\n')
    }
  }
}
