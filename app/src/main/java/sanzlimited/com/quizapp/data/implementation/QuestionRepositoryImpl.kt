package sanzlimited.com.quizapp.data.implementation

import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.model.QuestionItem
import sanzlimited.com.quizapp.data.network.QuestionApi
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import java.lang.Exception
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(
    private val api: QuestionApi
): QuestionRepository {
    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    override suspend fun getAllDifficultyQuestions(category: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String) {
        TODO("Not yet implemented")
    }
}