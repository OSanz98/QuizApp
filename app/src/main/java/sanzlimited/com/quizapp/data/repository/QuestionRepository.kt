package sanzlimited.com.quizapp.data.repository

import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question

interface QuestionRepository {
    suspend fun getAllDifficultyQuestions(category: String): Resource<Question>
    suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String): Resource<Question>
}