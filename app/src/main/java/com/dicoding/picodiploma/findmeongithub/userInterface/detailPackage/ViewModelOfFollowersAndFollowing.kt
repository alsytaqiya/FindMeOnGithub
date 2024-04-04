package com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.findmeongithub.appProgrammingInterface.ClientRetrofit
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelOfFollowersAndFollowing : ViewModel() {

    val followers = MutableLiveData<ArrayList<UserModel>>()

    val following = MutableLiveData<ArrayList<UserModel>>()


    fun setFollowing(username: String) {
        ClientRetrofit.instanceApi
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        following.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getFollowing(): LiveData<ArrayList<UserModel>> {
        return following
    }

    fun setFollower(username: String) {
        ClientRetrofit.instanceApi
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>>,
                    response: Response<ArrayList<UserModel>>
                ) {
                    if (response.isSuccessful) {
                        followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun getFollower(): LiveData<ArrayList<UserModel>> {
        return followers
    }

}