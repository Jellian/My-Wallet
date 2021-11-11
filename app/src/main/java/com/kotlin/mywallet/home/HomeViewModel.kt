package com.kotlin.mywallet.home

//import com.kotlin.mywallet.utils.apiCall
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.mywallet.data.UserRepository
import com.kotlin.mywallet.login.MainActivity
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat

class HomeViewModel (private val userRepository: UserRepository, context: Context): ViewModel(){

    private var preferences = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE)
    private val username = preferences.getString(MainActivity.USER_NAME, "").toString()
    private val userEmail = preferences.getString(MainActivity.USER_EMAIL, "").toString()

    var grandTotal = userRepository.getUserGrandTotal(username)
        set(value) {
            field = value
            //updateGrandTotalUSD()
        }
    var actualGoal = userRepository.getActualGoalByUser(username)
    var grandTotalUSD = MutableLiveData<Float>()

    init {
        //updateGrandTotalUSD()
    }

    fun getAccountNamesByUser(username: String): List<String> {
        return userRepository.getAccountNamesByUser(username)
    }

    fun editStringPref( key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getUserName(): String{
        return username
    }

    fun getUserEmail(): String{
        return userEmail
    }

    fun getUriRefByUser(username: String): Uri?{
        return userRepository.getUriRefByUser(username)?.toUri()
    }

    fun updateUriRefByUser(username: String, uriRef: Uri?){
        viewModelScope.launch {
            userRepository.updateUriRefByUser(username, uriRef.toString())
        }
    }

    fun updateGrandTotalUSD(rate: Float){

        //Log.d("APIIIIIII", apiCall().toString())
        grandTotalUSD.value = grandTotal.value?.times(rate)?: 0.0f
        //0.048f
    }

    fun apiCall(): LiveData<Float> {

        val okHttpClient = OkHttpClient()
        val url = "https://api.frankfurter.app/latest?from=MXN&to=USD"
        var rate = MutableLiveData<Float>()

        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //Log.e("Response", e.toString())
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                //Log.d( "Response", body!!)

                try {
                    val json = JSONObject(body)
                    val dec = DecimalFormat("#,###.##")
                    val rateString = json.getJSONObject("rates").getString("USD")
                    rate.postValue(rateString.toFloat())
                    //totalUSD = dec.format(rate.toDouble() * grandTotal)
                    // binding.textViewHomeTotalAmountDollars.text= "$ $totalUsd USD"
                } catch (e: JSONException) {
                }

                //Log.d("RATEEEEEEEE", rate)
                //grandTotalUSD.value = grandTotal.value?.times(rate.toFloat())?: 0.0f

            }
        })
        return rate
    }

}
