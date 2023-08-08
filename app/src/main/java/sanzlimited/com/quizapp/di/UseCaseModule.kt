package sanzlimited.com.quizapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import sanzlimited.com.quizapp.domain.usecase.GetAllDifficultyQuestions
import sanzlimited.com.quizapp.domain.usecase.GetSpecificDifficultyQuestions
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllDifficultyUseCase(questionRepository: QuestionRepository): GetAllDifficultyQuestions {
        return GetAllDifficultyQuestions(questionRepository)
    }

    @Singleton
    @Provides
    fun provideSpecificDifficultyUseCase(questionRepository: QuestionRepository): GetSpecificDifficultyQuestions {
        return GetSpecificDifficultyQuestions(questionRepository)
    }
}