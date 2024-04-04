package com.dicoding.picodiploma.findmeongithub.userInterface.myfavofavo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.DaoOfYayUser
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.DatabaseOfUser
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.YayUser

class MyFavoFavoViewModel(application: Application) : AndroidViewModel(application) {
    private var daoOfUser: DaoOfYayUser?
    private var dbOfUser: DatabaseOfUser?

    init {
        dbOfUser = DatabaseOfUser.getDatabase(application)
        daoOfUser = dbOfUser?.daoOfYayUser()
    }

    fun getYayUser(): LiveData<List<YayUser>>? {
        return daoOfUser?.getYayUser()
    }
}