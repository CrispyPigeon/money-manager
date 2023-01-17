package com.ds.money_manager.feature.main.fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ds.money_manager.R
import com.ds.money_manager.databinding.ItemWalletEmptyVpBinding

class WalletsViewPagerAdapter : RecyclerView.Adapter<WalletsViewPagerAdapter.WalletViewHolder>() {


    inner class WalletViewHolder(val binding: ItemWalletEmptyVpBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.root.setOnClickListener {
//                onClickListener(items[adapterPosition], adapterPosition)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletEmptyVpBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) = holder.itemView.run {
        //tvTitle.text = "item $position"
        //container.setBackgroundResource(colors[position])
    }

    override fun getItemCount() = 3
}