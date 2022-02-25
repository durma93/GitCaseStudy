package io.project.gitcasestudy.model.pojo

import android.os.Parcel
import android.os.Parcelable

data class GitObject(
    val id : Int,
    val title: String?,
    val language: String?,
    val numberOfWatchers: Int?,
    val description: String?,
    val loginName: String?,
    val repositoryUpdateDate: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(language)
        parcel.writeValue(numberOfWatchers)
        parcel.writeString(description)
        parcel.writeString(loginName)
        parcel.writeString(repositoryUpdateDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GitObject> {
        override fun createFromParcel(parcel: Parcel): GitObject {
            return GitObject(parcel)
        }

        override fun newArray(size: Int): Array<GitObject?> {
            return arrayOfNulls(size)
        }
    }
}
