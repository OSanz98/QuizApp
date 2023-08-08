package sanzlimited.com.quizapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.model.QuestionItem
import sanzlimited.com.quizapp.domain.usecase.GetAllDifficultyQuestions
import sanzlimited.com.quizapp.domain.usecase.GetSpecificDifficultyQuestions
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val app: Application,
    private val getAllDifficultyQuestions: GetAllDifficultyQuestions,
    private val getSpecificDifficultyQuestions: GetSpecificDifficultyQuestions
): ViewModel() {
    //creating a mutable state so our components can use it and know what to do - setting it to empty object
    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception(""))
    )

    fun getAllQuestions(category: String){
        viewModelScope.launch {
            if (isNetworkAvailable(app)){
                data.value.loading = true
                data.value = getAllDifficultyQuestions.execute(category)
                if(data.value.data.toString().isNotEmpty()){
                    data.value.loading = false
                }
            }
        }
    }

    fun getSpecificDifficulty(category: String, difficulty: String){
        viewModelScope.launch {
            if (isNetworkAvailable(app)){
                data.value.loading = true
                data.value = getSpecificDifficultyQuestions.execute(category, difficulty)
                if (data.value.data.toString().isNotEmpty()){
                    data.value.loading = false
                }
            }
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}