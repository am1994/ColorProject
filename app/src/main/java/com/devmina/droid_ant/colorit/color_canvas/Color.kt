package com.devmina.droid_ant.colorit.color_canvas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.colorit.R
import com.devmina.droid_ant.colorit.databinding.ActivityColorBinding
import com.devmina.droid_ant.colorit.image.IMAGE_ID
import com.devmina.droid_ant.colorit.image.Image
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import kotlinx.android.synthetic.main.activity_color.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


private const val PERMISSION_REQUEST = 101


class Color : AppCompatActivity() , View.OnClickListener{


    // Obtain ViewModel from ViewModelProviders
    private val viewModel by lazy { ViewModelProviders.of(this).get(ColorViewModel::class.java) }


    private var currentBackgroundColor = Color.RED




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val myCanvasView=MyCanvasView(this )
        // myCanvasView.systemUiVisibility= View.SYSTEM_UI_FLAG_FULLSCREEN
        // myCanvasView.contentDescription =getString(R.string.canvasConetentDescription)
        val binding: ActivityColorBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_color)

        binding.lifecycleOwner = this
        binding.color = viewModel


        binding.clear.setOnClickListener {
            myCanvasView.clearDrawing()

        }



        val yellow = binding.yellow
        val red = binding.red
        val green = binding.green
        val purple = binding.purple
        val viol = binding.viol
        val orange = binding.orange
        val blue = binding.blue

        yellow.setOnClickListener(this)
        red.setOnClickListener(this)
        green.setOnClickListener(this)
        purple.setOnClickListener(this)
        viol.setOnClickListener(this)
        orange.setOnClickListener(this)
        blue.setOnClickListener(this)




        binding.img.setOnClickListener {
            val nextScreenIntent = Intent(this, Image::class.java)
            startActivity(nextScreenIntent)
        }




        binding.parentButton.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this)
                .setTitle(R.string.color_dialog_title)
                .initialColor(currentBackgroundColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("ok") { dialog, selectedColor, allColors ->
                    myCanvasView.changeDrawColor(selectedColor)
                    binding.parentButton.setColor(selectedColor)
                }

                .setNegativeButton(
                    "cancel"
                ) { dialog, which -> dialog.dismiss() }
                .build()
                .show()
        }


        val id = intent.getIntExtra(IMAGE_ID, R.drawable.dol)
        myCanvasView.changeImages(id)
        //binding.drawImg.setImageResource(id)
        binding.img.setImageResource(id)

        binding.seekBar.setOnProgressChangeListener { progressValue ->
            myCanvasView.changeBrushSize(progressValue)
        }

        binding.save.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                Log.i("color", "Permission to write external storage  denied")
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST)
            }else{

            }

        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST) {

                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                    save(myCanvasView.getBitmap())


                    Log.i("color", "Permission has been granted by user")

                } else {
                    Log.i("color", "Permission has been denied by user")

                }


        }
    }

       /* Checks if external storage is available for read and write */
     fun isExternalStorageWritable():Boolean {
        val state :String = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true
        }
        return false
    }
    //TODO save images

    // Method to save an image to external storage
    private fun save(bitmap:Bitmap) {
        if (isExternalStorageWritable()) {
             val root:String  = Environment.getExternalStorageDirectory().toString()
        val myDir:File  =  File(root + "/saved_images");
        myDir.mkdirs()

        val timeStamp:String  =  SimpleDateFormat("yyyyMMdd_HHmmss").format( Date());
        val fname:String  = "Shutta_"+ timeStamp +".jpg";

        val file:File  =  File(myDir, fname)
        if (file.exists()) file.delete ()
        try {
            val out  =  FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (e:Exception ) {
            e.printStackTrace();
        }


        }else{
            //prompt the user or do something
        }

        /*  val file : String = UUID.randomUUID().toString()+".png"

        // Get the external storage directory path
        val folder :File = File(Environment.getExternalStorageDirectory().toString()+File.separator+Environment.DIRECTORY_PICTURES+File.separator+getString(R.string.app_name))

        if(!folder.exists()){
            folder.mkdirs()
        }
        // Create a file to save the image


            try {
                // Get the file output stream
                val stream: OutputStream = FileOutputStream(folder.toString()+File.separator+file)

                // Compress the bitmap
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

                // Flush the output stream
                stream.flush()

                // Close the output stream
                stream.close()
                Toast.makeText(this, "Image saved successful.", Toast.LENGTH_LONG).show()
            } catch (e: IOException) { // Catch the exception
                e.printStackTrace()
                Toast.makeText(this, "Error to save image.", Toast.LENGTH_LONG).show()

            }*/


    }
    override fun onClick(pencil: View) {

        when(pencil.id){
            yellow.id ->  myCanvasView.changeDrawColor(Color.YELLOW)
            red.id ->  myCanvasView.changeDrawColor(Color.RED)
            green.id ->  myCanvasView.changeDrawColor(Color.rgb(0,128,0))
            purple.id ->  myCanvasView.changeDrawColor(Color.MAGENTA)
            viol.id ->  myCanvasView.changeDrawColor(Color.rgb(138,43,226))
            orange.id ->  myCanvasView.changeDrawColor(Color.rgb(255,165,0))
            blue.id ->  myCanvasView.changeDrawColor(Color.rgb(0,0,255))


        }
    }
}
