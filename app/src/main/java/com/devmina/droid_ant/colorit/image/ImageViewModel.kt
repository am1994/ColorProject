package com.devmina.droid_ant.colorit.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmina.droid_ant.colorit.R
import com.devmina.droid_ant.colorit.model.ListofImages

class ImageViewModel:ViewModel(){

    private var _listImage= MutableLiveData<MutableList<ListofImages>>()
    val listImage: LiveData<MutableList<ListofImages>>
        get() = _listImage

    private var _chosenImage = MutableLiveData<ListofImages>()
    val chosenImage: LiveData<ListofImages>
        get() = _chosenImage








    init{
        insert()
    }





    private fun insert() {
        val images: MutableList<ListofImages> = mutableListOf()
        images.add(ListofImages(R.drawable.dol))
        images.add(ListofImages(R.drawable.dolphin))
        images.add(ListofImages(R.drawable.horse))
        images.add(ListofImages(R.drawable.umber))
        images.add(ListofImages(R.drawable.adventure))
        images.add(ListofImages(R.drawable.adventure_2))
        images.add(ListofImages(R.drawable.adventure_3))
        images.add(ListofImages(R.drawable.bears))
        images.add(ListofImages(R.drawable.bears_1))
        images.add(ListofImages(R.drawable.gumball))
        images.add(ListofImages(R.drawable.gumball_2))
        images.add(ListofImages(R.drawable.ice_bear))
        images.add(ListofImages(R.drawable.been_ten))
        images.add(ListofImages(R.drawable.gumball_1))
        images.add(ListofImages(R.drawable.gumball_3))
        images.add(ListofImages(R.drawable.gumball_4))
        images.add(ListofImages(R.drawable.been_ten_5))





        _listImage.postValue(images)
    }








    fun changeImage(image: ListofImages){
        _chosenImage.postValue(image)
    }
    fun completedChangeImage(){
        _chosenImage.postValue(null)
    }
}