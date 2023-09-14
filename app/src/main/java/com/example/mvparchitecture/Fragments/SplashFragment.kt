package com.example.mvparchitecture.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mvparchitecture.R
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var nav : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initview(view)
        val user : Boolean = auth.currentUser!= null
        Handler(Looper.myLooper()!!).postDelayed(
            {
                checkuser(user)
            },
            1000
        )
    }

    private fun checkuser(user: Boolean) {
        if(user){
            nav.navigate(R.id.action_splashFragment_to_homeFragment)
        }
        else nav.navigate(R.id.action_splashFragment_to_signInFragment)

    }

    private fun initview(view : View) {
        auth= FirebaseAuth.getInstance()
        nav = Navigation.findNavController(view)
    }
}