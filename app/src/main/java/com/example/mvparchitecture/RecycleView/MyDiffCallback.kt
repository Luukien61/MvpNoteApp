package com.example.mvparchitecture.RecycleView

import androidx.recyclerview.widget.DiffUtil
import com.example.mvparchitecture.Model.Task

class MyDiffCallback(private val oldList: ArrayList<Task>, private val newList: ArrayList<Task>)
    : DiffUtil.Callback(){

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].id==newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition]==newList[newItemPosition]
    }
}