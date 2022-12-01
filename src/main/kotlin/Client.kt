import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Path

class Client(private val session: String) {

  fun downloadInput(year: Int, day: Int): InputResult {
    val file = Path.of(cacheDirectory.toString(), "Input-$year-$day.txt").toFile()
    return if (file.exists()) {
      InputResult(file, file.readText())
    } else {
      download("https://adventofcode.com/$year/day/$day/input").let {
        file.parentFile.mkdirs()
        file.writeText(it)
        InputResult(file, it)
      }
    }
  }

  fun uploadAnswer(year: Int, day: Int, level: Int, answer: Any?): AnswerResult {
    val url = "https://adventofcode.com/$year/day/$day/answer"
    val formData = mapOf("level" to level.toString(), "answer" to answer.toString())
    val response = upload(url, formData)
    return when {
      response.contains("That's the right answer!") ->
        AnswerResult.Correct(year, day, level, answer)
      response.contains("That's not the right answer.") ->
        AnswerResult.Incorrect(year, day, level, answer)
      response.contains("That's not the right answer; your answer is too high.") ->
        AnswerResult.IncorrectTooHigh(year, day, level, answer)
      response.contains("You gave an answer too recently") ->
        AnswerResult.AnsweredTooRecently(year, day, level, answer)
      response.contains("You don't seem to be solving the right level.") ->
        AnswerResult.AlreadyAnswered(year, day, level, answer)
      else ->
        AnswerResult.Unknown(year, day, level, answer, response)
    }
  }

  private fun download(url: String): String {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .header("cookie", "session=$session")
      .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    if (response.statusCode() != 200) {
      throw IOException("Download failed: $url: HTTP ${response.statusCode()}: ${response.body()}")
    }
    return response.body()
  }

  private fun upload(url: String, values: Map<String, String>): String {
    val client = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
      .uri(URI.create(url))
      .header("cookie", "session=$session")
      .header("content-type", "application/x-www-form-urlencoded")
      .POST(formData(values))
      .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    if (response.statusCode() != 200) {
      throw IOException("Download failed: $url: HTTP ${response.statusCode()}: ${response.body()}")
    }
    return response.body()
  }

  private fun formData(values: Map<String, String>): HttpRequest.BodyPublisher {
    val data = values.map { (key, value) ->
      "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
    }.joinToString("&")
    return HttpRequest.BodyPublishers.ofString(data)
  }

  class InputResult(val file: File, val contents: String)

  sealed interface AnswerResult {

    val year: Int
    val day: Int
    val level: Int
    val answer: Any?

    data class Correct(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
    ) : AnswerResult

    data class Incorrect(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
    ) : AnswerResult

    data class IncorrectTooHigh(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
    ) : AnswerResult

    data class AnsweredTooRecently(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
    ) : AnswerResult

    data class AlreadyAnswered(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
    ) : AnswerResult

    data class Unknown(
      override val year: Int,
      override val day: Int,
      override val level: Int,
      override val answer: Any?,
      val rawResponse: String,
    ) : AnswerResult
  }

  companion object {

    private val cacheDirectory: Path =
      Path.of(System.getProperty("java.io.tmpdir"), "AdventOfCode", "Inputs")

    fun clearCache() {
      cacheDirectory.toFile().deleteRecursively()
    }
  }
}
