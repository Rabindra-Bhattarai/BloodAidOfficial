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
    var noOfTimeDonated:Int=0
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
        parcel.readInt()?:0
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
        parcel.writeInt(noOfTimeDonated)
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