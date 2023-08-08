package sanzlimited.com.quizapp.data.repository

import dagger.Provides
import sanzlimited.com.quizapp.data.DataOrException
import sanzlimited.com.quizapp.data.model.QuestionItem
import java.lang.Exception

interface QuestionRepository {

    suspend fun getAllDifficultyQuestions(category: String): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>
    suspend fun getSpecificDifficultyQuestions(category: String, difficulty: String): DataOrException<ArrayList<QuestionItem>, Boolean, Exception>
}