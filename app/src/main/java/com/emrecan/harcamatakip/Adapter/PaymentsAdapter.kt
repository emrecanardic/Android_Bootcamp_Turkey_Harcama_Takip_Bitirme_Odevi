package com.emrecan.harcamatakip.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.canerkaya.harcamatakip.R
import com.emrecan.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.R

class PaymentsAdapter(private val paymentList:ArrayList<PaymentModel>, private val costType:String, private val listener:Listener):RecyclerView.Adapter<PaymentsAdapter.RowHolder>() {
    interface Listener{
        fun onItemClick(paymentModel: PaymentModel,costType: String)
    }
    class RowHolder(view:View):RecyclerView.ViewHolder(view) {
        val paymentImage:ImageView = view.findViewById(R.id.paymentLogoImageView)
        val paymentName:TextView = view.findViewById(R.id.paymentNameTv)
        val paymentCost:TextView = view.findViewById(R.id.paymentCostTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.payment_row,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.paymentName.text = paymentList[position].paymentName
        when(costType){
            "₺" ->{holder.paymentCost.setText(holder.itemView.context.getString(R.string.cost,paymentList[position].tlCost,"₺"))}
            "$" ->{holder.paymentCost.setText(holder.itemView.context.getString(R.string.cost,paymentList[position].dolarCost,"$"))}
            "€" ->{holder.paymentCost.setText(holder.itemView.context.getString(R.string.cost,paymentList[position].euroCost,"€"))}
            "£" ->{holder.paymentCost.setText(holder.itemView.context.getString(R.string.cost,paymentList[position].sterlinCost,"£"))}
        }
        when(paymentList[position].paymentType){
            "Kira" -> {
                holder.paymentImage.setImageResource(R.drawable.home_icon)
            }
            "Fatura" -> {
                holder.paymentImage.setImageResource(R.drawable.bill_logo)
            }
            else -> {
                holder.paymentImage.setImageResource(R.drawable.shop_icon)
            }
        }
        holder.itemView.setOnClickListener {listener.onItemClick(paymentList[position],costType)}

    }

    override fun getItemCount(): Int {
        return paymentList.size
    }
}