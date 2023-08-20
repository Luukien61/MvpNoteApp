package com.example.mvparchitecture.RecycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvparchitecture.Model.Task
import com.example.mvparchitecture.databinding.EachItemBinding

class RecycleAdapter: RecyclerView.Adapter<RecycleAdapter.viewholder>() {

    private lateinit var listtask : ArrayList<Task>

    fun initlist(list : ArrayList<Task>){
        listtask= list
    }

    inner class viewholder(val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
             binding.constraint1.setOnClickListener {
                 listtask[adapterPosition].visi=!listtask[adapterPosition].visi
                 notifyItemChanged(adapterPosition)
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
       return listtask.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val task = listtask[position]
        holder.bind(task)

    }


}