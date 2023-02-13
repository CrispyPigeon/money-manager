package com.ds.money_manager.feature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.api.TransactionResponse
import com.ds.money_manager.databinding.ItemCommonBinding
import com.ds.money_manager.extensions.loadPicture
import com.ds.money_manager.utils.CurrencyUtils
import com.ds.money_manager.utils.ImageUtils
import com.ds.money_manager.utils.StringUtils.toDateString

class TransactionsAdapter(val context: Context) :
    BaseRecyclerViewAdapter<TransactionsAdapter.TransactionViewHolder, TransactionResponse>() {

    inner class TransactionViewHolder(val binding: ItemCommonBinding) :
        BaseViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemCommonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            imageViewIcon.loadPicture(context, item.typeImage)
            textViewName.text = item.name
            textViewDescription.text = item.dateTime.toDateString()
            textViewAmount.text = CurrencyUtils.showAmount(item.amount)
        }
    }
}