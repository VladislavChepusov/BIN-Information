package com.example.projectbin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectbin.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // private var textview: TextView? = null
    private lateinit var bindingMain: ActivityMainBinding

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        bindingMain.sendBtn.setOnClickListener {
            if (checkForInternet(this)) {
                val BIN_NUMBER = bindingMain.binField.text

                if (BIN_NUMBER.length < 6 || BIN_NUMBER.length > 8) {
                    bindingMain.messageTv.text = "Invalid BIN length"

                } else {
                    bindingMain.messageTv.text = ""
                    getResult(BIN_NUMBER)
                }

            } else {
                // Написать лучше
                Toast.makeText(this, "Отсутсвие интернета", Toast.LENGTH_SHORT).show()
                bindingMain.messageTv.text = "There is no internet connection"
            }

        }
    }

    private fun DataActivity(Data: String) {
        // создание объекта Intent для запуска SecondActivity
        val intent = Intent(this, BinDataActivity::class.java)
        intent.putExtra("Data", Data)
        startActivity(intent)
    }


    @SuppressLint("SetTextI18n")
    private fun getResult(number: Editable) {
        val url = "https://lookup.binlist.net/$number"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                //bindingMain.messageTv.text = "Данных есть "
                Log.d("MyLog", "Ответ: $response")
                DataActivity(response)
            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
                bindingMain.messageTv.text = "No data found"
            }
        )
        queue.add(stringRequest)
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Для разных версий андроида
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Возвращает сетевой объект, соответствующий
            // текущей активной сети передачи данных по умолчанию.
            val network = connectivityManager.activeNetwork ?: return false
            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}