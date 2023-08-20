package com.example.mvparchitecture.Presenter

import com.example.mvparchitecture.Model.Task
import com.example.mvparchitecture.Model.User

class Presenter {

    private lateinit var user: User
    private lateinit var _getuser: getUser
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var homeaction: HomeAction



    fun init(_getuser : getUser){
        this._getuser= _getuser
    }

    fun inithomeaction( listener : HomeAction){
        homeaction= listener
    }


    fun onSignInclick(){
        inituser()
        if(email.isNotEmpty()&&password.isNotEmpty()){
            user=User(email,password)
            _getuser.is_success(user)
        }
        else _getuser.is_fail("Fill the require fields")
    }

    fun onsignup() {
        inituser()
        val pass= _getuser.getuser().get(2)
        if(email.isNotEmpty()&&password.isNotEmpty()&&pass.isNotEmpty()){
            if(!password.equals(pass)){
                _getuser.is_fail("Password dont match")
            }
            else {
                user = User(email, password)
                _getuser.is_success(user)
            }
        }
        else {
            _getuser.is_fail("Fill the require fields")

        }
    }


    private fun inituser(){
        email= _getuser.getuser()[0]
        password= _getuser.getuser()[1]
    }


    fun deleteswipe(id: Task) {
        homeaction.deleteswipe(id)
    }





    interface getUser{
        fun getuser(): List<String>
        fun is_success(user: User)
        fun is_fail(error : String)
    }

    interface HomeAction{
        fun deleteswipe(id: Task)

    }


}