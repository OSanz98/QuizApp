package sanzlimited.com.quizapp.domain.usecase

import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.model.QuestionItem
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import java.lang.Exception


class GetAllDifficultyQuestions (private val questionRepository: QuestionRepository) {
    suspend fun execute(category: String): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        return questionRepository.getAllDifficultyQuestions(category)
    }
}