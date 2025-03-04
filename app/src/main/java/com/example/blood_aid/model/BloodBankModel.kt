package com.example.blood_aid.model

import android.os.Parcel
import android.os.Parcelable

data class BloodBankModel (
        var OrgId:String="",
        var A_POSITIVE:Int=0,
        var A_NEGATIVE:Int=0,
        var B_POSITIVE:Int=0,
        var B_NEGATIVE:Int=0,
        var AB_POSITIVE:Int=0,
        var AB_NEGATIVE:Int=0,
        var O_POSITIVE:Int=0,
        var O_NEGATIVE:Int=0
    ): Parcelable {
    fun getAvailableUnits(bloodGroup: String): Int {
        return when (bloodGroup) {
            "a_POSITIVE" -> A_POSITIVE
            "a_NEGATIVE" -> A_NEGATIVE
            "b_POSITIVE" -> B_POSITIVE
            "b_NEGATIVE" -> B_NEGATIVE
            "ab_POSITIVE" -> AB_POSITIVE
            "ab_NEGATIVE" -> AB_NEGATIVE
            "o_POSITIVE" -> O_POSITIVE
            "o_NEGATIVE" -> O_NEGATIVE
            else -> 0 // Return 0 if the blood group is not recognized
        }
    }

        constructor(parcel: Parcel) : this(
            parcel.readString().toString()?:"",
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0,
            parcel.readInt()?:0
        )

        {

        }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(OrgId)
        parcel.writeInt(A_POSITIVE)
        parcel.writeInt(A_NEGATIVE)
        parcel.writeInt(B_POSITIVE)
        parcel.writeInt(B_NEGATIVE)
        parcel.writeInt(AB_POSITIVE)
        parcel.writeInt(AB_NEGATIVE)
        parcel.writeInt(O_POSITIVE)
        parcel.writeInt(O_NEGATIVE)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BloodBankModel> {
        override fun createFromParcel(parcel: Parcel): BloodBankModel {
            return BloodBankModel(parcel)
        }

        override fun newArray(size: Int): Array<BloodBankModel?> {
            return arrayOfNulls(size)
        }
    }
}