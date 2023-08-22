package com.example.mvparchitecture.Model.Fragments


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.example.mvparchitecture.Model.Task
import com.example.mvparchitecture.R
import com.example.mvparchitecture.databinding.FragmentPopUpBinding

class PopUpFragment : DialogFragment() {
    private lateinit var binding: FragmentPopUpBinding
    private lateinit var popupinterface: PopUpInterface


    fun initlistener(listenerr: PopUpInterface) {
        this.popupinterface = listenerr
    }


    private var prevtask: Task? = null
    fun initprevious(note: Task) {
        prevtask = note
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopUpBinding.inflate(inflater, container, false)
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bottomsheet)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // showSoftKeyboard(binding.edtnote)
        init()

    }

    private fun init() {

       binding.edtnote.requestFocus()
        if (prevtask != null) {
            binding.edtnote.setText(prevtask?.note)
            binding.edtdes.setText(prevtask?.des)
        }
        binding.edtnote.requestFocus()
        prevtask?.note?.let { binding.edtnote.setSelection(it.length) }

        binding.btnnext.setOnClickListener {
            val note = binding.edtnote.text.toString()
            val des = binding.edtdes.text.toString()

            if (prevtask != null) {
                val task = Task(note, des)
                task.id = prevtask?.id
                popupinterface.updatenote(task)

            } else {
                if (note.isNotEmpty()) {
                    popupinterface?.let { it.addnewnotee(Task(note, des), "Add") }

                }
            }
            dismiss()

            binding.edtdes.setText("")
            binding.edtdes.setText("")

        }
    }



    interface PopUpInterface {
        fun addnewnotee(task: Task, mess: String)
        fun updatenote(note: Task)
    }


}