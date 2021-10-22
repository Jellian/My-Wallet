package com.kotlin.mywallet

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.navigation.fragment.findNavController
import com.kotlin.mywallet.data.UserDatabase
import com.kotlin.mywallet.databinding.FragmentHomeBinding
import com.kotlin.mywallet.login.MainActivity
import com.kotlin.mywallet.personal.Usuario
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val REQUEST_CAMERA = 1 // PARA ABRIR CAMARA

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var preferences: SharedPreferences

    private var pictureUriReference: Uri? =null

    private lateinit var email: String
    private lateinit var username: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDrawer()

        val parentActivity = activity as HomeActivity?

        // Agregando las preferencias para guardar los datos de meta
        preferences = activity?.getSharedPreferences(HomeActivity.PREFS_NAME, Context.MODE_PRIVATE) as SharedPreferences

        username = parentActivity?.intent?.getStringExtra(HomeActivity.USER_NAME).toString()
        email = parentActivity?.intent?.getStringExtra(MainActivity.USER_EMAIL).toString()


        val headerView = binding.navView.getHeaderView(0)
        val userNameNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_userName)
        val emailNav = headerView.findViewById<TextView>(R.id.textView_drawerMenu_email)
        val changePictureNav = headerView.findViewById<ImageView>(R.id.imageView_drawerMenu_camera)

        userNameNav.text = username
        emailNav.text = email

        changePictureNav.setOnClickListener{ changePicture() }

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_about->{
                    findNavController().navigate(R.id.aboutOfFragment, null, MainActivity.options)
                    true
                }
                R.id.nav_privacy->{
                    findNavController().navigate(R.id.privacyFragment, null, MainActivity.options)
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
                    findNavController().navigate(R.id.goalFragment, null, MainActivity.options)
                    true
                }
                R.id.nav_signOut ->{
                    showDialog("Cerrando sesión...", "¿Estás seguro que deseas salir?", HomeActivity.EXIT)
                    true
                }
                else -> false
            }
        }

        binding.myGoal.text = preferences.getFloat(HomeActivity.GOAL,0f).toString()

        "¡Hola, $username!".also { binding.textViewHomeWelcome.text = it }

        binding.buttonHomeAddIncome.setOnClickListener( prepareCharge() )
        binding.buttonHomeAddExpense.setOnClickListener( prepareCharge() )
        binding.buttonShowAccount.setOnClickListener{ showAccounts() }
        binding.cardGoal.setOnClickListener { findNavController().navigate(R.id.goalFragment, null, MainActivity.options) }
    }

    private fun setupDrawer(){
        val appBar = binding.toolbarHomeFragmentAppBar
        (activity as AppCompatActivity).setSupportActionBar(appBar)
        ActionBarDrawerToggle(activity, binding.drawerLayout, appBar, R.string.open_drawer, R.string.close_drawer)
    }

    private fun prepareCharge() = View.OnClickListener { view ->

        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        executor.execute(
            Runnable {
                val accountList = UserDatabase.getInstance(requireContext())
                    ?.userDao
                    ?.findAccountNamesByUser(username.toString())

                Handler(Looper.getMainLooper()).post(Runnable {

                    if(accountList.isNullOrEmpty())
                        showDialog("No tan rápido...", "Primero debes \"Agregar una cuenta\"")
                    else {
                        val intent = Intent(context, AddChargeActivity::class.java)
                        // Boton que ha llamado a la funcion
                        when (view.id) {
                            R.id.button_home_addIncome -> intent.putExtra(HomeActivity.TYPE, +1)
                            else ->
                                intent.putExtra(HomeActivity.TYPE, -1)
                        }
                        intent.putExtra(HomeActivity.USER_NAME, username)
                        startActivity(intent)
                    }
                })
            })
    }

    private fun showAccounts() {
        val intent = Intent(context, ListActivity::class.java)
        intent.putExtra(HomeActivity.USER_NAME, username)
        startActivity(intent)
    }

    private fun showDialog(title:String, message:String, type: String = HomeActivity.ALERT){
        if (type == HomeActivity.ALERT) AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("OK") { _, _ -> }.create().show()
        else if (type == HomeActivity.EXIT)
            AlertDialog.Builder(context).setTitle(title).setMessage(message).setPositiveButton("Sí") { _, _ ->
                preferences.edit().putString(HomeActivity.IS_LOGGED, "FALSE").apply()
                activity?.finish()
            }.setNegativeButton("No", null).create().show()
    }

    private fun changePicture(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(activity?.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                || activity?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
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

        pictureUriReference = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)

        val camaraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUriReference)
        startActivityForResult(camaraIntent, REQUEST_CAMERA)
    }

    private fun addAccount() {
        val intent = Intent(context, AddAccountActivity::class.java)
        intent.putExtra(HomeActivity.USER_NAME, username)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openCamera()
                else Toast.makeText( context, "No puedes acceder a la cámara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && data != null){
            when (requestCode) {
                REQUEST_CAMERA -> {
                    // Obtenemos bitmap desde URI REFERENCE
                    val bitmap = if(Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap( requireActivity().contentResolver, pictureUriReference)
                    } else{
                        val source = pictureUriReference?.let { ImageDecoder.createSource(requireActivity().contentResolver, it) }
                        source?.let { ImageDecoder.decodeBitmap(it) }
                    }
                    // Create the RoundedBitmapDrawable.
                    val roundDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                    roundDrawable.isCircular = true
                    imageView_drawerMenu_profilePicture.setImageDrawable(roundDrawable)
                }
            }
            //refreshTotal()
        }
    }

//    private fun refreshTotal(){
//        val dec = DecimalFormat("#,###.##")
//        val total = dec.format(user.getGrandTotal())
//        binding.textViewHomeTotalAmount.text = "$ $total MXN"
//        apiCall()
//        isGoalReach()
//    }

//    private fun apiCall(){
//        val okHttpClient = OkHttpClient()
//        val url = "https://api.frankfurter.app/latest?from=MXN&to=USD"
//
//        val request = Request.Builder().url(url).build()
//        okHttpClient.newCall(request).enqueue(object: Callback {
//
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                //Log.e("Response", e.toString())
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: Response) {
//                val body = response.body?.string()
//                //Log.d( "Response", body!!)
//
//                try {
//                    val json = JSONObject(body)
//                    val dec = DecimalFormat("#,###.##")
//                    val rate = json.getJSONObject("rates").getString("USD")
//                    val totalUsd = dec.format(rate.toDouble()*user.getGrandTotal())
//
//                    binding.textViewHomeTotalAmountDollars.text= "$ $totalUsd USD"
//
//                } catch (e: JSONException){ }
//            }
//        } )
//    }

}