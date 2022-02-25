package io.project.gitcasestudy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.project.gitcasestudy.network.GitRetrofit
import io.project.gitcasestudy.network.NetworkMapper
import io.project.gitcasestudy.repository.GitRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        retrofit: GitRetrofit,
        networkMapper: NetworkMapper
    ): GitRepository {
        return GitRepository(retrofit, networkMapper)
    }
}