package com.kotlin.mywallet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
import kotlinx.android.synthetic.main.drawer_header.*
import kotlinx.android.synthetic.main.drawer_header.view.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat

private const val ONE = 1   // PARA AGREGAR CARGO
private const val TWO = 2   // PARA AGREGAR CUENTA
private const val THREE = 3 // PARA AGREGAR META
private const val REQUEST_CAMERA = 4 // PARA ABRIR CAMARA

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

    var picture: Uri? = null
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
        val cambiarImagen=headerView.findViewById<Button>(R.id.cambiarImagen)
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
        cambiarImagen.setOnClickListener { changePicture()}
    }

    private fun changePicture(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //GET PERMISSIONS//
                val permissionCamera= arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissionCamera, REQUEST_CAMERA)
            }
            else opencamera()
        }
        else{
            opencamera()
        }
    }

    private fun opencamera(){
        //recuperar los bits de una foto--espacio de memoria vacio ContentValues
        val value= ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "${System.currentTimeMillis()}.jpg")
        picture = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        val camaraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picture)
        startActivityForResult(camaraIntent, REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    opencamera()
                else
                    Toast.makeText(applicationContext, "No puedes acceder a la cámara", Toast.LENGTH_SHORT).show()
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
            else if(requestCode == REQUEST_CAMERA){
                foto_perfil.setImageURI(picture)
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
        totalAmountTextView.text = "$ $total MXN"
        llamadaAppi()
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

    fun llamadaAppi(){
        val okHttpClient = OkHttpClient()
        val url = "https://api.frankfurter.app/latest?from=MXN&to=USD"

        val request = Request.Builder()
            .url(url)
            .build()
        okHttpClient.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("Response", e.toString())
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                Log.d( "Response", body!!)

                try {
                    val json = JSONObject(body)
                    val dec = DecimalFormat("#,###.##")

                    val rate = json.getJSONObject("rates").getString("USD")
                    val totalUsd = dec.format(rate.toDouble()*user.getGrandTotal())

                    binding.textViewHomeTotalAmountDollars.text= "$ $totalUsd USD"


                } catch (e: JSONException){

                }

            }
        } )
    }

}