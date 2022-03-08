package io.project.gitcasestudy.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.project.gitcasestudy.R
import io.project.gitcasestudy.model.pojo.GitObject
import kotlin.properties.Delegates

class GitAdapter : RecyclerView.Adapter<GitViewHolder>() {

    var gitList: MutableList<GitObject> by Delegates.observable(mutableListOf<GitObject>()) { _, old, new ->
        autoNotify(old, new) { o, n ->
            o.id == n.id
                    && o.description == n.description
                    && o.title == n.title
                    && o.language == n.language
                    && o.loginName == n.loginName
        }
    }

    private lateinit var clickListener: (GitObject) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.git_list_item, parent, false)
        return GitViewHolder(view)
    }

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        val gitObject = gitList[position]
        holder.txtName?.text = gitObject.title
        holder.txtLanguage?.text = gitObject.language
        holder.txtNumberOfWatchers?.text = gitObject.numberOfWatchers.toString()
        holder.setOnClickListener(gitObject, clickListener)
    }

    override fun getItemCount(): Int {
        return gitList.size
    }

    fun setClickListener(clickListener: (GitObject) -> Unit) {
        this.clickListener = clickListener
    }
}

class GitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    internal var txtName: TextView? = null
    internal var txtLanguage: TextView? = null
    internal var txtNumberOfWatchers: TextView? = null
    private var clParent: ConstraintLayout? = null

    init {
        txtName = itemView.findViewById(R.id.txtName)
        txtLanguage = itemView.findViewById(R.id.txtLanguage)
        txtNumberOfWatchers = itemView.findViewById(R.id.txtNumberOfWatchers)
        clParent = itemView.findViewById(R.id.clParent)
    }

    fun setOnClickListener(gitObject: GitObject, clickListener: (GitObject) -> Unit) {
        clParent?.setOnClickListener { clickListener(gitObject) }
    }

}
