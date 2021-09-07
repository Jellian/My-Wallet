package com.kotlin.mywallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.finance.Cuenta
import java.text.DecimalFormat

class RecyclerAdapter( private val context: Context, private val accounts: MutableList<Cuenta>, private val clickListener: (Cuenta) -> Unit):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        //obteniendo las referencias a las Views
        private val name = view.findViewById<TextView>(R.id.textView_itemAccount_accountName)
        private val amount = view.findViewById<TextView>(R.id.textView_itemAccount_amount)

        //"atando" los datos a las Views
        fun bind(account: Cuenta){
            name.text = account.getName()
            val dec = DecimalFormat("#,###.##")
            val total = dec.format(account.getTotalAmount())
            amount.text = "$ $total MXN"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.bind(account)

        holder.view.setOnClickListener{clickListener(account)}
    }

    override fun getItemCount(): Int {
        return accounts.size
    }


}
