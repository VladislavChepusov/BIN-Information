package com.example.projectbin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.projectbin.data.APIModel
import com.example.projectbin.databinding.ActivityBinDataBinding
import com.example.projectbin.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import org.json.JSONObject


class BinDataActivity : AppCompatActivity() {

    private lateinit var bindingBinData: ActivityBinDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingBinData = ActivityBinDataBinding.inflate(layoutInflater)
        val data = intent.getStringExtra("Data")
        val ParserData: APIModel

        if (data != null) {
            ParserData = parserBinData(data)
            bindingBinData.textView4.text = ParserData.scheme
            Log.d("MyLog", "Ебуться мишки!!: $ParserData")
        }

        setContentView(bindingBinData.root)
    }


    private fun parserBinData(Data: String): APIModel {
        //val gson = GsonBuilder().create()
        //val resultData: APIModel = gson.fromJson(Data, APIModel::class.java)
        //Log.d("MyLog", "ПАРСИНГ: $resultData")
        return GsonBuilder().create().fromJson(Data, APIModel::class.java)
    }


}