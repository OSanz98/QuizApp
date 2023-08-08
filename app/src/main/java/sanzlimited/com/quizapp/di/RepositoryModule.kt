package sanzlimited.com.quizapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sanzlimited.com.quizapp.data.implementation.QuestionRepositoryImpl
import sanzlimited.com.quizapp.data.network.QuestionApi
import sanzlimited.com.quizapp.data.repository.QuestionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi): QuestionRepository {
        return QuestionRepositoryImpl(api)
    }
}