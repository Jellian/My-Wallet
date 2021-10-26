package com.kotlin.mywallet.account.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.data.entities.Account
import com.kotlin.mywallet.databinding.ItemAccountBinding

class AccountAdapter(private val context: Context, val viewModel: AccountListViewModel, private val clickListener: (Account) -> Unit):
    ListAdapter<Account, AccountAdapter.AccountViewHolder>(AccountDiffCallback){

    class AccountViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind( viewModel: AccountListViewModel, itemAccount: Account ){
            binding.apply {
                account = itemAccount
                this.viewModel = viewModel

                executePendingBindings()

//                name.text = account.accountName
//                val dec = DecimalFormat("#,###.##")
//                val total = dec.format(account.totalAmount)
//                amount.text = "$ $total MXN"

            }
        }

        companion object {
            fun from(parent: ViewGroup): AccountViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAccountBinding.inflate(layoutInflater, parent, false)

                return AccountViewHolder( binding )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderAccount: AccountViewHolder, position: Int) {
        val item = getItem(position)
        holderAccount.bind(viewModel, item)

        holderAccount.itemView.setOnClickListener{ clickListener(item) }
        //holderAccount.binding.imageViewItemAccountEditIcon.setOnClickListener {  Toast.makeText( context, "EDIT", Toast.LENGTH_SHORT).show()  }


 //           .view.setOnClickListener{ clickListener(account) }
//
//        val editButton = holderAccount.view.findViewById<ConstraintLayout>(R.id.layout_imageView_itemAccount_editIcon)
//        val deleteButton = holderAccount.view.findViewById<ConstraintLayout>(R.id.layout_imageView_itemAccount_deleteIcon)
//
//        editButton.setOnClickListener { Toast.makeText( context, "EDIT", Toast.LENGTH_SHORT).show() }
//        deleteButton.setOnClickListener { Toast.makeText( context, "DELETE", Toast.LENGTH_SHORT).show() }
    }

}

object AccountDiffCallback: DiffUtil.ItemCallback<Account>(){
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }

}


