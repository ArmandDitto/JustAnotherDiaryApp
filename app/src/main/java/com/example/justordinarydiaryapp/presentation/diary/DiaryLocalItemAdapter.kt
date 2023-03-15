package com.example.justordinarydiaryapp.presentation.diary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.justordinarydiaryapp.databinding.ItemDiaryBinding
import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.utils.DateTimeHelper
import com.example.justordinarydiaryapp.utils.extension.visibleView

class DiaryLocalItemAdapter(isArchiveMenu: Boolean? = false) :
    PagingDataAdapter<Diary, DiaryLocalItemAdapter.MainViewHolder>(DIFF_CALLBACK) {

    var onRootClick: ((data: Diary) -> Unit)? = null
    var onUnarchived: ((data: Diary) -> Unit)? = null
    var isInArchiveMenu = isArchiveMenu

    inner class MainViewHolder(val binding: ItemDiaryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemDiaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {
            tvTitle.text = data?.title
            tvContent.text = data?.content
            val dateCreated = DateTimeHelper.getStringFromString(
                sourceString = data?.createdAt,
                sourceFormat = DateTimeHelper.FORMAT_YEAR_MONTH_DAY_HOUR24_MINUTE_SECOND_MILLISECOND_TIMEZONE,
                targetFormat = DateTimeHelper.FORMAT_DAY_MONTHNAME_YEAR_HOUR24_MINUTE
            )
            tvCreatedTime.text = String.format("Created at %s", dateCreated)
            cvItemDiary.setOnClickListener {
                data?.let { diary -> onRootClick?.invoke(diary) }
            }
            if(isInArchiveMenu == true) {
                cvUnarchive.visibleView()
                cvUnarchive.setOnClickListener {
                    data?.let { onUnarchived?.invoke(it) }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Diary>() {
            override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean =
                oldItem == newItem
        }
    }
}