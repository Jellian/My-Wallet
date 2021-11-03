package com.kotlin.mywallet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.data.entities.User

@Database(
    entities = [
        User::class,
        Account::class,
        Charge::class
               ],
    version = 2,
    exportSchema = false)

abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {

        @Volatile
        private var USERINSTANCE: UserDatabase? = null

        private const val DB_NAME = "myWallet_db"

        fun getInstance(context: Context) : UserDatabase?{



            if(USERINSTANCE == null){
                //UserDatabase::class
                synchronized(this){
                    USERINSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration() // Al cambiar de version, destruir info en vez de migrar
                        .build()
                }
            }
            return USERINSTANCE
        }
    }

}