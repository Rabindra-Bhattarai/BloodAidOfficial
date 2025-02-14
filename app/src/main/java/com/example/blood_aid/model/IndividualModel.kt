package com.example.blood_aid.model

import android.os.Parcel
import android.os.Parcelable

data class IndividualModel (
    var userId:String= "",
    var fullName:String="",
    var email:String="",
    var phoneNumber:String="",
    var address:String="",
    var citizenshipNumber:String="",
    var bloodGroup:String="",
    var gender:String="",
    ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
        parcel.readString().toString()?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeString(address)
        parcel.writeString(citizenshipNumber)
        parcel.writeString(bloodGroup)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IndividualModel> {
        override fun createFromParcel(parcel: Parcel): IndividualModel {
            return IndividualModel(parcel)
        }

        override fun newArray(size: Int): Array<IndividualModel?> {
            return arrayOfNulls(size)
        }
    }
}