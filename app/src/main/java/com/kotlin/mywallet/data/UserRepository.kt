package com.kotlin.mywallet.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.data.entities.User
import kotlinx.coroutines.*

class UserRepository( private val userDao: UserDao, private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO){

    //-------------------------------------------------------------//
    //---------------------- USERS --------------------------------//
    //-------------------------------------------------------------//

    suspend fun insertUser(user: User) = withContext(ioDispatcher){
        return@withContext userDao.insertUser(user)
    }

    suspend fun updateUserGrandTotal(username: String, amount: Float) = withContext(ioDispatcher){
        return@withContext userDao.updateUserGrandTotal(username, amount)
    }

    suspend fun populateUsers(users: List<User>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllUsers(users)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User?{
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun getUserByNameAndPassword(username: String, password: String): User?{
        return userDao.getUserByNameAndPassword(username, password)
    }

    fun getUserGrandTotal(username: String): LiveData<Float>{
        return userDao.getUserGrandTotal(username)
    }

    //-------------------------------------------------------------//
    //---------------------- ACCOUNTS -----------------------------//
    //-------------------------------------------------------------//

    suspend fun insertAccount(account: Account) = withContext(ioDispatcher){
        return@withContext userDao.insertAccount(account)
    }

    suspend fun updateAccountTotalAmount(accountName: String, username: String, amount: Float) = withContext(ioDispatcher){
        return@withContext userDao.updateAccountTotalAmount(accountName, username, amount)
    }

    suspend fun updateAccountById(account: Account) = withContext(ioDispatcher){
        return@withContext userDao.updateAccountById(account)
    }

    fun getAccountById(accountId: Int): Account{
        return userDao.getAccountById(accountId)
    }

    fun getAccountsByUser(username: String): LiveData<List<Account>> {
        return userDao.getAccountsByUser(username)
    }

    fun getAnyAccountByUser(username: String): Int{
        return userDao.getAnyAccountByUser(username)
    }

    fun getAccountByNameAndUser(accountName: String, username: String): Account{
        return userDao.getAccountByNameAndUser(accountName, username)
    }

    suspend fun deleteAccount(account: Account) = withContext(ioDispatcher){
        return@withContext userDao.deleteAccount(account)
    }

    suspend fun populateAccounts(accounts: List<Account>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllAccounts(accounts)
    }

    fun getAccountNamesByUser(username: String): List<String>{
        return userDao.getAccountNamesByUser(username)
    }

    //-------------------------------------------------------------//
    //---------------------- CHARGES ------------------------------//
    //-------------------------------------------------------------//

    suspend fun insertCharge(charge: Charge) = withContext(ioDispatcher){
        return@withContext userDao.insertCharge(charge)
    }

    fun getChargesByUserAndAccount(username: String, accountName: String): List<Charge>{
        return userDao.getChargesByUserAndAccount(username, accountName)
    }

    suspend fun populateCharges(charges: List<Charge>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllCharges(charges)
    }

    suspend fun deleteChargesByUserAndAccount(username: String, accountName: String) = withContext(ioDispatcher) {
        return@withContext userDao.deleteChargesByUserAndAccount(username, accountName)
    }

    suspend fun updateChargesAccountName (newAccountName: String, oldAccountName: String, username: String) = withContext(ioDispatcher) {
        return@withContext userDao.updateChargesAccountName(newAccountName, oldAccountName, username)
    }


    private suspend fun prepopulateAccounts(){
        val accounts = listOf(
            Account(accountName = "Principal", initialAmount = 1025.2f, username = "Peter Parker"),
            Account(accountName = "Mi banco 1", initialAmount =  154.52f, username = "Drake"),
            Account(accountName = "Ahorros", initialAmount =  450.48f, username = "David Uppen"),
            Account(accountName = "Efectivo", initialAmount = 250f, username = "Dua Lipa"),
            Account(accountName = "Préstamo", initialAmount =  1500f, username = "David Uppen"),
            Account(accountName = "Cochinito", initialAmount =  458.5f, username = "Dua Lipa"),
            Account(accountName = "Rentas", initialAmount =  15000f, username = "Peter Parker"),
            Account(accountName = "Segunda", initialAmount =  4850f, username = "Peter Parker"),
            Account(accountName = "Inversión", initialAmount =  40000f, username = "Galileo Galilei"),
            Account(accountName = "Banco Nacional", initialAmount =  45000f, username = "Travis Scott"),
            Account(accountName = "Nómina", initialAmount =  15000f, username = "Enrique Peña"),
            Account(accountName = "Tarjeta metro", initialAmount =  75f, username = "Andrés Manuel"),
            Account(accountName = "Ahorros", initialAmount =  100000f, username = "Enrique Peña"),
            Account(accountName = "Ahorro Auto", initialAmount =  105000f, username = "Oswaldo Sanchéz"),
            Account(accountName = "Tarjeta Gas", initialAmount =  750f, username = "Travis Scott"),
            Account(accountName = "Caja", initialAmount =  805f, username = "Peter Parker"),
            Account(accountName = "Vales", initialAmount =  2750f, username = "Drake"),
            Account(accountName = "Dividendos", initialAmount =  2500f, username = "David Uppen"),
            Account(accountName = "Ahorros", initialAmount =  75000f, username = "Drake"),
            Account(accountName = "Tarjeta HeyBanco", initialAmount =  42000f, username = "Andrés Manuel"),
            Account(accountName = "Vacaciones", initialAmount =  6080f, username = "Dua Lipa")
        )

        populateAccounts(accounts)
    }


    private suspend fun prepopulateUsers(){
        val users = listOf(
            User(username= "Peter Parker", email = "Peter_Parker@gmail.com", password = "SpiderverseConfirmado"),
            User(username= "Drake", email = "Drake_Drake@outlook.com", password = "OneDance2016"),
            User(username= "David Uppen", email = "David_Uppen@gmail.com", password = "Dave12345678"),
            User(username= "Dua Lipa", email = "Dua_Lipa@outlook.com", password = "betheoneIDGAF"),
            User(username= "Travis Scott", email = "Travis_Scott@gmail.com", password = "TraviesoScott:v"),
            User(username= "Galileo Galilei", email = "Galileo_Galilei@hotmail.com", password = "GalileoGalileo"),
            User(username= "Enrique Peña", email = "Enrique_pena@gmail.com", password = "5...no,menos...10"),
            User(username= "Andrés Manuel", email = "AMLO2018@outlook.com", password = "AMLO2018:v"),
            User(username= "Oswaldo Sanchéz", email = "OswalditoUwU@gmail.com", password = "JuegaLimpioSienteTuLiga")
        )

        populateUsers(users)

    }

    private suspend fun prepopulateCharges(){

        val charges = listOf(
            Charge(amount =-254f , category = "Transporte" , note = "Transporte a casa.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-45.78f , category = "Comida" , note = "Sandwiches.",date ="", accountName = "Mi banco 1" , username = "Drake" ),
            Charge(amount =-7.5f , category = "Transporte", note = "Combi a trabajo.",date ="", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-350f , category = "Ropa" , note = "Pantalón.",date ="", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =-700f , category = "Salud" , note = "Cita mensual dentista.",date = "", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-558f , category = "Auto", note = "Verificación.",date ="", accountName = "Vales", username = "Drake"),
            Charge(amount =-250f , category = "Restaurante", note = "Comida con amigos.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-100f , category = "Casa", note = "Repareción puerta.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-12f , category = "Transporte", note = "Combis a escuela.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-890f , category = "Ropa", note = "Teniss.",date ="", accountName = "Ahorros", username = "Drake" ),
            Charge(amount =-350f , category = "Comida", note = "Despensa Super.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-560f , category = "Salud", note = "Medicamentos.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-200f , category = "Entretenimiento", note = "Salida al cine.",date ="", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-70f , category = "Casa", note = "Candado nuevo.",date ="", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-40f , category = "Auto", note = "Estacionamiento.",date ="", accountName = "Vales", username = "Drake"),
            Charge(amount =-24f , category = "Transporte", note = "Taxi.",date = "", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-25f , category = "Casa", note = "Cooperación edificio.",date ="", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-230f , category = "Ropa", note = "Playeras.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =-449.50f , category = "Salud", note = "Rayos X",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-187.90f , category = "Restaurante", note = "Restaurante Palmas.",date ="", accountName = "Ahorros", username = "David Uppen"),


            Charge(amount =200f , category = "Depósito", note = "Nada.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =230f , category = "Inversión", note = "Rendimiento quincenal.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =200f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =235f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =137.12f , category = "Inversión", note = "Rendimientos.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =100f , category = "Depósito", note = "David PM.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =17000f , category = "Salario", note = "Quincena.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =24000f , category = "Salario", note = "Quincena.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =2500f , category = "Depósito", note = "René.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =900f , category = "Ventas", note = "",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =103.88f , category = "Inversión", note = "Acumulado.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =100f , category = "Depósito", note = "Karen Denisse M.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =378f , category = "Ahorro", note = "Ahorro semanal.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =22000f , category = "Salario", note = "Mensual.",date = "", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =900f , category = "Depósito", note = "Javier A.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =20000f , category = "Salario", note = "Quincena.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =87.3f , category = "Inversión", note = "Mensual.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =350f , category = "Depósito", note = "A cuenta viaje Qro. YIPM.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =460f , category = "Depósito", note = "Yunnuen S.G.",date = "", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =35000f , category = "Salario", note = "Mensual.",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =27000f , category = "Salario", note = "Octubre.",date = "", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =30f , category = "Depósito", note = "Torta xd",date = "", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =250f, category = "Ventas", note = "",date = "", accountName = "Banco Nacional", username = "Travis Scott")
        )

        populateCharges(charges)

    }

    //-------------------------------------------------------------//
    //---------------- FOR TEST PURPOSE ONLY ----------------------//
    //-------------------------------------------------------------//


//    init{
//       runBlocking {
//            withContext(ioDispatcher) {
//                prepopulateUsers()
//                prepopulateAccounts()
//                prepopulateCharges()
//                setAllUsersGranTotal()
//            }
//       }
//    }

//
//    private suspend fun setAllUsersGranTotal() = withContext(ioDispatcher){
//        val usersList = userDao.getAllUsers()
//
//        return@withContext usersList.forEach {
//            val grandTotal = userDao.getTotalAmountsFromAllAccountsByUser(it.username).sum()
//            updateUserGrandTotal(it.username, grandTotal)
//        }
//    }


//  //-----SET THESE LINES THE FIRST TIME ACCOUNT LIST IS SHOWN----//
//    runBlocking {
//        withContext(ioDispatcher) {
//            setAllUsersGranTotal()
//        }
//    }
//  //-----SET THESE LINES THE FIRST TIME ACCOUNT LIST IS SHOWN----//


    //-------------------------------------------------------------//
    //-------------------- END OF TESTING -------------------------//
    //-------------------------------------------------------------//

}