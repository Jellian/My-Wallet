package com.kotlin.mywallet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UserDb : RoomDatabase() {

    companion object {
        private var userInstance: UserDb? = null

        const val DB_NAME = "MyWallet_DB"

        fun getInstance(context: Context) : UserDb?{
            if(userInstance==null){

                synchronized(UserDb::class){
                    userInstance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDb::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration() // al cambiar de version, destruir info en vez de migrar
                        .build()
                }
            }

            return userInstance
        }
    }

    abstract fun userDao(): UserDao
}