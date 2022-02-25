package io.project.gitcasestudy.model.pojo

data class GitObject(
    val id : Int,
    val title: String?,
    val language: String?,
    val numberOfWatchers: Int?,
    val description: String?,
    val loginName: String,
    val repositoryUpdateDate: String
)
