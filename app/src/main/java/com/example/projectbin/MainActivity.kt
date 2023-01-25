package com.example.projectbin

import android.annotation.SuppressLint

import android.content.Intent

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
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
            work()
        }
        bindingMain.binField.setOnKeyListener  {v, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    work()
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
    }

    private fun work() {
        if (Support.checkForInternet(this)) {
            val BIN_NUMBER = bindingMain.binField.text

            if (BIN_NUMBER.length < 6 || BIN_NUMBER.length > 8) {
                bindingMain.messageTv.text = "Invalid BIN length"

            } else {
                bindingMain.messageTv.text = ""
                getResult(BIN_NUMBER)
            }

        } else {
            // Написать лучше
            Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show()
            bindingMain.messageTv.text = "There is no internet connection"
        }
    }


    private fun DataActivity(Data: String, Bin: Editable) {
        // создание объекта Intent для запуска SecondActivity
        val intent = Intent(this, BinDataActivity::class.java)
        intent.putExtra("Data", Data)
        intent.putExtra("Bin", Bin.chunked(4).joinToString(separator = " "))
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
                DataActivity(response, number)
            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
                bindingMain.messageTv.text = "No data found"
            }
        )
        queue.add(stringRequest)
    }

}