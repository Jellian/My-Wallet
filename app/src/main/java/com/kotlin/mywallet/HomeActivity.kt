package com.kotlin.mywallet

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.databinding.ActivityHomeBinding
import com.kotlin.mywallet.finance.Cargo
import com.kotlin.mywallet.login.MainActivity
import com.kotlin.mywallet.personal.Usuario
import kotlinx.android.synthetic.main.drawer_header.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val ADD_CHARGE = 1   // PARA AGREGAR CUENTA
private const val ADD_GOAL = 3 // PARA AGREGAR META
private const val REQUEST_CAMERA = 4 // PARA ABRIR CAMARA

class HomeActivity : AppCompatActivity() {

    companion object {
        const val ACCOUNT_LIST = "ACCOUNT_LIST"
        const val TYPE = "TYPE"
        const val CHARGE = "CHARGE"
        const val ACCOUNT = "ACCOUNT"

        const val PREFS_NAME = "com.kotlin.mywallet"
        const val GOAL = "GOAL"
        const val IS_LOGGED = "IS_LOGGED"
        const val USER_NAME = "USER_NAME"
        const val USER_EMAIL = "USER_EMAIL"

        const val ALERT = "ALERT"
        const val EXIT = "EXIT"
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var preferences: SharedPreferences

    private lateinit var userName: String
    private lateinit var email: String
    private lateinit var user: Usuario
    private var pictureUriReference: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        setupDrawer()

        // Agregando las preferencias para guardar los datos de meta
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        userName = intent.getStringExtra(MainActivity.USER_NAME).toString()
        email = intent.getStringExtra(MainActivity.USER_EMAIL).toString()

        //user = Usuario(userName)

        val headerView = binding.navView.getHeaderView(0)
        val userNameNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_userName)
        val emailNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_email)
        val changePictureNav = headerView.findViewById<ImageView>(R.id.imageView_drawerMenu_camera)

        userNameNav.text = userName
        emailNav.text = email

        binding.myGoal.text = getCurrentGoal().toString()

        "Hola de nuevo, $userName".also { binding.textViewHomeWelcome.text = it }

        changePictureNav.setOnClickListener{ changePicture() }

        binding.buttonHomeAddIncome.setOnClickListener( prepareCharge() )
        binding.buttonHomeAddExpense.setOnClickListener( prepareCharge() )
        binding.buttonShowAccount.setOnClickListener{ showAccounts() }
        binding.cardGoal.setOnClickListener { addGoal() }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.drawerLayout.closeDrawers()
                    true
                }
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
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_addAccount -> {
                    addAccount()
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_addGoal ->{
                    addGoal()
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_signOut ->{
                    showDialog("Cerrando sesión...", "¿Estás seguro que deseas salir?", EXIT)
                    true
                }
                else -> false
            }
        }
    }

    private fun changePicture(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ||checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //GET PERMISSIONS//
                val permissionCamera= arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissionCamera, REQUEST_CAMERA)
            }
            else openCamera()
        }
        else openCamera()
    }

    private fun openCamera(){
        // Recuperar los bits de una foto--espacio de memoria vacio ContentValues
        val value= ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "${System.currentTimeMillis()}.jpg")

        pictureUriReference = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)

        val camaraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUriReference)
        startActivityForResult(camaraIntent, REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openCamera()
                else Toast.makeText(applicationContext, "No puedes acceder a la cámara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private  fun addGoal() {
        val intent = Intent(this, GoalActivity::class.java)
        startActivityForResult(intent, ADD_GOAL)
    }

    private fun addAccount() {
        val intent = Intent(this, AddAccountActivity::class.java)
        // Se envía lista de strings que corresponden a los nombres de todas las cuentas del usuario
        //intent.putExtra( ACCOUNT_LIST, user.getAccountNames() )
        //startActivityForResult(intent, ADD_ACCOUNT)
        intent.putExtra(USER_NAME, userName)
        startActivity(intent)
    }

    private fun showAccounts() {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra(USER_NAME, userName)
        startActivity(intent)
    }

    private fun prepareCharge() = View.OnClickListener { view ->

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {
                val accountList = UserDatabase.getInstance(this)
                    ?.userDao
                    ?.findAccountNamesByUser(userName)

                Handler(Looper.getMainLooper()).post(Runnable {

                    if(accountList.isNullOrEmpty())
                        showDialog("No tan rápido...", "Primero debes \"Agregar una cuenta\"")
                    else {
                        val intent = Intent(this, AddChargeActivity::class.java)
                        // Boton que ha llamado a la funcion
                        when (view.id) {
                            R.id.button_home_addIncome -> intent.putExtra(TYPE, +1)
                            else ->
                                intent.putExtra(TYPE, -1)
                        }
                        intent.putExtra(USER_NAME, userName)
                        startActivity(intent)
                    }
                })
            })
    }

    override fun onResume() {
        super.onResume()
        //refreshTotal()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null){
            when (requestCode) {

                REQUEST_CAMERA -> {
                    // Obtenemos bitmap desde URI REFERENCE
                    val bitmap = if(Build.VERSION.SDK_INT < 28) {
                       MediaStore.Images.Media.getBitmap( this.contentResolver, pictureUriReference)
                    } else{
                        val source = pictureUriReference?.let { ImageDecoder.createSource(this.contentResolver, it) }
                        source?.let { ImageDecoder.decodeBitmap(it) }
                    }
                    // Create the RoundedBitmapDrawable.
                    val roundDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                    roundDrawable.isCircular = true
                    imageView_drawerMenu_profilePicture.setImageDrawable(roundDrawable)
                }
                ADD_GOAL -> { binding.myGoal.text = getCurrentGoal().toString() }
            }
            //refreshTotal()
        }
    }

    private fun setupDrawer(){
        val appBar = binding.toolbarHomeAppBar
        this.setSupportActionBar(appBar)
        ActionBarDrawerToggle(this,binding.drawerLayout, appBar,R.string.open_drawer,R.string.close_drawer)
    }

    private fun showDialog(title:String, message:String, type: String = ALERT){
        if (type == ALERT)
            AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("OK") { _, _ -> }.create().show()
        else if (type == EXIT)
            AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton("Sí") { _, _ ->
                    preferences.edit().putString(HomeActivity.IS_LOGGED, "FALSE").apply()
                    finish()
                }
                .setNegativeButton("No", null).create().show()
    }

    private fun refreshTotal(){
        val dec = DecimalFormat("#,###.##")
        val total = dec.format(user.getGrandTotal())
        binding.textViewHomeTotalAmount.text = "$ $total MXN"
        apiCall()
        isGoalReach()
    }

    private  fun getCurrentGoal(): Float{
        return preferences.getFloat(GOAL,0f)
    }

    private fun isGoalReach() {
        if( getCurrentGoal() < user.getGrandTotal()) {
            binding.cardGoal.setBackgroundColor(Color.parseColor("#4FA64F"))
            binding.cardGoal.setBackgroundColor(resources.getColor(R.color.primaryColor))
            Toast.makeText(applicationContext,"Meta alcanzada",Toast.LENGTH_SHORT).show()
        }
    }

    private fun apiCall(){
        val okHttpClient = OkHttpClient()
        val url = "https://api.frankfurter.app/latest?from=MXN&to=USD"

        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //Log.e("Response", e.toString())
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                //Log.d( "Response", body!!)

                try {
                    val json = JSONObject(body)
                    val dec = DecimalFormat("#,###.##")
                    val rate = json.getJSONObject("rates").getString("USD")
                    val totalUsd = dec.format(rate.toDouble()*user.getGrandTotal())

                    binding.textViewHomeTotalAmountDollars.text= "$ $totalUsd USD"

                } catch (e: JSONException){ }
            }
        } )
    }
}