package io.project.gitcasestudy.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.project.gitcasestudy.R
import io.project.gitcasestudy.model.pojo.GitObject
import io.project.gitcasestudy.utils.GIT_OBJECT
import kotlinx.android.synthetic.main.activity_details.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val gitObject = intent.getParcelableExtra<GitObject>(GIT_OBJECT)
        setTextViews(gitObject)
    }

    private fun setTextViews(gitObject: GitObject?) {
        txtTitleDetails.text = gitObject?.title
        txtLoginName.text = gitObject?.loginName
        txtWatchersDetails.text = gitObject?.numberOfWatchers.toString()
        txtUpdatedAt.text = parseDate(gitObject?.repositoryUpdateDate)
    }

    private fun parseDate(date: String?): String {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val output = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var newDate = Date()
        try {
            newDate = input.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return output.format(newDate)
    }
}