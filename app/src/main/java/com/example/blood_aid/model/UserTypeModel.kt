package com.example.blood_aid.model

import android.os.Parcel
import android.os.Parcelable

data class UserTypeModel(
    var userId: String = "",
    var userType: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserTypeModel> {
        override fun createFromParcel(parcel: Parcel): UserTypeModel {
            return UserTypeModel(parcel)
        }

        override fun newArray(size: Int): Array<UserTypeModel?> {
            return arrayOfNulls(size)
        }
    }
}
