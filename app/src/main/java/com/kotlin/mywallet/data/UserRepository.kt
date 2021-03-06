package com.kotlin.mywallet.data

import android.net.Uri
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

    suspend fun updateUriRefByUser(username: String, uriRefAsString: String?) = withContext(ioDispatcher){
        return@withContext userDao.updateUriRefByUser(username, uriRefAsString)
    }

    suspend fun updateUserGrandTotal(username: String, amount: Float) = withContext(ioDispatcher){
        return@withContext userDao.updateUserGrandTotal(username, amount)
    }

    suspend fun updateActualGoalByUser(username: String, newGoal: Float ) = withContext(ioDispatcher){
        return@withContext userDao.updateActualGoalByUser(username, newGoal)
    }

    suspend fun populateUsers(users: List<User>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllUsers(users)
    }

    fun getUriRefByUser(username: String):String?{
        return userDao.getUriRefByUser(username)
    }

    fun getUserByEmailAndPassword(email: String, password: String): User?{
        return userDao.getUserByEmailAndPassword(email, password)
    }

    fun getUserByNameAndPassword(username: String, password: String): User?{
        return userDao.getUserByNameAndPassword(username, password)
    }

    fun getUserGrandTotal(username: String): LiveData<Float> {
        return userDao.getUserGrandTotal(username)
    }

    fun getActualGoalByUser(username: String): LiveData<Float>{
        return userDao.getActualGoalByUser(username)
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

    fun getAccountsCountByUser(username: String): Int{
        return userDao.getAccountsCountByUser(username)
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

    fun getAccountTotalAmount(accountName: String, username: String): LiveData<Float>{
        return userDao.getAccountTotalAmount(accountName, username)
    }

    //-------------------------------------------------------------//
    //---------------------- CHARGES ------------------------------//
    //-------------------------------------------------------------//

    suspend fun insertCharge(charge: Charge) = withContext(ioDispatcher){
        return@withContext userDao.insertCharge(charge)
    }

    fun getChargesByUserAndAccount(username: String, accountName: String): LiveData<List<Charge>>{
        return userDao.getChargesByUserAndAccount(username, accountName)
    }

    suspend fun updateChargeById(charge: Charge) = withContext(ioDispatcher){
        return@withContext userDao.updateChargeById(charge)
    }

    fun getChargeById(chargeId: Int): Charge{
        return userDao.getChargeById(chargeId)
    }

    suspend fun populateCharges(charges: List<Charge>) = withContext(ioDispatcher){
        return@withContext userDao.insertAllCharges(charges)
    }

    suspend fun deleteCharge(charge: Charge) = withContext(ioDispatcher){
        return@withContext userDao.deleteCharge(charge)
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
            Account(accountName = "Pr??stamo", initialAmount =  1500f, username = "David Uppen"),
            Account(accountName = "Cochinito", initialAmount =  458.5f, username = "Dua Lipa"),
            Account(accountName = "Rentas", initialAmount =  15000f, username = "Peter Parker"),
            Account(accountName = "Segunda", initialAmount =  4850f, username = "Peter Parker"),
            Account(accountName = "Inversi??n", initialAmount =  40000f, username = "Galileo Galilei"),
            Account(accountName = "Banco Nacional", initialAmount =  45000f, username = "Travis Scott"),
            Account(accountName = "N??mina", initialAmount =  15000f, username = "Enrique Pe??a"),
            Account(accountName = "Tarjeta metro", initialAmount =  75f, username = "Andr??s Manuel"),
            Account(accountName = "Ahorros", initialAmount =  100000f, username = "Enrique Pe??a"),
            Account(accountName = "Ahorro Auto", initialAmount =  105000f, username = "Oswaldo Sanch??z"),
            Account(accountName = "Tarjeta Gas", initialAmount =  750f, username = "Travis Scott"),
            Account(accountName = "Caja", initialAmount =  805f, username = "Peter Parker"),
            Account(accountName = "Vales", initialAmount =  2750f, username = "Drake"),
            Account(accountName = "Dividendos", initialAmount =  2500f, username = "David Uppen"),
            Account(accountName = "Ahorros", initialAmount =  75000f, username = "Drake"),
            Account(accountName = "Tarjeta HeyBanco", initialAmount =  42000f, username = "Andr??s Manuel"),
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
            User(username= "Enrique Pe??a", email = "Enrique_pena@gmail.com", password = "5...no,menos...10"),
            User(username= "Andr??s Manuel", email = "AMLO2018@outlook.com", password = "AMLO2018:v"),
            User(username= "Oswaldo Sanch??z", email = "OswalditoUwU@gmail.com", password = "JuegaLimpioSienteTuLiga")
        )

        populateUsers(users)

    }

    private suspend fun prepopulateCharges(){

        val charges = listOf(
            Charge(amount =-254f , category = "Transporte" , note = "Transporte a casa.",date ="2021/ 10/ 05", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-45.78f , category = "Comida" , note = "Sandwiches.",date ="2021/ 09/ 27", accountName = "Mi banco 1" , username = "Drake" ),
            Charge(amount =-7.5f , category = "Transporte", note = "Combi a trabajo.",date ="2020/ 07/ 01", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-350f , category = "Ropa" , note = "Pantal??n.",date ="2021/ 03/ 14", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =-700f , category = "Salud" , note = "Cita mensual dentista.",date = "2021/ 03/ 16", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-558f , category = "Auto", note = "Verificaci??n.",date ="2021/ 02/ 14", accountName = "Vales", username = "Drake"),
            Charge(amount =-250f , category = "Restaurante", note = "Comida con amigos.",date ="2020/ 12/ 12", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-100f , category = "Casa", note = "Repareci??n puerta.",date ="2020/ 11/ 02", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-12f , category = "Transporte", note = "Combis a escuela.",date ="2020/ 11/ 19", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-890f , category = "Ropa", note = "Teniss.",date ="2021/ 05/ 15", accountName = "Ahorros", username = "Drake" ),
            Charge(amount =-350f , category = "Comida", note = "Despensa Super.",date ="2021/ 05/ 10", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-560f , category = "Salud", note = "Medicamentos.",date ="2021/ 04/ 13", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-200f , category = "Entretenimiento", note = "Salida al cine.",date ="2021/ 07/ 29", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-70f , category = "Casa", note = "Candado nuevo.",date ="2021/ 07/ 14", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =-40f , category = "Auto", note = "Estacionamiento.",date ="2020/ 01/ 01", accountName = "Vales", username = "Drake"),
            Charge(amount =-24f , category = "Transporte", note = "Taxi.",date = "2021/ 08/ 17", accountName = "Segunda", username = "Peter Parker"),
            Charge(amount =-25f , category = "Casa", note = "Cooperaci??n edificio.",date ="2021/ 07/ 15", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-230f , category = "Ropa", note = "Playeras.",date = "2021/ 08/ 01", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =-449.50f , category = "Salud", note = "Rayos X",date = "2021/ 10/ 10", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =-187.90f , category = "Restaurante", note = "Restaurante Palmas.",date ="2021/ 10/ 11", accountName = "Ahorros", username = "David Uppen"),


            Charge(amount =200f , category = "Dep??sito", note = "Nada.",date = "2021/ 05/ 06", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =230f , category = "Inversi??n", note = "Rendimiento quincenal.",date = "2021/ 05/ 10", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =200f , category = "Ahorro", note = "Ahorro semanal.",date = "2021/ 09/ 15", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =235f , category = "Ahorro", note = "Ahorro semanal.",date = "2021/ 09/ 16", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =137.12f , category = "Inversi??n", note = "Rendimientos.",date = "2020/ 11/ 20", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =100f , category = "Dep??sito", note = "David PM.",date = "2021/ 04/ 30", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =17000f , category = "Salario", note = "Quincena.",date = "2021/ 03/ 31", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =24000f , category = "Salario", note = "Quincena.",date = "2021/ 02/ 10", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =2500f , category = "Dep??sito", note = "Ren??.",date = "2020/ 02/ 28", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =900f , category = "Ventas", note = "",date = "2019/ 01/ 15", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =103.88f , category = "Inversi??n", note = "Acumulado.",date = "2020/ 01/ 17", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =100f , category = "Dep??sito", note = "Karen Denisse M.",date = "2021/ 08/ 27", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =378f , category = "Ahorro", note = "Ahorro semanal.",date = "2017/ 01/ 15", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =22000f , category = "Salario", note = "Mensual.",date = "2018/ 02/ 13", accountName = "Banco Nacional", username = "Travis Scott"),
            Charge(amount =900f , category = "Dep??sito", note = "Javier A.",date = "2019/ 03/ 04", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =20000f , category = "Salario", note = "Quincena.",date = "2019/ 04/ 16", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =87.3f , category = "Inversi??n", note = "Mensual.",date = "2015/ 05/ 20", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =350f , category = "Dep??sito", note = "A cuenta viaje Qro. YIPM.",date = "2018/ 06/ 21", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =460f , category = "Dep??sito", note = "Yunnuen S.G.",date = "2020/ 07/ 17", accountName = "Ahorros", username = "David Uppen"),
            Charge(amount =35000f , category = "Salario", note = "Mensual.",date = "2021/ 08/ 11", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =27000f , category = "Salario", note = "Octubre.",date = "2019/ 09/ 13", accountName = "Mi banco 1", username = "Drake"),
            Charge(amount =30f , category = "Dep??sito", note = "Torta xd",date = "2018/ 10/ 31", accountName = "Principal", username = "Peter Parker"),
            Charge(amount =250f, category = "Ventas", note = "",date = "2018/ 11/ 04", accountName = "Banco Nacional", username = "Travis Scott")
        )

        populateCharges(charges)

    }

    //-------------------------------------------------------------//
    //---------------- FOR TEST PURPOSE ONLY ----------------------//
    //-------------------------------------------------------------//
//
//
//    init {
//        runBlocking {
//            withContext(ioDispatcher) {
//                prepopulateUsers()
//                prepopulateAccounts()
//                prepopulateCharges()
//                setAllUsersGranTotal()
//            }
//        }
//    }
//
//
//    private suspend fun setAllUsersGranTotal() = withContext(ioDispatcher) {
//        val usersList = userDao.getAllUsers()
//
//        return@withContext usersList.forEach {
//            val grandTotal = userDao.getTotalAmountsFromAllAccountsByUser(it.username).sum()
//            updateUserGrandTotal(it.username, grandTotal)
//        }
//    }

//  //-----------IGNORE THIS---------------------------------------//
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