package com.ds.money_manager.feature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.databinding.ItemCommonBinding
import com.ds.money_manager.extensions.loadPicture
import com.ds.money_manager.utils.CurrencyUtils

class StatisticItemsAdapter(val context: Context) :
    BaseRecyclerViewAdapter<StatisticItemsAdapter.StatisticItemViewHolder, StatisticItemResponse>() {

    inner class StatisticItemViewHolder(val binding: ItemCommonBinding) :
        BaseViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticItemViewHolder {
        val binding = ItemCommonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatisticItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticItemViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            imageViewIcon.loadPicture(context, item.image)
            textViewName.text = item.name
            textViewDescription.text = item.percent
            textViewAmount.text = CurrencyUtils.showAmount(item.amount)
        }
    }
}