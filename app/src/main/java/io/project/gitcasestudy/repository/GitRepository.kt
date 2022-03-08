package io.project.gitcasestudy.repository

import android.util.Log
import io.project.gitcasestudy.model.GitResponse
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
    private val listOfData : MutableList<GitObject> = mutableListOf<GitObject>()

    suspend fun getRepos(pageNumbar: Int): Flow<DataState<List<GitObject>>> = flow {
        emit(DataState.Loading)

        try {
            val gitReposNetwork = gitRetrofit.getGitRepos(20, pageNumbar)
            val gitObject = networkMapper.mapFromEntityList(gitReposNetwork)

            listOfData.addAll(gitObject)

            Log.d(TAG, "getRepos: ${listOfData.toString()}")
            emit(DataState.Success(listOfData))
        } catch (e: Exception) {
            Log.e(TAG, "getComments: ", e)
            emit(DataState.Error(e))
        }
    }
}