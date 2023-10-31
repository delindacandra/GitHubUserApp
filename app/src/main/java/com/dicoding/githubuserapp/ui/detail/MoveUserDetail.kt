package com.dicoding.githubuserapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.remote.response.DetailResponse
import com.dicoding.githubuserapp.databinding.ActivityMoveUserDetailBinding
import com.dicoding.githubuserapp.ui.ViewModelFactory
import com.dicoding.githubuserapp.ui.fragment.SectionsPagerAdapter
import com.dicoding.githubuserapp.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MoveUserDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMoveUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoveUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(data)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.appName = name.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val detailViewModel: DetailViewModel by viewModels { factory }
        if(name != null ) {
            detailViewModel.fetchUserData(name.toString())
        }
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        detailViewModel.userData.observe(this){userData->
            setDetailUser(userData)
        }

        detailViewModel.getFav(name!!).observe(this){ favorite ->
            val ivFavorite = binding.ivFavorite
            if(favorite == null){
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.star))
            }else{
                ivFavorite.setImageDrawable(ContextCompat.getDrawable(ivFavorite.context, R.drawable.baseline_star_24))
            }
            binding.ivFavorite.setOnClickListener {
                if (favorite == null){
                    detailViewModel.saveFav(detailViewModel.userData.value?.login.toString(), detailViewModel.userData.value?.avatarUrl.toString())
                }else{
                    detailViewModel.deleteFav(detailViewModel.userData.value?.login.toString(), detailViewModel.userData.value?.avatarUrl.toString())
                }
            }
        }

        binding.lnDetailUser.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    @SuppressLint("SetTextI18n")
    private fun setDetailUser(userData: DetailResponse){
        binding.nameUser.text = userData.login
        binding.username.text = userData.name
        binding.followers.text = "Followers: ${userData.followers}"
        binding.following.text = "Following: ${userData.following}"
        Glide.with(this)
            .load(userData.avatarUrl)
            .into(binding.imgUser)
    }
    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val data = "username"
    }

}

