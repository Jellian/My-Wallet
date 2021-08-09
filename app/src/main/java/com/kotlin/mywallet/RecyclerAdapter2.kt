package com.kotlin.mywallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.finance.Cargo

class RecyclerAdapter2(private val context: Context, private val charges : List<Cargo>) : RecyclerView.Adapter<RecyclerAdapter2.ViewHolder2>(){

    class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {

        private val type = view.findViewById<TextView>(R.id.textView_itemCharge_charge)
        private val amount = view.findViewById<TextView>(R.id.textView_itemCharge_amount)
        private val category = view.findViewById<TextView>(R.id.textView_itemCharge_category)
        private val note = view.findViewById<TextView>(R.id.textView_itemCharge_note)
        private val arrow = view.findViewById<ImageView>(R.id.imageView_itemCharge_arrowIcon)
        private val date = view.findViewById<TextView>(R.id.textView_itemCharge_date)

        fun bind(charge: Cargo, context: Context){
            type.text = charge.getType()
            amount.text = charge.getAmount().toString()
            category.text = charge.getCategory()
            note.text = charge.getNote()
            date.text = charge.getDate()

            if (charge.getType() != "Ingreso"){
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
        holder.bind(charge, context)
    }

    override fun getItemCount(): Int {
        return charges.size
    }

}
