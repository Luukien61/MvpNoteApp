package com.example.mvparchitecture.Model.Fragments.SignIn

import android.os.Bundle
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
import com.example.mvparchitecture.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() , Presenter.getUser{

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var presenter: Presenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentSignInBinding.inflate(inflater, container, false)
       return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        setevent()
    }

    private fun setevent() {
        binding.btnnext.setOnClickListener{
            presenter!!.onSignInclick()
        }
    }

    private fun init(view: View) {
        auth = FirebaseAuth.getInstance()
        navController= Navigation.findNavController(view)
        presenter= Presenter()
        presenter.init(this)

    }

    override fun is_success(user: User) {
        signin(user!!)
    }

    private fun signin(user: User){
        auth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if(it.isSuccessful){
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            }
            else {
                Toast.makeText(requireActivity(), "Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun is_fail(error : String) {
        Toast.makeText(requireActivity(),
            error, Toast.LENGTH_SHORT).show()
    }

    override fun getuser(): List<String> {
        val email = binding.edtemail.text.toString()
        val password = binding.edtpassword.text.toString()
        return listOf(email, password)
    }


}