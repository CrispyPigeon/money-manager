package com.ds.money_manager.feature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ds.money_manager.base.presentation.adapters.BaseRecyclerViewAdapter
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder
import com.ds.money_manager.data.model.api.CostTypeResponse
import com.ds.money_manager.databinding.ItemCostTypeBinding
import com.ds.money_manager.extensions.loadPicture

class CostTypesAdapter(val context: Context) :
    BaseRecyclerViewAdapter<CostTypesAdapter.CostTypeViewHolder, CostTypeResponse>() {

    inner class CostTypeViewHolder(val binding: ItemCostTypeBinding) :
        BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickListener(items[adapterPosition], adapterPosition)
            }
        }

        fun bind(item: CostTypeResponse) {
            with(binding) {
                textViewName.text = item.name
                imageViewIcon.loadPicture(context, item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostTypeViewHolder {
        val binding = ItemCostTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CostTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CostTypeViewHolder, position: Int) {
        holder.bind(items[position])
    }
}