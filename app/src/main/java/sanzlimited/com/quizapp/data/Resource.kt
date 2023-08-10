package sanzlimited.com.quizapp.data

//wrapper sealed class to set the state of the api call and store the response
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T> (data: T) : Resource<T>(data)
    class Loading<T> (data: T? = null) : Resource<T>(data)
    class Error<T> (message: String, data: T? = null) : Resource<T>(data, message)
}