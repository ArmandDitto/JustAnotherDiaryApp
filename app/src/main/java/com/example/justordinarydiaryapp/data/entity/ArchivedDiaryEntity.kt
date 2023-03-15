package com.example.justordinarydiaryapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.justordinarydiaryapp.model.Diary

@Entity(tableName = ArchivedDiaryEntity.TABLE_NAME)
class ArchivedDiaryEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "content")
    val content: String? = null,

    @ColumnInfo(name = "is_archieved")
    val isArchieved: Boolean? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null

) {

    companion object {
        const val TABLE_NAME = "table_archived_diary"
    }

    object ModelMapper {
        fun fromDiary(diary: Diary?): ArchivedDiaryEntity {
            return ArchivedDiaryEntity(
                id = diary?.id ?: "",
                title = diary?.title,
                content = diary?.content,
                isArchieved = diary?.isArchieved,
                createdAt = diary?.createdAt,
                updatedAt = diary?.updatedAt
            )
        }

        fun toDiary(diaryEntity: ArchivedDiaryEntity?): Diary {
            return Diary(
                id = diaryEntity?.id ?: "",
                title = diaryEntity?.title,
                content = diaryEntity?.content,
                isArchieved = diaryEntity?.isArchieved,
                createdAt = diaryEntity?.createdAt,
                updatedAt = diaryEntity?.updatedAt
            )
        }
    }

}