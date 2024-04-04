package com.dicoding.picodiploma.findmeongithub.userInterface.detailPackage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.findmeongithub.R
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import com.dicoding.picodiploma.findmeongithub.databinding.FragmentFollowersandfollowingBinding
import com.dicoding.picodiploma.findmeongithub.userInterface.main.AdapterOfUser

class FragmentOfFollowers : Fragment(R.layout.fragment_followersandfollowing) {

    private var _binding: FragmentFollowersandfollowingBinding? = null
    private val bindingItems get() = _binding!!
    private lateinit var viewModel: ViewModelOfFollowersAndFollowing
    private lateinit var followerAdapter: AdapterOfUser
    private lateinit var username: String

    private fun showSelectedUser(user: UserModel) {
        val moveIntent = Intent(requireContext(), DetailOfUserActivity::class.java)
        moveIntent.putExtra(DetailOfUserActivity.EXTRA_GITHUBUSERNAME, user.login)
        moveIntent.putExtra(DetailOfUserActivity.EXTRA_ID, user.id)
        moveIntent.putExtra(DetailOfUserActivity.EXTRA_URL, user.avatar_url)
        startActivity(moveIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailOfUserActivity.EXTRA_GITHUBUSERNAME).toString()
        _binding = FragmentFollowersandfollowingBinding.bind(view)

        followerAdapter = AdapterOfUser()
        followerAdapter.notifyDataSetChanged()
        bindingItems.apply {
            rvGithubusers.layoutManager = LinearLayoutManager(activity)
            rvGithubusers.setHasFixedSize(true)
            rvGithubusers.adapter = followerAdapter
        }

        followerAdapter.setOnItemClickCallback(object : AdapterOfUser.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                showSelectedUser(data)
            }
        })

        loading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ViewModelOfFollowersAndFollowing::class.java)

        viewModel.setFollower(username)
        viewModel.getFollower().observe(viewLifecycleOwner) {
            if (it != null) {
                followerAdapter.setUserList(it)
                loading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loading(stateOfVisibility: Boolean) {
        if (stateOfVisibility) {
            bindingItems.progressBar.visibility = View.VISIBLE
        } else {
            bindingItems.progressBar.visibility = View.GONE
        }
    }
}
