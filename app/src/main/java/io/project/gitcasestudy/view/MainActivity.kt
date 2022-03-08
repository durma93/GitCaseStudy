package io.project.gitcasestudy.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var pageNumber = 1

    lateinit var myLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObservers()
        gitViewModel.setStateEvent(GitListStateEvent.GetRepos, pageNumber)

        rvGitRepos.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@MainActivity)
            myLayoutManager = layoutManager as LinearLayoutManager
            adapter = gitAdapter
        }

        rvGitRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visiblePosition: Int = myLayoutManager.findLastVisibleItemPosition()
                Log.d(TAG, "onScrolled: $visiblePosition")
                if (visiblePosition == pageNumber * 19) {
                    Log.d(TAG, "onScrolled 2: $visiblePosition")
                    pageNumber += 1;
                    Log.d(TAG, "onScrolled 2 pageNumber: $pageNumber")
                    gitViewModel.setStateEvent(GitListStateEvent.GetRepos, pageNumber)
                }
            }
        })
    }

    private fun subscribeObservers() {
        gitViewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<GitObject>> -> {
                    gitAdapter.gitList = dataState.data.toMutableList()

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