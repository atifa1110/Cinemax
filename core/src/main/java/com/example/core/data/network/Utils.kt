package com.example.core.data.network

import com.example.core.R
import com.example.core.ui.model.Genre
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getDateCustomFormat(releaseDate: String?): String {
    // Determine which date string to use
    val dateString = releaseDate?.takeIf { it.isNotEmpty() }

    // Check if the dateString is null or empty
    if (dateString.isNullOrEmpty()) {
        return "No Release Date"
    }

    return try {
        // Parse the original date string
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date? = inputFormat.parse(dateString)

        // Define the desired output format
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        // Format the date to the desired string
        if (date != null) {
            outputFormat.format(date)
        } else {
            "No Release Date"
        }
    } catch (e: Exception) {
        "No Release Date"
    }
}
//
fun getYearFromFormattedDate(dateString: String): String {
    return try {
        // Define the input format that matches the given date string
        val inputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

        // Remove the "On " prefix and parse the date
        val date = inputFormat.parse(dateString.removePrefix("On "))

        // Extract the year using SimpleDateFormat
        val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())

        if (date != null) {
            outputFormat.format(date)
        } else {
            "No Release Date"
        }
    } catch (e: Exception) {
        "No Release Date"
    }
}

fun String.asMediaType() = capitalizeFirstLetter(this)
fun capitalizeFirstLetter(input: String?): String {
    return if (input?.isNotEmpty() == true) {
        input.substring(0, 1).uppercase() + input.substring(1).lowercase()
    } else {
        input?:""
    }
}
fun buildImageUrl(path: String) = Constants.IMAGE_URL + path
fun String.asImageURL() = buildImageUrl(path = this)

fun Double.getRating() = calculateStarRating(voteAverage = this)
fun calculateStarRating(voteAverage: Double?): Double {
    val starRating = voteAverage?.div(2)?.coerceIn(1.0, 5.0) ?: 0.0
    return (starRating * 10).toInt() / 10.0
}
fun Double.getPercentageRating() = calculatePercentage(this)
fun calculatePercentage(vote: Double): Int {
    return ((vote / 10.0) * 100).toInt()
}
fun String.getFormatReleaseDate() = getDateCustomFormat(this)
fun String.getYearReleaseDate() = getYearFromFormattedDate(dateString = this)


fun getGenreName(genre: List<Genre>?): Int {
    return try {
        genre?.get(0)?.nameResourceId?: R.string.none
    } catch (e: IndexOutOfBoundsException) {
        R.string.none
    }
}