package io.project.gitcasestudy.model


import com.google.gson.annotations.SerializedName

data class GitResponse (

    @SerializedName("id")
    val id: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("watchers")
    val watchers: Int
)