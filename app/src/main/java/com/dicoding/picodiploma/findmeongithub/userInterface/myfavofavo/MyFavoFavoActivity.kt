package com.dicoding.picodiploma.findmeongithub.userInterface.myfavofavo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.findmeongithub.dataApp.localData.YayUser
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import com.dicoding.picodiploma.findmeongithub.databinding.ActivityMyFavoFavoBinding
import com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage.DetailOfUserActivity
import com.dicoding.picodiploma.findmeongithub.userInterface.main.AdapterOfUser

class MyFavoFavoActivity : AppCompatActivity() {

    private lateinit var bindingItems: ActivityMyFavoFavoBinding
    private lateinit var userAdapter: AdapterOfUser
    private lateinit var userViewModel: MyFavoFavoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingItems = ActivityMyFavoFavoBinding.inflate(layoutInflater)
        setContentView(bindingItems.root)

        userAdapter = AdapterOfUser()
        userAdapter.notifyDataSetChanged()

        userViewModel = ViewModelProvider(this).get(MyFavoFavoViewModel::class.java)


        userAdapter.setOnItemClickCallback(object : AdapterOfUser.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                Intent(this@MyFavoFavoActivity, DetailOfUserActivity::class.java).also {
                    it.putExtra(DetailOfUserActivity.EXTRA_GITHUBUSERNAME, data.login)
                    it.putExtra(DetailOfUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailOfUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        bindingItems.apply {
            rvGithubusers.setHasFixedSize(true)
            rvGithubusers.layoutManager = LinearLayoutManager(this@MyFavoFavoActivity)
            rvGithubusers.adapter = userAdapter
        }

        userViewModel = ViewModelProvider(this).get(MyFavoFavoViewModel::class.java)
        userViewModel.getYayUser()?.observe(this) {
            if (it != null) {
                val list =mapList(it)
                userAdapter.setUserList(list)
            }
        }
    }

    private fun mapList(users: List<YayUser>): ArrayList<UserModel> {
        val listUser = ArrayList<UserModel>()
        for (user in users) {
            val userMapped = UserModel(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(userMapped)
        }
        return listUser
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getYayUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                userAdapter.setUserList(list)
            }
        }
    }
}