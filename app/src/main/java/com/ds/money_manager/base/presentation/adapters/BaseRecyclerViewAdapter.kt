package com.ds.money_manager.base.presentation.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ds.money_manager.base.presentation.viewholders.BaseViewHolder

abstract class BaseRecyclerViewAdapter<T : BaseViewHolder, K : Any> : RecyclerView.Adapter<T>() {

    val items: MutableList<K> = mutableListOf()
    var onClickListener: (item: K, position: Int) -> Unit = { _, _ -> }
    var onLongClickListener: (item: K, position: Int) -> Unit = { _, _ -> }

    private var recyclerView: RecyclerView? = null

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T

    abstract override fun onBindViewHolder(holder: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        this.recyclerView = null
    }

    fun addItems(items: List<K>) {
        val startPosition = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(startPosition, items.size)
    }

    fun addItem(item: K) {
        val startPosition = this.items.size
        items.add(item)
        notifyItemRangeInserted(startPosition, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<K>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}