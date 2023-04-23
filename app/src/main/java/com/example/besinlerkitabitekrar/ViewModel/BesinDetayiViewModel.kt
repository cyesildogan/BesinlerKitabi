package com.example.besinlerkitabitekrar.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.besinlerkitabitekrar.model.Besin
import com.example.besinlerkitabitekrar.Servis.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
        launch {
            val dao = BesinDatabase(getApplication()).besinDAO()
            val besin = dao.getBesin(uuid)
            besinler.value = besin
        }


    }
}