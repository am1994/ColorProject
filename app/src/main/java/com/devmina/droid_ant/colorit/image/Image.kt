package com.devmina.droid_ant.colorit.image

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.devmina.droid_ant.colorit.R
import com.devmina.droid_ant.colorit.color_canvas.Color
import com.devmina.droid_ant.colorit.color_canvas.ImageAdapter
import com.devmina.droid_ant.colorit.databinding.ActivityImageBinding
const val  IMAGE_ID = "ID"

class Image : AppCompatActivity() {
    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy { ViewModelProviders.of(this).get(ImageViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val myCanvasView=MyCanvasView(this )
        // myCanvasView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        // myCanvasView.contentDescription =getString(R.string.canvasConetentDescription)
        val binding: ActivityImageBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_image)

        binding.lifecycleOwner = this
        binding.image = viewModel

        val imageAdapter=
            ImageAdapter(ImageAdapter.onClickListener {
                viewModel.changeImage(it) })

       viewModel.chosenImage.observe(this, Observer {
            if(it != null){
                val nextScreenIntent = Intent(this, Color::class.java).apply {
                    putExtra(IMAGE_ID, it.image)
                }

                startActivity(nextScreenIntent)

                viewModel.completedChangeImage()
            }
        })


        val manager = GridLayoutManager(applicationContext, 3)
        binding.imageRecycler.layoutManager = manager
        binding.imageRecycler.adapter = imageAdapter


    }
}