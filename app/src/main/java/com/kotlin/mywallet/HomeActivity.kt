package com.kotlin.mywallet

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.personal.Usuario

private const val ONE = 1   // PARA AGREGAR CARGO
private const val TWO = 2   // PARA AGREGAR CUENTA

class HomeActivity : AppCompatActivity() {

    companion object {
        const val ACCOUNT_LIST = "ACCOUNT_LIST"
        const val TYPE = "TYPE"
        const val CHARGE = "CHARGE"
        const val ACCOUNT = "ACCOUNT"
        const val NEW_ACCOUNT_NAME = "NEW_ACCOUNT_NAME"
        const val NEW_ACCOUNT_AMOUNT = "NEW_ACCOUNT_AMOUNT"
    }

    private lateinit var welcomeTextView: TextView
    private lateinit var totalAmountTextView: TextView
    private lateinit var addIncomeButton: Button
    private lateinit var addExpenseButton: Button

    //private lateinit var addAccountButton: Button
    //private lateinit var showAccountsButton: Button

    private lateinit var user: Usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_home)

        val appBar = findViewById<Toolbar>(R.id.toolbar_home_appBar)
        this.setSupportActionBar(appBar)

        setupDrawer(appBar)

        val userName = intent.getStringExtra(MainActivity.USER_NAME)
        val email = intent.getStringExtra(MainActivity.USER_EMAIL)

        user = Usuario(userName)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)


        val navView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val userNameNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_userName)
        val emailNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_email)

        userNameNav.text = userName
        emailNav.text = email

        welcomeTextView = findViewById(R.id.textView_home_welcome)
        addIncomeButton = findViewById(R.id.button_home_addIncome)
        addExpenseButton = findViewById(R.id.button_home_addExpense)
        totalAmountTextView = findViewById(R.id.textView_home_totalAmount)

        //addAccountButton = findViewById(R.id.addAcountButton)
        //showAccountsButton = findViewById(R.id.showAccountsButton)

        "Bienvenido \n $userName".also { welcomeTextView.text = it }

        addIncomeButton.setOnClickListener(prepareCharge())
        addExpenseButton.setOnClickListener(prepareCharge())

        //addAccountButton.setOnClickListener(addAccount())
        //showAccountsButton.setOnClickListener(showAccounts())

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_about->{
                    val intent = Intent(this, AboutOfActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_privacy->{
                    val intent = Intent(this, PrivacyActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_accounts -> {
                    showAccounts()
                    true
                }
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_addAccount -> {
                    addAccount()
                    true
                }
                else -> false
            }
        }

    }

    private fun addAccount() {
        val intent = Intent(this, AddAccountActivity::class.java)
        startActivityForResult(intent, TWO)
    }

    private fun showAccounts(){
        val intent = Intent(this, ListActivity::class.java)

        // Se agrega cada cuenta con su nombre al intent
        user.getAccounts().forEach {
            intent.putExtra( it.getName(), it)
        }
        // Se envía lista de strings que corresponden a los nombres de todas las cuentas del usuario
        intent.putExtra(ACCOUNT_LIST ,user.getAccountNames())
        startActivity(intent)
    }

    private fun prepareCharge() = View.OnClickListener { view ->

        val intent = Intent(this, AddChargeActivity::class.java)
        // Qué botón llamó a la función?
        when(view.id){
            R.id.button_home_addIncome -> intent.putExtra(TYPE, +1)
            else -> intent.putExtra(TYPE, -1)
        }
        intent.putExtra(ACCOUNT_LIST ,user.getAccountNames())

        startActivityForResult(intent, ONE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null){
            // Viene de hacer el cargo
            if(requestCode == ONE) {
                doCharge(data)
            }
            // Viene de agregar cuenta
            else if(requestCode == TWO){
                val newAccountName = data.getStringExtra(NEW_ACCOUNT_NAME)?: ""
                val newAccountAmount = data.getStringExtra(NEW_ACCOUNT_AMOUNT)?.toFloat() ?: 0.0f
                user.addAccount(newAccountName, newAccountAmount)
            }
            refreshTotal()
        }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }

    private fun showDialog(title:String,message:String){
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK"){ _, _ -> }.create().show()
    }

    private fun doCharge(intent: Intent?){
        if(intent != null) {
            val type = intent.getIntExtra(TYPE, 0)
            val accountName = intent.getStringExtra(ACCOUNT)
            // INGRESO
            if (type > 0) {
                val charge = intent.getParcelableExtra<Cargo>(CHARGE)
                user.addIncome(accountName, charge)
                showDialog("Ingreso creado.", "${charge?.getAmount()} MXN a cuenta $accountName en categoría ${charge?.getCategory()}.")
            }
            // EGRESO
            else {
                val charge = intent.getParcelableExtra<Cargo>(CHARGE)
                user.addExpense(accountName, charge)
                showDialog("Egreso creado.", "-${charge?.getAmount()} MXN a cuenta $accountName en categoría ${charge?.getCategory()}.")
            }
        }
    }

    private fun refreshTotal(){
        totalAmountTextView.text = user.getGrandTotal().toString()
    }

}