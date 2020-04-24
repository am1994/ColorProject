package com.devmina.droid_ant.colorit.main_activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.colorit.R
import com.devmina.droid_ant.colorit.databinding.ActivityMainBinding
import com.devmina.droid_ant.colorit.image.Image
import com.google.android.gms.ads.*
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
import java.util.*


class MainActivity : AppCompatActivity() {

    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding : ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        viewModel.click.observe(this, Observer {
            if(it){


                    val intent =  Intent(this, Image::class.java)
                    startActivity(intent)

                    viewModel.CompleteClick()




            }
        })


        binding.mainViewmodel=viewModel




    }



}
