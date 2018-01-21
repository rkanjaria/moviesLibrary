package files

/**
 * Created by MF on 03-01-2018.
 */

fun getYearFromDate(date : String ?) : String {

    // 2017-05-23 format

    if(date != null && !date.isBlank()) {
        val year = date.split("-")
        return year[0]
    }else{
        return ""
    }
}