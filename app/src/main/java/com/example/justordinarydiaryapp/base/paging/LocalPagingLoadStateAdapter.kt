package com.example.justordinarydiaryapp.base.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.justordinarydiaryapp.databinding.LayoutPagingLoadStateBinding

class LocalPagingLoadStateAdapter :
    LoadStateAdapter<LocalPagingLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: LayoutPagingLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutPagingLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.binding.apply {
            btnRetry.isVisible = false
            progressBar.isVisible = loadState is LoadState.Loading
        }
    }
}