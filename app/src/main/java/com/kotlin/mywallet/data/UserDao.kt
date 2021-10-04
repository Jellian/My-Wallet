package com.kotlin.mywallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE user_name LIKE :username")
    fun findByName(username: String): User

    @Query("SELECT * FROM user WHERE email = :email AND password = :passwd")
    fun findByEmailAndPassword(email: String, passwd: String): User

    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :passwd")
    fun findByUsernameAndPassword(userName: String, passwd: String): User

    @Query("SELECT EXISTS (SELECT * FROM user WHERE user_name = :userName AND password = :password)")
    fun checkIfExistsUsernameAndPassword(userName: String, password: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM user WHERE email = :userEmail AND password = :password)")
    fun checkIfExistsEmailAndPassword(userEmail: String, password: String): Boolean

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

}