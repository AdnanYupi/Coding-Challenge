package com.codingchallenge.database

import androidx.room.TypeConverter
import com.codingchallenge.model.responses.user.User
import com.google.gson.Gson


//Use type converter to convert List of custom objects for Room
//Easiest way is probably using Gson converter
object UserDataConverter {
    @TypeConverter
    @JvmStatic
    fun toListOfStrings(userData: User): String {
        return Gson().toJson(userData)
    }

    @TypeConverter
    @JvmStatic
    fun fromListOfStrings(userData: String): User {
        return Gson().fromJson(userData, User::class.java)
    }
}