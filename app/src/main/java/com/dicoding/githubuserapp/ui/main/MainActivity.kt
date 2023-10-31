package com.dicoding.githubuserapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubuserapp.data.remote.response.ItemsItem
import com.dicoding.githubuserapp.databinding.ActivityMainBinding
import com.dicoding.githubuserapp.ui.settings.ThemeSetting
import com.dicoding.githubuserapp.ui.ViewModelFactory
import com.dicoding.githubuserapp.ui.favorite.FavoriteActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private lateinit var oriItems: List<ItemsItem>
    private  var filterSearch: List<ItemsItem> = emptyList()

    private fun setDataUsers(items: List<ItemsItem>){
        filterSearch = items
        val adapter = ListUserAdapter()
        adapter.submitList(filterSearch)
        binding.recyclerView.adapter = adapter
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        oriItems = emptyList()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val mainViewModel: MainViewModel by viewModels { factory }

        mainViewModel.items.observe(this) { items ->
            setDataUsers(items)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.isEmpty.observe(this){
            binding.tvEmpty.isVisible = it
        }
        mainViewModel.getTheme().observe(this){isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.text = searchView.text
                    mainViewModel.searchUsers(searchView.text.toString())
                    searchView.hide()
                    false
                }
        }

        binding.favList.setOnClickListener{
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.nightMode.setOnClickListener {
            val intent = Intent(this, ThemeSetting::class.java)
            startActivity(intent)
        }


    }


    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }


}


