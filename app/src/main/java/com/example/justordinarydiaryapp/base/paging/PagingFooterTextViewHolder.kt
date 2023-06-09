package com.example.justordinarydiaryapp.base.paging

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.justordinarydiaryapp.databinding.LayoutPagingLoadStateBinding

class PagingFooterTextViewHolder(private val binding: LayoutPagingLoadStateBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindView(desc: String) {
        with(binding) {
            progressBar.isVisible = false
            btnRetry.isVisible = false
            tvMessage.text = desc
        }
    }
}