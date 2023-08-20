package com.example.mvparchitecture.Model

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.mvparchitecture.Presenter.Presenter
import com.example.mvparchitecture.databinding.DialogBinding


class CustomDialog(contextt: Context) : Dialog(contextt) {

    private lateinit var binding: DialogBinding

    private lateinit var listener : Listener


    fun initlistener(listener: Listener){
        this.listener= listener
    }

    companion object{
        private  var preTask: Task?=null
        fun initprevious(task : Task){
            preTask= task
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    private fun init() {
        binding.edtnote.requestFocus()
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        if(preTask!=null){
            binding.edtnote.setText(preTask?.note)
            preTask?.note?.let { binding.edtnote.setSelection(it.length) }
            binding.edtdes.setText(preTask?.des)
        }

        binding.btnnext.setOnClickListener {
            val note = binding.edtnote.text.toString()
            val des = binding.edtdes.text.toString()
            if(preTask!=null){
                //update

            }else{
                if(note.isNotEmpty()){
                    listener.addnewnote(Task(note, des))
                    dismiss()
                }
            }

        }
    }

    interface Listener{
        fun addnewnote(note: Task)

    }



}