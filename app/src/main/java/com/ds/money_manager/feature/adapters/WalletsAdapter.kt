package com.ds.money_manager.feature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.databinding.ItemWalletBinding
import com.ds.money_manager.utils.CurrencyUtils

class WalletsAdapter(val context: Context) :
    BaseRecyclerViewAdapter<WalletsAdapter.WalletViewHolder, WalletDetailsResponse>() {

    inner class WalletViewHolder(val binding: ItemWalletBinding) :
        BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(items[adapterPosition], adapterPosition)
            }
        }

        fun bind(item: WalletDetailsResponse) {
            with(binding) {
                textViewName.text = item.name
                textViewAmount.text = CurrencyUtils.showAmount(item.balance)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val binding = ItemWalletBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WalletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(items[position])
    }
}