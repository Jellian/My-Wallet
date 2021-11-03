package com.kotlin.mywallet.charge.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.mywallet.R
import com.kotlin.mywallet.data.entities.Charge
import com.kotlin.mywallet.databinding.ItemChargeBinding

class ChargeAdapter (private val context: Context, val viewModel: ChargeListViewModel):
    ListAdapter<Charge, ChargeAdapter.ChargeViewHolder>(ChargeDiffCallback){

    class ChargeViewHolder(val binding: ItemChargeBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind( viewModel: ChargeListViewModel, itemCharge: Charge ){
            binding.apply {
                charge = itemCharge
                this.viewModel = viewModel
                executePendingBindings()
            }

            if (itemCharge.type != "Ingreso"){
                binding.imageViewItemChargeArrowIcon.setImageResource(R.drawable.ic_arrow_red)
            }
            else{
                binding.imageViewItemChargeArrowIcon.setImageResource(R.drawable.ic_arrow_green)
                binding.imageViewItemChargeArrowIcon.rotation = 180.0f
            }
        }

        companion object {
            fun from(parent: ViewGroup): ChargeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemChargeBinding.inflate(layoutInflater, parent, false)

                return ChargeViewHolder( binding )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargeViewHolder {
        return ChargeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderCharge: ChargeViewHolder, position: Int) {
        val item = getItem(position)
        holderCharge.bind(viewModel, item)

        //holderCharge.itemView.setOnClickListener{ clickListener(item) }
    }

    object ChargeDiffCallback: DiffUtil.ItemCallback<Charge>(){
        override fun areItemsTheSame(oldItem: Charge, newItem: Charge): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Charge, newItem: Charge): Boolean {
            return oldItem == newItem
        }

    }
}