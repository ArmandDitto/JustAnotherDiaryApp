package com.example.justordinarydiaryapp.utils.extension

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.justordinarydiaryapp.R

fun TextView.setColoredText(
    message: String,
    color: Int? = null,
    isUnderline: Boolean = false,
    isBold: Boolean = true,
    clickAction: (() -> Unit) = { }
) {

    val spannableString = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickAction.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            if (color != null) ds.color = ContextCompat.getColor(context, color)
            else ds.color = ContextCompat.getColor(context, R.color.color_primary)
            if (!isUnderline) ds.isUnderlineText = false
            if (isBold) ds.typeface = typeface
        }
    }
    val startIndex = text.indexOf(message)
    if (startIndex != -1) {
        spannableString.setSpan(
            clickableSpan,
            startIndex,
            startIndex + message.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    movementMethod = LinkMovementMethod.getInstance()
    setText(spannableString, TextView.BufferType.SPANNABLE)
    highlightColor = Color.TRANSPARENT
}