package io.project.gitcasestudy.repository

import android.util.Log
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.network.GitRetrofit
import io.project.gitcasestudy.network.NetworkMapper
import io.project.gitcasestudy.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val TAG = "CommentRepository"

class GitRepository
constructor(
    private val gitRetrofit: GitRetrofit,
    private val networkMapper: NetworkMapper
) {

    suspend fun getRepos(): Flow<DataState<List<GitObject>>> = flow {
        emit(DataState.Loading)

        try {
            val gitReposNetwork = gitRetrofit.getGitRepos(20, 1)
            val gitObject = networkMapper.mapFromEntityList(gitReposNetwork)
            emit(DataState.Success(gitObject))
        } catch (e: Exception) {
            Log.e(TAG, "getComments: ", e)
            emit(DataState.Error(e))
        }
    }
}