package sanzlimited.com.quizapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sanzlimited.com.quizapp.data.implementation.QuestionRepositoryImpl
import sanzlimited.com.quizapp.data.network.QuestionApi
import sanzlimited.com.quizapp.util.constants.AppConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    Provide one instance of our Question API across the app
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideQuestionAPI(retrofit: Retrofit): QuestionApi {
        return retrofit.create(QuestionApi::class.java)
    }
}