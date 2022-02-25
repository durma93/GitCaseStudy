package io.project.gitcasestudy.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.project.gitcasestudy.R
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.utils.DataState
import io.project.gitcasestudy.utils.GIT_OBJECT
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
                            val intent = Intent(this, DetailsActivity::class.java)
                            intent.putExtra(GIT_OBJECT, gitObject)
                            startActivity(intent)
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