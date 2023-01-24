package com.ds.money_manager.feature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.api.StatisticItemResponse
import com.ds.money_manager.databinding.ItemStatisticBinding
import com.ds.money_manager.utils.ImageUtils

class StatisticItemsAdapter(val context: Context) :
    BaseRecyclerViewAdapter<StatisticItemsAdapter.StatisticItemViewHolder, StatisticItemResponse>() {

    inner class StatisticItemViewHolder(val binding: ItemStatisticBinding) :
        BaseViewHolder(binding.root) {
        init {
            //binding.root.setOnClickListener {
            //    onClickListener(items[adapterPosition], adapterPosition)
            //}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticItemViewHolder {
        val binding = ItemStatisticBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatisticItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticItemViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            ImageUtils.loadPicture(context, item.image, imageViewIcon)
            textViewName.text = item.name
            textViewPercent.text = item.percent
            textViewAmount.text = item.amount.toPlainString()
        }
    }
}