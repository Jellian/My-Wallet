package com.kotlin.mywallet.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.data.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Insert
    suspend fun insertAllUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(vararg users: User)

    @Query("UPDATE user SET grandTotal = grandTotal + :amount WHERE username = :username")
    suspend fun  updateUserGrandTotal(username: String, amount: Float)

    @Query("UPDATE user SET actualGoal = :newGoal WHERE username = :username")
    suspend fun updateActualGoalByUser(username: String, newGoal: Float)

    @Query("UPDATE user SET uriRef = :uriRefAsString WHERE username = :username")
    suspend fun updateUriRefByUser(username: String, uriRefAsString: String?)

    @Query("SELECT grandTotal FROM user WHERE username = :username")
    fun getUserGrandTotal(username: String): LiveData<Float>

    @Query("SELECT * FROM user WHERE username LIKE :username")
    suspend fun findByName(username: String): User

    @Transaction
    @Query("SELECT * FROM user WHERE email = :email AND password = :passwd")
    fun getUserByEmailAndPassword(email: String, passwd: String): User?

    @Query("SELECT * FROM user WHERE username = :userName AND password = :passwd")
    fun getUserByNameAndPassword(userName: String, passwd: String): User?

    @Query("SELECT totalAmount FROM account WHERE username = :username")
    fun getTotalAmountsFromAllAccountsByUser(username: String): List<Float>

    @Query("SELECT actualGoal FROM user WHERE username = :username")
    fun getActualGoalByUser(username: String): LiveData<Float>

    @Query("SELECT uriRef FROM user WHERE username = :username")
    fun getUriRefByUser(username: String): String?

    @Delete
    suspend fun delete(user: User)


    // THIS IS FOR RELATION USER - ACCOUNTS

    @Insert
    suspend fun insertAllAccounts(accounts: List<Account>)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Update
    suspend fun updateAccountById(vararg account: Account)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(vararg account: Account)


    @Query("UPDATE account SET totalAmount = totalAmount + :amount WHERE accountName = :accountName AND username = :username")
    suspend fun  updateAccountTotalAmount(accountName: String, username: String, amount: Float)

    @Query("SELECT count(*) FROM account WHERE username = :username")
    fun getNumberOfAccountsByUser(username: String): Int

    @Transaction
    @Query("SELECT accountName FROM account WHERE userName = :userName")
    fun getAccountNamesByUser(userName: String): List<String>

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName")
    fun getAccountsByUser(userName: String): LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE id = :accountId")
    fun getAccountById(accountId: Int): Account


    @Query("SELECT count(*) FROM account WHERE username= :username LIMIT 1")
    fun getAccountsCountByUser(username: String): Int

    @Transaction
    @Query("SELECT * FROM account WHERE userName = :userName AND accountName = :accountName")
    fun getAccountByNameAndUser(accountName: String, userName: String): Account

    @Transaction
    @Query("SELECT totalAmount FROM account WHERE accountName = :accountName AND username = :username")
    fun getAccountTotalAmount(accountName: String, username: String): LiveData<Float>

    // THIS IS FOR RELATION ACCOUNT - CHARGES

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharge(vararg charge: Charge)

    @Delete
    suspend fun deleteCharge(charge: Charge)

    @Transaction
    @Query("SELECT * FROM charge WHERE userName = :userName AND accountName= :accountName ORDER BY date DESC")
    fun getChargesByUserAndAccount(userName: String, accountName: String): LiveData<List<Charge>>

    @Update
    suspend fun updateChargeById(vararg charge: Charge)

    @Insert
    suspend fun insertAllCharges(charges: List<Charge>)

    @Transaction
    @Query("DELETE FROM charge WHERE username = :username AND accountName = :accountName")
    suspend fun deleteChargesByUserAndAccount(username: String, accountName: String)

    @Transaction
    @Query("UPDATE charge SET accountName = :newAccountName WHERE accountName = :oldAccountName AND username = :username")
    suspend fun updateChargesAccountName(newAccountName: String, oldAccountName: String, username: String)

    @Query("SELECT * FROM charge WHERE id = :chargeId")
    fun getChargeById(chargeId: Int): Charge

}