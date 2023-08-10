package sanzlimited.com.quizapp.domain.usecase

import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.data.repository.QuestionRepository

class GetSpecificDifficultyQuestions (private val questionRepository: QuestionRepository) {
    suspend fun execute(category: String, difficulty: String): Resource<Question> {
        return questionRepository.getSpecificDifficultyQuestions(category, difficulty)
    }
}