package com.example.justordinarydiaryapp.presentation.diary

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.justordinarydiaryapp.base.paging.PagingFooterTextViewHolder
import com.example.justordinarydiaryapp.base.paging.PagingUiModel
import com.example.justordinarydiaryapp.databinding.ItemDiaryBinding
import com.example.justordinarydiaryapp.databinding.LayoutPagingLoadStateBinding
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.utils.DateTimeHelper
import com.example.justordinarydiaryapp.utils.DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
import com.example.justordinarydiaryapp.utils.DateTimeHelper.FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE
import com.example.justordinarydiaryapp.utils.extension.viewBinding

class DiaryItemAdapter(val context: Context) :
    PagingDataAdapter<PagingUiModel<Diary>, RecyclerView.ViewHolder>(
        DiffUtilCallback()
    ) {

    var onRootClick: ((data: Diary) -> Unit)? = null

    inner class ViewHolder(private val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: Diary) {
            with(binding) {
                tvTitle.text = data.title
                tvContent.text = data.content
                val dateCreated = DateTimeHelper.getStringFromString(
                    sourceString = data.createdAt,
                    sourceFormat = FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE,
                    targetFormat = FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
                )
                tvCreatedTime.text = String.format("Created at %s", dateCreated)
                cvItemDiary.setOnClickListener {
                    onRootClick?.invoke(data)
                }
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<PagingUiModel<Diary>>() {
        override fun areItemsTheSame(
            oldItem: PagingUiModel<Diary>,
            newItem: PagingUiModel<Diary>
        ): Boolean {
            return (oldItem is PagingUiModel.DataItem && newItem is PagingUiModel.DataItem &&
                    oldItem.data.id == newItem.data.id) ||
                    (oldItem is PagingUiModel.SeparatorItem && newItem is PagingUiModel.SeparatorItem &&
                            oldItem.description == newItem.description)
        }

        override fun areContentsTheSame(
            oldItem: PagingUiModel<Diary>,
            newItem: PagingUiModel<Diary>
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel.let {
            when (uiModel) {
                is PagingUiModel.DataItem -> (holder as ViewHolder).bindView(uiModel.data)
                is PagingUiModel.SeparatorItem -> (holder as PagingFooterTextViewHolder).bindView(
                    uiModel.description
                )

                null -> throw UnsupportedOperationException("Unknown view")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> ViewHolder(parent.viewBinding(ItemDiaryBinding::inflate))
            else -> PagingFooterTextViewHolder(parent.viewBinding(LayoutPagingLoadStateBinding::inflate))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingUiModel.DataItem -> 1
            is PagingUiModel.SeparatorItem -> 2
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

}