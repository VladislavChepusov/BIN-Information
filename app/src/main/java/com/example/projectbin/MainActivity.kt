package com.example.projectbin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.projectbin.databinding.ActivityMainBinding
import com.example.projectbin.fragments.Adapter

class MainActivity : AppCompatActivity(), Adapter.Listener {

    private lateinit var bindingMain: ActivityMainBinding
    private var pref: SharedPreferences? = null
    private val adapter = Adapter(this)

    @SuppressLint("MissingInflatedId", "SetTextI18n", "CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        pref = getSharedPreferences("MyTable", Context.MODE_PRIVATE)
        val binList: HashSet<String> = pref?.getStringSet("binList", HashSet()) as HashSet<String>

        init(binList)
        setContentView(bindingMain.root)

        bindingMain.sendBtn.setOnClickListener {
            preparation()
        }
        bindingMain.binField.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    preparation()
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun preparation() {
        if (Support.checkForInternet(this)) {
            val BIN_NUMBER = bindingMain.binField.text

            if (BIN_NUMBER.length < 6 || BIN_NUMBER.length > 8) {
                bindingMain.messageTv.text = "Invalid BIN length"
            } else {
                bindingMain.messageTv.text = ""
                requestProcessing(BIN_NUMBER)
            }
        } else {
            Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show()
            bindingMain.messageTv.text = "There is no internet connection"
        }
    }


    private fun dataActivity(Data: String, Bin: Editable) {
        // создание объекта Intent для запуска SecondActivity
        val intent = Intent(this, BinDataActivity::class.java)
        intent.putExtra("Data", Data)
        intent.putExtra("Bin", Bin.chunked(4).joinToString(separator = " "))
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun requestProcessing(number: Editable) {
        val url = "https://lookup.binlist.net/$number"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                saveData(number)
                dataActivity(response, number)
            },
            { error ->
                Log.d("MyLog", "Volley error: $error")
                bindingMain.messageTv.text = "No data found"
            }
        )
        queue.add(stringRequest)
    }

    @SuppressLint("CommitPrefEdits", "MutatingSharedPrefs")
    private fun saveData(bin: Editable) {
        val editor = pref?.edit()
        val binList: HashSet<String> = pref?.getStringSet("binList", HashSet()) as HashSet<String>
        binList.add(bin.toString())
        editor?.clear();
        editor?.putStringSet("binList", binList)
        editor?.apply()
    }

    private fun init(binlist: HashSet<String>) {
        bindingMain.apply {
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rcView.adapter = adapter
            adapter.dataSynchronization(binlist)
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun trash(view: View) {
        val editor = pref?.edit()
        editor?.clear()
        editor?.apply()
        adapter.dataSynchronization(HashSet())
    }

    override fun onClick(data: String) {
        requestProcessing(Editable.Factory.getInstance().newEditable(data))
    }
}