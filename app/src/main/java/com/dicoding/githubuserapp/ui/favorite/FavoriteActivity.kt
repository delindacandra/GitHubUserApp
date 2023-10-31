package com.dicoding.githubuserapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.githubuserapp.ui.ViewModelFactory
import com.dicoding.githubuserapp.ui.main.ListUserAdapter
import com.dicoding.githubuserapp.ui.main.MainActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val favoriteViewModel: FavoriteViewModel by viewModels { factory }

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)

        favoriteViewModel.getFavList().observe(this){ user ->
            val items = arrayListOf<ItemsItem>()
            user.map {
                val item = ItemsItem(login=it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            val adapter = ListUserAdapter()
            adapter.submitList(items)
            binding.rvFavorite.adapter=adapter
        }

        binding.lnFavUser.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}