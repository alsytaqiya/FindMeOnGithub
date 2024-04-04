package com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.picodiploma.findmeongithub.appProgrammingInterface.ClientRetrofit
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.DaoOfYayUser
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.DatabaseOfUser
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.YayUser
import com.dicoding.picodiploma.findmeongithub.dataApp.model.DetailResponseOfUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailOfUserViewModel(application: Application) : AndroidViewModel(application) {
    val githubUsername = MutableLiveData<DetailResponseOfUser>()

    private var daoOfUser: DaoOfYayUser?
    private var dbOfUser: DatabaseOfUser?

    init {
        dbOfUser = DatabaseOfUser.getDatabase(application)
        daoOfUser = dbOfUser?.daoOfYayUser()
    }

    fun setUserGithubDetail(username: String) {
        ClientRetrofit.instanceApi
            .getDetailOfUser(username)
            .enqueue(object : Callback<DetailResponseOfUser> {
                override fun onResponse(
                    call: Call<DetailResponseOfUser>,
                    systemResponse: Response<DetailResponseOfUser>
                ) {
                    if (systemResponse.isSuccessful) {
                        githubUsername.postValue(systemResponse.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponseOfUser>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun getUserGithubDetail(): LiveData<DetailResponseOfUser> {
        return githubUsername
    }

    fun addToYay(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var githubUser = YayUser(
                username,
                id,
                avatarUrl
            )
            daoOfUser?.addToFavorite(githubUser)
        }
    }

    suspend fun userCheck(id: Int) = daoOfUser?.userCheck(id)

//    val isFavorite = ViewModelOfFollowersAndFollowing.userCheck(id)
//    withContext(Dispatchers.Main) {
//        _isChecked = isFavorite
//        binding.favToggle.isChecked = _isChecked
//    }


    fun removeFromYay(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            daoOfUser?.removeFromYay(id)
        }
    }

}