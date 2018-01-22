package files

/**
 * Created by MF on 03-01-2018.
 */

fun getYearFromDate(date: String?): String {

    // 2017-05-23 format

    if (date != null && !date.isBlank()) {
        val year = date.split("-")
        return year[0]
    } else {
        return ""
    }
}

fun getDateWithCustomFormat(date: String?): String {

    // 2017-05-23 format

    if (date != null && !date.isBlank()) {
        val year = date.split("-")
        var month = ""
        when (year[1].toInt()) {
            1 -> month = "Jan"
            2 -> month = "Feb"
            3 -> month = "Mar"
            4 -> month = "Apr"
            5 -> month = "May"
            6 -> month = "June"
            7 -> month = "July"
            8 -> month = "Aug"
            9 -> month = "Sep"
            10 -> month = "Oct"
            11 -> month = "Nov"
            12 -> month = "Dec"
        }
        return year[2] + " " + month + " " + year[0]

    } else {
        return ""
    }
}