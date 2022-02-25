package io.project.gitcasestudy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.network.GitRetrofit
import io.project.gitcasestudy.network.NetworkMapper
import io.project.gitcasestudy.repository.GitRepository
import io.project.gitcasestudy.utils.BASE_URL
import io.project.gitcasestudy.utils.DataState
import io.project.gitcasestudy.viewmodel.GitListStateEvent
import io.project.gitcasestudy.viewmodel.GitViewModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class GitViewModelTest : TestCase() {

    private lateinit var gitViewModel: GitViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .create()
                )
            ).build().create(GitRetrofit::class.java)
        val networkMapper = NetworkMapper()
        val repository = GitRepository(retrofit, networkMapper)

        gitViewModel = GitViewModel(repository)
    }


    @Test
    fun testCommentViewModel() {
        gitViewModel.setStateEvent(GitListStateEvent.GetRepos)
        when (val dataState = gitViewModel.dataState.getOrAwaitValue()) {
            is DataState.Success<List<GitObject>> -> {
                val result = dataState.data.find {
                    it.loginName == "google"
                }

                assertThat(result != null).isTrue()
            }
            else -> {}
        }
    }
}