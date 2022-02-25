package io.project.gitcasestudy.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.project.gitcasestudy.R
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.utils.DataState
import io.project.gitcasestudy.viewmodel.GitListStateEvent
import io.project.gitcasestudy.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val gitViewModel: GitViewModel by viewModels()

    @Inject
    lateinit var gitAdapter: GitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        gitViewModel.setStateEvent(GitListStateEvent.GetRepos)

        rvGitRepos.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gitAdapter
        }
    }

    private fun subscribeObservers() {
        gitViewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<GitObject>> -> {
                    gitAdapter.gitList = dataState.data
                    gitAdapter.setClickListener { gitObject: GitObject ->
                        run {
                            Toast.makeText(
                                this,
                                "subscribeObservers: ${gitObject.language}, ${gitObject.numberOfWatchers}, ${gitObject.loginName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {

                }
            }
        })
    }
}