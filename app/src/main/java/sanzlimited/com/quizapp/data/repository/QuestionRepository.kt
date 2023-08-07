package sanzlimited.com.quizapp.data.repository

interface QuestionRepository {
    suspend fun getAllDifficultyQuestions(category: String)
    suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String)
}