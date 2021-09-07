package com.kotlin.mywallet

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kotlin.mywallet.databinding.ActivityHomeBinding
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.personal.Usuario
import kotlinx.android.synthetic.main.drawer_header.view.*
import java.text.DecimalFormat

private const val ONE = 1   // PARA AGREGAR CARGO
private const val TWO = 2   // PARA AGREGAR CUENTA
private const val THREE = 3 // PARA AGREGAR META

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    companion object {
        const val ACCOUNT_LIST = "ACCOUNT_LIST"
        const val TYPE = "TYPE"
        const val CHARGE = "CHARGE"
        const val ACCOUNT = "ACCOUNT"
        const val NEW_ACCOUNT_NAME = "NEW_ACCOUNT_NAME"
        const val NEW_ACCOUNT_AMOUNT = "NEW_ACCOUNT_AMOUNT"
        const val GOAL = "GOAL"
        const val PREFS_NAME = "com.kotlin.mywallet"
    }
    private lateinit var preferences: SharedPreferences
    private lateinit var welcomeTextView: TextView
    private lateinit var totalAmountTextView: TextView
    private lateinit var addIncomeButton: FloatingActionButton
    private lateinit var addExpenseButton: FloatingActionButton
    private lateinit var btnShowAccount:Button
    lateinit var userName:String
    lateinit var email:String

    private lateinit var user: Usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        val appBar = binding.toolbarHomeAppBar

//        val appBar = findViewById<Toolbar>(R.id.toolbar_home_appBar)
        this.setSupportActionBar(appBar)

        setupDrawer(appBar)

        userName = intent.getStringExtra(MainActivity.USER_NAME)!!
        email = intent.getStringExtra(MainActivity.USER_EMAIL)!!

        user = Usuario(userName)

//        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerLayout = binding.drawerLayout

//        val navView = findViewById<NavigationView>(R.id.nav_view)
        val navView = binding.navView
        val headerView = navView.getHeaderView(0)
        val userNameNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_userName)
        val emailNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_email)
        //agregando las preferencias para guardar los datos de meta
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        userNameNav.text = userName
        emailNav.text = email

        welcomeTextView = binding.textViewHomeWelcome
        addIncomeButton = binding.buttonHomeAddIncome
        addExpenseButton = binding.buttonHomeAddExpense
        totalAmountTextView = binding.textViewHomeTotalAmount
        btnShowAccount = binding.btnShowAccount

        binding.myGoal.text = getCurrentGoal().toString()

        "Hola de nuevo, $userName".also { welcomeTextView.text = it }

        addIncomeButton.setOnClickListener(prepareCharge())
        addExpenseButton.setOnClickListener(prepareCharge())
        btnShowAccount.setOnClickListener{
            showAccounts()
        }
        binding.cardGoal.setOnClickListener {
            addGoal()
        }

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
                    drawerLayout.closeDrawers()
                    showAccounts()
                    true
                }
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_addAccount -> {
                    drawerLayout.closeDrawers()
                    addAccount()
                    true
                }
                R.id.nav_addGoal ->{
                    drawerLayout.closeDrawers()
                    addGoal()
                    true
                }
                else -> false
            }
        }

    }
    private  fun addGoal(){
        val intent = Intent(this, GoalActivity::class.java)
        intent.putExtra(MainActivity.USER_NAME, userName)
        intent.putExtra(MainActivity.USER_EMAIL, email)
        startActivityForResult(intent, THREE)
    }
    private fun addAccount() {
        val intent = Intent(this, AddAccountActivity::class.java)
        // Se envía lista de strings que corresponden a los nombres de todas las cuentas del usuario
        intent.putExtra(ACCOUNT_LIST ,user.getAccountNames())
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

        if(user.getAccountNames().size == 0){
            showDialog("No tan rápido...", "Primero debes \"Agregar una cuenta\"")
        }
        else{
            val intent = Intent(this, AddChargeActivity::class.java)
            // Qué botón llamó a la función?
            when(view.id){
                R.id.button_home_addIncome -> intent.putExtra(TYPE, +1)
                else -> intent.putExtra(TYPE, -1)
        }
        intent.putExtra(ACCOUNT_LIST ,user.getAccountNames())

        startActivityForResult(intent, ONE )
        }
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
            binding.myGoal.text = getCurrentGoal().toString()
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
        val dec = DecimalFormat("#,###.##")
        val total = dec.format(user.getGrandTotal())
        totalAmountTextView.text = "$ $total"
        isGoalReach()
    }

    private  fun getCurrentGoal(): Float{
        return preferences.getFloat(GOAL,0f)
    }

    private fun isGoalReach() {
        if( getCurrentGoal() < user.getGrandTotal())
        {
            binding.cardGoal.setBackgroundColor(Color.parseColor("#4FA64F"))
            binding.cardGoal.setBackgroundColor(resources.getColor(R.color.primaryColor))
            Toast.makeText(applicationContext,"Meta alcanzada",Toast.LENGTH_SHORT).show()
        }
    }

}