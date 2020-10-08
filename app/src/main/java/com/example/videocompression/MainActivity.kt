package com.example.videocompression

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: CompressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProvider(this,CompressViewModelFactory(application)).get(CompressViewModel::class.java)




    }
}