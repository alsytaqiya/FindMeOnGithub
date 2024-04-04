package com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.picodiploma.findmeongithub.databinding.ActivityDetailOfUserBinding
import kotlinx.coroutines.*

class DetailOfUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailOfUserBinding
    private lateinit var modelOfView: DetailOfUserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailOfUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_GITHUBUSERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(
            EXTRA_GITHUBUSERNAME,
            username
        )

        modelOfView = ViewModelProvider(this).get(DetailOfUserViewModel::class.java)
        modelOfView.setUserGithubDetail(username.toString())
        modelOfView.getUserGithubDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    imgItemPhoto.load(it.avatar_url) {
                        transformations(CircleCropTransformation())
                    }
                    tvUsername.text = it.login
                    tvFullname.text = it.name
                    tvLocation.text = StringBuilder("Location: ").append(it.location)
                    repository.text = StringBuilder("Repository: ").append(it.public_repos)
                    company.text = StringBuilder("Company: ").append(it.company)
                    followers.text = "${it.followers} Followers"
                    following.text = "${it.following} Following"
                }
            }
        }

        val adapterOfSectionPager = AdapterOfSectionPager(
            this, supportFragmentManager,
            bundle
        )
        binding.apply {
            viewPager.adapter = adapterOfSectionPager
            tablayout.setupWithViewPager(viewPager)
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
//            val countWhetherInDatabase = modelOfView.userCheck(id)
//            withContext(Dispatchers.Main) {
//                if (countWhetherInDatabase != null) {
//                    _isChecked = countWhetherInDatabase == 1  // Set true jika ada di favorit (nilai 1)
//                } else {
//                    _isChecked = countWhetherInDatabase == 0
//                }
//                binding.favToggle.isChecked = _isChecked
//            }

            val countWhetherInDatabase = modelOfView.userCheck(id)
            withContext(Dispatchers.Main) {
                if (countWhetherInDatabase != null)
                    if (countWhetherInDatabase > 0) {
                        binding.favToggle.isChecked = true
                        _isChecked = true
                    } else {
                        binding.favToggle.isChecked = false
                        _isChecked = false
                    }
            }


        }

        binding.favToggle.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                modelOfView.addToYay(username.toString(), id, avatarUrl.toString())
            } else {
                modelOfView.removeFromYay(id)
            }
            binding.favToggle.isChecked = _isChecked
        }
    }

    companion object {
        const val EXTRA_GITHUBUSERNAME = "extra_githubusername"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}