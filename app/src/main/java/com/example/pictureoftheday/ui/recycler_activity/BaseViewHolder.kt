package com.example.pictureoftheday.ui.recycler_activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.ui.recycler_activity.model.Data

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)
}