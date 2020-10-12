package com.codingchallenge.model.responses.user

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    val created_at: String?,
    val email: String?,
    val gender: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String?,
    val status: String?,
    val updated_at: String?,
    val isCreatedOffline: Boolean = false
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(created_at)
        parcel.writeString(email)
        parcel.writeString(gender)
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(updated_at)
        parcel.writeByte(if (isCreatedOffline) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData {
            return UserData(parcel)
        }

        override fun newArray(size: Int): Array<UserData?> {
            return arrayOfNulls(size)
        }
    }
}