package com.example.mvparchitecture.RecycleView

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvparchitecture.Model.Task
import com.example.mvparchitecture.databinding.EachItemBinding

class RecycleAdapter: RecyclerView.Adapter<RecycleAdapter.viewholder>() {

    private  val  listtask by lazy {
         ArrayList<Task>()
    }

    fun initlist(list : ArrayList<Task>){
        val diffCallback = MyDiffCallback(listtask, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listtask.clear()
        listtask.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class viewholder(val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
             binding.constraint1.setOnClickListener {
                 listtask[absoluteAdapterPosition].visi=!listtask[absoluteAdapterPosition].visi
                 TransitionManager.beginDelayedTransition(itemView as ViewGroup?, AutoTransition())
                 notifyItemChanged(absoluteAdapterPosition)

             }
        }
        fun bind(task : Task){
            binding.txtnote.setText(task.note)
            binding.txtdes.setText(task.des)
            if(task.visi) binding.constraint2.visibility= View.VISIBLE
            else binding.constraint2.visibility= View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {

        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false )
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
        Log.i("size ", listtask.size.toString())
       return listtask.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val task = listtask[position]
        holder.bind(task)
    }


}