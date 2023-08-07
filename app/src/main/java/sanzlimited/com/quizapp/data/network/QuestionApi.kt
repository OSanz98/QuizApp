package sanzlimited.com.quizapp.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.util.constants.AppConstants
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("api/v1/questions")
    suspend fun getAllDifficultyQuestions(
        @Query("category")
        category: String,
        @Header("X-Api-Key")
        apiKey: String = AppConstants.API_KEY
    ): Question

    @GET("api/v1/questions")
    suspend fun getSpecificDifficultyQuestions(
        @Query("category")
        category: String,
        @Query("difficulty")
        difficulty: String,
        @Header("X-Api-Key")
        apiKey: String = AppConstants.API_KEY
    ): Question
}