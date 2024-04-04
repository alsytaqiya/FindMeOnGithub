package com.dicoding.picodiploma.findmeongithub.userInterface.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.picodiploma.findmeongithub.dataApp.model.UserModel
import com.dicoding.picodiploma.findmeongithub.databinding.ItemRowUserBinding

class AdapterOfUser : RecyclerView.Adapter<AdapterOfUser.ViewHolderOfUser>() {

    private val userList = ArrayList<UserModel>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setUserList(githubUsers: ArrayList<UserModel>) {
        userList.clear()
        userList.addAll(githubUsers)
        notifyDataSetChanged()
    }

    inner class ViewHolderOfUser(private val bindingItems: ItemRowUserBinding) :
        RecyclerView.ViewHolder(bindingItems.root) {
        fun bind(item: UserModel) {
            bindingItems.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)
            }
            bindingItems.apply {
                imgItemPhoto.load(item.avatar_url) {
                    transformations(CircleCropTransformation())
                }
                tvUsername.text = item.login
                tvId.text = item.id.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOfUser {
        val viewUser =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderOfUser(viewUser)
    }

    override fun onBindViewHolder(holder: ViewHolderOfUser, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserModel)
    }

}