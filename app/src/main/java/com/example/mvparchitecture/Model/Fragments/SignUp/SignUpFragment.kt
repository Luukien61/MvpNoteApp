package com.example.mvparchitecture.Model.Fragments.SignUp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mvparchitecture.Model.User
import com.example.mvparchitecture.Presenter.Presenter
import com.example.mvparchitecture.R
import com.example.mvparchitecture.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.logging.Logger

class SignUpFragment : Fragment(), Presenter.getUser {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var presenter: Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setevents()
    }

    private fun setevents() {
        binding.btnnext.setOnClickListener {
            presenter!!.onsignup()
        }
        binding.btnsignin.setOnClickListener {

        }
    }

    override fun getuser(): List<String> {
        val email = binding.edtemail.text.toString()
        val password = binding.edtpassword.text.toString()
        val checkpass = binding.edtcheckpass.text.toString()
       return listOf(email, password, checkpass)
    }

    override fun is_success(user: User) {
        auth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if(it.isSuccessful){
                navController.navigate(R.id.action_signUpFragment_to_homeFragment)
            }
            else {
                Log.d("fail", it.exception.toString())
            }
        }
    }

    override fun is_fail(error : String) {
       Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }


    private fun init(view : View){

        auth= FirebaseAuth.getInstance()
        navController= Navigation.findNavController(view)
        presenter= Presenter()
        presenter.init(this)
    }


}