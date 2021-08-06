package com.kotlin.mywallet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.kotlin.mywallet.personal.Usuario

//const val TRANS_TYPE = "com.kotlin.mywallet"
private const val ONE = 1   // Para agregar cargo
private const val TWO = 2   // Para agregar cuenta

class HomeActivity : AppCompatActivity() {

    private lateinit var welcomeHomeTxt: TextView
    private lateinit var totalAmountTxt: TextView
    private lateinit var addIncomeButton: Button
    private lateinit var addExpenseButton: Button

    private lateinit var addAccountButton: Button
    private lateinit var showAccountsButton: Button

    private lateinit var user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val appBar = findViewById<Toolbar>(R.id.app_bar)
        this.setSupportActionBar(appBar)

        setupDrawer(appBar)

        val bundle = intent.extras
        val name = bundle?.getString(USER_NAME)

        user = Usuario(name!!)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navView.getHeaderView(0)
        val navUserName = headerView.findViewById<TextView>(R.id.userNameText)

        navUserName.text = name

        welcomeHomeTxt = findViewById(R.id.welcomeHomeText)
        addIncomeButton = findViewById(R.id.addIncomeButton)
        addExpenseButton = findViewById(R.id.addExpenseButton)
        totalAmountTxt = findViewById(R.id.totalAmountText)

        addAccountButton = findViewById(R.id.addAcountButton)
        showAccountsButton = findViewById(R.id.showAccountsButton)

        welcomeHomeTxt.text = "Bienvenido $name"

        addIncomeButton.setOnClickListener(prepareCharge())
        addExpenseButton.setOnClickListener(prepareCharge())

        addAccountButton.setOnClickListener(addAccount())

    }

    private fun addAccount() = View.OnClickListener {
        val intent = Intent(this, AddAccountActivity::class.java)
        startActivityForResult(intent, TWO)
    }

    private fun prepareCharge() = View.OnClickListener { view ->

        //val bundle = Bundle()
        val intent = Intent(this, AddChargeActivity::class.java)

        when(view.id){
            R.id.addIncomeButton -> intent.putExtra("type", "ingreso")//bundle.putString(TRANS_TYPE, "ingreso")
            else -> intent.putExtra("type", "egreso")//bundle.putString(TRANS_TYPE, "egreso")
        }

        intent.putExtra("accountsList" ,user.getAccountNames())

        //intent.putExtras(bundle)
        //startActivity(intent)

        startActivityForResult(intent, ONE )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            // Viene de hacer el cargo
            if(requestCode == ONE) {

                //val cargo = intent.getParcelableExtra<Cargo>("cargo")
                val cuentaNombre = intent.getStringExtra("cuenta")

                //addCharge(cuentaNombre, cargo)
            }
            // Viene de agregar cuenta
            else if(requestCode == TWO){
                val n = data?.getStringExtra("name") ?: ""
                val a = data?.getStringExtra("amount")?.toFloat() ?: 0.0f
                user.addAccount(n, a)
            }
        }
    }

    private fun setupDrawer(toolbar: Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)
    }




}