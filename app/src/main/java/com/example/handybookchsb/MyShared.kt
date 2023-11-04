package com.example.handybookchsb

import android.content.Context
import android.content.SharedPreferences
import com.example.handybookchsb.model.f
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyShared private constructor(context: Context){
    private val shared: SharedPreferences = context.getSharedPreferences("data", 0)
    private val edit: SharedPreferences.Editor = shared.edit()
    private val gson  = Gson()

    companion object{
        private var instance :MyShared? = null
        fun getInstance(context: Context):MyShared{
            if (instance == null){
                instance = MyShared(context)
            }
            return instance!!
        }
    }

    fun setUser(user: f){
        val data  = gson.toJson(user)
        edit.putString("user", data).apply()
    }

    fun logOut(){
        edit.putString("user", "").apply()
    }
    fun getUser():f?{
        val data = shared.getString("user", "")
        if (data == "") return null
        val typeToken = object : TypeToken<f>() {}.type
        return gson.fromJson(data, typeToken)
    }


}