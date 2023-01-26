package com.example.projectbin.fragments

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbin.R
import com.example.projectbin.databinding.BinItemBinding

class Adapter(private val listener: Listener) : RecyclerView.Adapter<Adapter.Holder>() {

    private var binList = arrayOf<String>()

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = BinItemBinding.bind(item)

        fun bind(data: String, listener: Listener) = with(binding) {
            oldBinTv.text = data

            itemView.setOnClickListener {
                listener.onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bin_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return binList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(binList[position], listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dataSynchronization(datas: HashSet<String>) {
        binList = datas.toTypedArray()
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick(data: String)
    }
}