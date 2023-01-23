package com.ds.money_manager.feature.main.fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.EmptyWallet
import com.ds.money_manager.data.model.WalletItem
import com.ds.money_manager.data.model.api.WalletDetailsResponse
import com.ds.money_manager.databinding.ItemWalletEmptyVpBinding
import com.ds.money_manager.databinding.ItemWalletVpBinding

class WalletsViewPagerAdapter : BaseRecyclerViewAdapter<BaseViewHolder, WalletItem>() {

    companion object {
        const val VIEW_TYPE_WALLET_DETAILS = 1
        const val VIEW_TYPE_WALLET_EMPTY = 2
    }

    inner class WalletDetailsViewHolder(val binding: ItemWalletVpBinding) :
        BaseViewHolder(binding.root) {
        init {
            //binding.root.setOnClickListener {
            //    onClickListener(items[adapterPosition], adapterPosition)
            //}
        }

        fun bind(item: WalletDetailsResponse) {
            with(binding) {
                textViewWalletTitle.text = item.name
                textViewWalletBalance.text = item.balance.toString()
                textViewIncome.text = item.income.toString()
                textViewOutcome.text = item.outcome.toString()
            }
        }
    }

    inner class WalletEmptyViewHolder(val binding: ItemWalletEmptyVpBinding) :
        BaseViewHolder(binding.root) {
        init {
            //binding.root.setOnClickListener {
            //    onClickListener(items[adapterPosition], adapterPosition)
            //}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_WALLET_DETAILS -> {
                val binding = ItemWalletVpBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                WalletDetailsViewHolder(binding)
            }
            VIEW_TYPE_WALLET_EMPTY -> {
                val binding = ItemWalletEmptyVpBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                WalletEmptyViewHolder(binding)
            }
            else -> throw Exception("Recycler view not found")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is WalletDetailsViewHolder -> {
                holder.bind(item as WalletDetailsResponse)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is WalletDetailsResponse -> VIEW_TYPE_WALLET_DETAILS
            is EmptyWallet -> VIEW_TYPE_WALLET_EMPTY
            else -> throw Exception("Recycler item not found")
        }
    }
}