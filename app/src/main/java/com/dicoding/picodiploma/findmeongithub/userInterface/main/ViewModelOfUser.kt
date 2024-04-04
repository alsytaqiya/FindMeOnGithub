package com.dicoding.picodiploma.findmeongithub.userInterface.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.findmeongithub.appProgrammingInterface.ClientRetrofit
import com.dicoding.picodiploma.findmeongithub.dataApp.model.ResponseOfUser
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelOfUser : ViewModel() {
    val usersList = MutableLiveData<ArrayList<UserModel>>()
    fun setUsersSearch(query: String) {
        ClientRetrofit.instanceApi
            .getUserSearch(query)
            .enqueue(object : Callback<ResponseOfUser> {
                override fun onResponse(
                    call: Call<ResponseOfUser>,
                    response: Response<ResponseOfUser>
                ) {
                    if (response.isSuccessful) {
                        usersList.postValue(response.body()?.itemUser)
                    }
                }

                override fun onFailure(call: Call<ResponseOfUser>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUsersSearch(): LiveData<ArrayList<UserModel>> {
        return usersList
    }


}