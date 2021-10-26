package com.kotlin.mywallet.account.list

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.R
import com.kotlin.mywallet.data.entities.Account
import java.text.DecimalFormat

class AccountRecyclerAdapter(private val context: Context, private val accounts: MutableList<Account>, private val clickListener: (Account) -> Unit):
    RecyclerView.Adapter<AccountRecyclerAdapter.AccountViewHolder>(){

    class AccountViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        //obteniendo las referencias a las Views
        private val name = view.findViewById<TextView>(R.id.textView_itemAccount_accountName)
        private val amount = view.findViewById<TextView>(R.id.textView_itemAccount_amount)

        //"atando" los datos a las Views
        fun bind(account: Account){
            name.text = account.accountName
            val dec = DecimalFormat("#,###.##")
            val total = dec.format(account.totalAmount)
            amount.text = "$ $total MXN"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holderAccount: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holderAccount.bind(account)

        holderAccount.view.setOnClickListener{ clickListener(account) }

        val editButton = holderAccount.view.findViewById<ConstraintLayout>(R.id.layout_imageView_itemAccount_editIcon)
        val deleteButton = holderAccount.view.findViewById<ConstraintLayout>(R.id.layout_imageView_itemAccount_deleteIcon)

        editButton.setOnClickListener { Toast.makeText( context, "EDIT", Toast.LENGTH_SHORT).show() }
        deleteButton.setOnClickListener { Toast.makeText( context, "DELETE", Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

}

