package sanzlimited.com.quizapp.data.repository

import dagger.Provides
//import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.data.model.QuestionItem
import java.lang.Exception

interface QuestionRepository {
    suspend fun getAllDifficultyQuestions(category: String): Resource<Question>
    suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String): Resource<Question>
}