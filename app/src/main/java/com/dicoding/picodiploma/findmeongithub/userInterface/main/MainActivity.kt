package com.dicoding.picodiploma.findmeongithub.userInterface.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.findmeongithub.R
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import com.dicoding.picodiploma.findmeongithub.databinding.ActivityMainBinding
import com.dicoding.picodiploma.findmeongithub.setting.*
import com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage.DetailOfUserActivity
import com.dicoding.picodiploma.findmeongithub.userInterface.myfavofavo.MyFavoFavoActivity


class MainActivity : AppCompatActivity() {
    private lateinit var bindingItems: ActivityMainBinding
    private lateinit var userViewModel: ViewModelOfUser
    private lateinit var userAdapter: AdapterOfUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingItems = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingItems.root)


        val pref = PreferencesOfSetting.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, FactoryOfViewModel(pref)).get(
            MainOfViewModel::class.java
        )
        mainViewModel.getSettingsOfTheme().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        userAdapter = AdapterOfUser()
        userAdapter.notifyDataSetChanged()

        userAdapter.setOnItemClickCallback(object : AdapterOfUser.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                Intent(this@MainActivity, DetailOfUserActivity::class.java).also {
                    it.putExtra(DetailOfUserActivity.EXTRA_GITHUBUSERNAME, data.login)
                    it.putExtra(DetailOfUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailOfUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelOfUser::class.java)

        bindingItems.apply {
            rvGithubusers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGithubusers.setHasFixedSize(true)
            rvGithubusers.adapter = userAdapter

            btnSearch.setOnClickListener {
                searchGitHubUser()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchGitHubUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }

        userViewModel.getUsersSearch().observe(this

        ) {
            if (it != null) {
                userAdapter.setUserList(it)
                loading(false)
            }
        }

    }

    private fun searchGitHubUser() {
        bindingItems.apply {
            val queryEntries = etQuery.text.toString()
            if (queryEntries.isEmpty()) return
            loading(true)
            userViewModel.setUsersSearch(queryEntries)
        }

    }

    private fun loading(stateOfVisibility: Boolean) {
        if (stateOfVisibility) {
            bindingItems.progressBar.visibility = View.VISIBLE
        } else {
            bindingItems.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_of_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.myfavofavo_menu -> {
                Intent(this, MyFavoFavoActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.settingIcon -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
