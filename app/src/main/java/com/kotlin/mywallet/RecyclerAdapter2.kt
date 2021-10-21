package com.kotlin.mywallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.finance.Cargo
import java.text.DecimalFormat

class RecyclerAdapter2(private val context: Context, private val charges : MutableList<Charge>) : RecyclerView.Adapter<RecyclerAdapter2.ViewHolder2>(){

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        private val type = view.findViewById<TextView>(R.id.textView_itemCharge_charge)
        private val amount = view.findViewById<TextView>(R.id.textView_itemCharge_amount)
        private val category = view.findViewById<TextView>(R.id.textView_itemCharge_category)
        private val note = view.findViewById<TextView>(R.id.textView_itemCharge_note)
        private val arrow = view.findViewById<ImageView>(R.id.imageView_itemCharge_arrowIcon)
        private val date = view.findViewById<TextView>(R.id.textView_itemCharge_date)

        fun bind(charge: Charge){
            type.text = charge.type

            val dec = DecimalFormat("#,###.##")
            val total = dec.format(charge.amount)
            amount.text = "$ $total MXN"

            category.text = charge.category
            note.text = charge.note
            date.text = charge.date

            if (charge.type != "Ingreso"){
                arrow.setImageResource(R.drawable.ic_arrow_red)
            }
            else{
                arrow.setImageResource(R.drawable.ic_arrow_green)
                arrow.rotation = 180.0f
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_charge, parent, false)
        return ViewHolder2(view)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val charge = charges[position]
        holder.bind(charge)
    }

    override fun getItemCount(): Int {
        return charges.size
    }

}
