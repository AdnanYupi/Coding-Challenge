package com.codingchallenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingchallenge.model.responses.repositories.RepositoriesItem
import com.codingchallenge.model.responses.user.UserItem

@Database(entities = [UserItem::class, RepositoriesItem::class], version = 1)
@TypeConverters(UserDataConverter::class)
abstract class CodingChallengeDatabase : RoomDatabase() {

    abstract fun getCodingDao(): CodingChallengeDao
    //Init DB
    companion object {
        @Volatile
        private var instance: CodingChallengeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CodingChallengeDatabase::class.java, "codingChallenge.db"
            )
                .build()
    }
}