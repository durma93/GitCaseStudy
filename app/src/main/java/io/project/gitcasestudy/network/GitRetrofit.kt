package io.project.gitcasestudy.network

import io.project.gitcasestudy.model.GitResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitRetrofit {

    @GET("users/google/repos")
    suspend fun getGitRepos(@Query("per_page") perPage: Int,
                            @Query("page") page: Int): List<GitResponse>
}