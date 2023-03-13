package com.example.justordinarydiaryapp.utils.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> ViewGroup.viewBinding(bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T) =
    bindingInflater(LayoutInflater.from(context), this, false)

fun <VH : RecyclerView.ViewHolder> RecyclerView.setAutoNullAdapter(
    adapter: RecyclerView.Adapter<VH>
) {
    this.adapter = adapter
    this.clearAdapter()
}

internal fun RecyclerView.clearAdapter() {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(p0: View) {

        }

        override fun onViewDetachedFromWindow(p0: View) {
            this@clearAdapter.adapter = null
        }

    })
}

inline fun RecyclerView.onScrolledListener(
    lifecycleOwner: LifecycleOwner,
    crossinline listener: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit
) {
    object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) =
            listener(recyclerView, dx, dy)
    }.let {
        LifecycleEventDispatcher(
            lifecycleOwner,
            onStart = { addOnScrollListener(it) },
            onStop = { removeOnScrollListener(it) }
        )
    }
}

fun Context.showDefaultToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

fun View?.goneView() {
    this?.visibility = View.GONE
}

fun View?.visibleView() {
    this?.visibility = View.VISIBLE
}