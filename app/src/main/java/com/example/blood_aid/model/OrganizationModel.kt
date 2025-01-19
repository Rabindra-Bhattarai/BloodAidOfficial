package com.example.blood_aid.model

import android.os.Parcel
import android.os.Parcelable

data class OrganizationModel(
    var userId: String = "",
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var address: String = "",
    var registrationNumber: String = "",
    var enabled: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(address)
        parcel.writeString(registrationNumber)
        parcel.writeByte(if (enabled) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrganizationModel> {
        override fun createFromParcel(parcel: Parcel): OrganizationModel {
            return OrganizationModel(parcel)
        }

        override fun newArray(size: Int): Array<OrganizationModel?> {
            return arrayOfNulls(size)
        }
    }
}
