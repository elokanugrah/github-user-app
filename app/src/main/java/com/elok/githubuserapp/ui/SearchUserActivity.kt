package com.elok.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elok.githubuserapp.databinding.ActivitySearchUserBinding

class SearchUserActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySearchUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}