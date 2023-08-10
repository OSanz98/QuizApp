package sanzlimited.com.quizapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.domain.usecase.GetAllDifficultyQuestions
import sanzlimited.com.quizapp.domain.usecase.GetSpecificDifficultyQuestions
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val app: Application,
    private val getAllDifficultyQuestions: GetAllDifficultyQuestions,
    private val getSpecificDifficultyQuestions: GetSpecificDifficultyQuestions
): ViewModel() {
    //creating a mutable state so our components can use it and know what to do - setting it to empty object
    private val _quizData = MutableLiveData<Resource<Question>>()
    val quizData: LiveData<Resource<Question>> = _quizData

    fun getAllQuestions(category: String){
        _quizData.value = Resource.Loading()
        viewModelScope.launch {
            if (isNetworkAvailable(app)){
                val apiResult = getAllDifficultyQuestions.execute(category)
                if(apiResult.data.toString().isNotEmpty() && apiResult.data != null){
                    _quizData.value = Resource.Success(apiResult.data)
                }
            } else {
                _quizData.value = Resource.Error("Internet isn't available")
            }
        }
    }

    fun getSpecificDifficulty(category: String, difficulty: String){
        _quizData.postValue(Resource.Loading())
        viewModelScope.launch {
            if (isNetworkAvailable(app)){
                _quizData.postValue(Resource.Loading())
                val apiResult = getSpecificDifficultyQuestions.execute(category, difficulty)
                if(apiResult.data.toString().isNotEmpty()){
                    _quizData.postValue(apiResult)
                }
            } else {
                _quizData.postValue(Resource.Error("Internet isn't available"))
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