package com.example.lookin

import androidx.activity.ComponentActivity
import android.content.Intent
import android.os.Bundle
import android.view.View



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openSearchPage(view: View) {
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }

    fun openCommunityPage(view: View) {
        val intent = Intent(this, CommunityActivity::class.java)
        startActivity(intent)
    }
}

class SearchActivity

class CommunityActivity