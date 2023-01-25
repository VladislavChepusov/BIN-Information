package com.example.projectbin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.projectbin.data.APIModel
import com.example.projectbin.data.NullText
import com.example.projectbin.databinding.ActivityBinDataBinding
import com.google.gson.GsonBuilder


class BinDataActivity : AppCompatActivity() {

    private lateinit var bindingBinData: ActivityBinDataBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingBinData = ActivityBinDataBinding.inflate(layoutInflater)
        val data = intent.getStringExtra("Data")
        val bin = intent.getStringExtra("Bin")
        bindingBinData.NumTv.text = "BIN: $bin"

        val parserData: APIModel
        if (data != null) {
            parserData = parserBinData(data)
            completion(parserData)
        }
        setContentView(bindingBinData.root)
        bindingBinData.backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun completion(Data: APIModel) {

        bindingBinData.shemeTV.text = Data.scheme
        bindingBinData.brandTV.text = if (Data.brand != null) Data.brand else NullText
        bindingBinData.countryTv.text = Data.country?.emoji + " " + Data.country?.name
        bindingBinData.coordinatesTv.text =
            "(latitude: ${Data.country?.latitude}, longitude: ${Data.country?.longitude})"
        bindingBinData.typeTv.text = Data.type
        bindingBinData.lengthTv.text = Data.number?.length
        bindingBinData.bankNameTv.text = Data.bank?.name
        bindingBinData.bankUrlTv.text = Data.bank?.url
        bindingBinData.bankPhoneTv.text = Data.bank?.phone
        bindingBinData.cityTv.text = Data.bank?.city

        if (Data.number?.luhn == null) {
            bindingBinData.luhnTv.text = NullText
        } else {
            if (Data.number?.luhn == true)
                bindingBinData.luhnTv.text =
                    Html.fromHtml("<font color=\"#4D4D4D\"><b>Yes</b></font> / No")
            else
                bindingBinData.luhnTv.text =
                    Html.fromHtml("Yes /  <font color=\"#4D4D4D\"><b>No</b></font>")
        }

        if (Data.prepaid == null) {
            bindingBinData.prepaidTv.text = NullText
        } else {
            if (Data.prepaid == true)
                bindingBinData.prepaidTv.text =
                    Html.fromHtml("<font color=\"#4D4D4D\"><b>Yes</b></font> / No")
            else
                bindingBinData.prepaidTv.text =
                    Html.fromHtml("Yes /  <font color=\"#4D4D4D\"><b>No</b></font>")
        }

    }

    private fun parserBinData(Data: String): APIModel {
        //val gson = GsonBuilder().create()
        //val resultData: APIModel = gson.fromJson(Data, APIModel::class.java)
        return GsonBuilder().create().fromJson(Data, APIModel::class.java)
    }


}