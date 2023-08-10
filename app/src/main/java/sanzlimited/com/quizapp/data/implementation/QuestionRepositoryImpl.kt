package sanzlimited.com.quizapp.data.implementation

import retrofit2.Response
import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.data.network.QuestionApi
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val api: QuestionApi
): QuestionRepository {
    override suspend fun getAllDifficultyQuestions(category: String): Resource<Question> {
        return responseToResource(api.getAllDifficultyQuestions(category))
    }

    override suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String): Resource<Question> {
        return responseToResource(api.getSpecificDifficultyQuestions(category, difficulty))
    }

    private fun responseToResource(response: Response<Question>): Resource<Question> {
        if(response.isSuccessful){
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

}
