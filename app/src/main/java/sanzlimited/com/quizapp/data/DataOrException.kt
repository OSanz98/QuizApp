package sanzlimited.com.quizapp.data

import java.lang.Exception

//here we are creating a wrapper class to wrap around our API response, so that we
//can add other values such as loading - this can be used in our UI layer to show a
//loading indicator for example
data class DataOrException<T, Boolean, E:Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)
