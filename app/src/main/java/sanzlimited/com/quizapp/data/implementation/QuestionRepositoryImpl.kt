package sanzlimited.com.quizapp.data.implementation

import android.util.Log
import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.model.QuestionItem
import sanzlimited.com.quizapp.data.network.QuestionApi
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import java.lang.Exception
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val api: QuestionApi
): QuestionRepository {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    override suspend fun getAllDifficultyQuestions(category: String): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            //setting loading to true so we can show a loading screen to let user know that a request is happening
            dataOrException.loading = true
            dataOrException.data = api.getAllDifficultyQuestions(category)
            //when we get data then set loading to false to show we got a response
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exc", "getAllDifficultyQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }

    override suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            //setting loading to true so we can show a loading screen to let user know that a request is happening
            dataOrException.loading = true
            dataOrException.data = api.getSpecificDifficultyQuestions(category, difficulty)
            //when we get data then set loading to false to show we got a response
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exc", "getAllDifficultyQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }

}