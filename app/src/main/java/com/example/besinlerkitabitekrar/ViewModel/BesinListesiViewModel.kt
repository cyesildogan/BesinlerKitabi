package com.example.besinlerkitabitekrar.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData


import com.example.besinlerkitabitekrar.model.Besin
import com.example.besinlerkitabitekrar.Servis.BesinAPIServis
import com.example.besinlerkitabitekrar.Servis.BesinDatabase
import com.example.besinlerkitabitekrar.util.OzelSharedPreferences


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinlerHataMesaji = MutableLiveData<Boolean>()
    val besinlerProgressBar = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10*60*1000*1000*1000L
    private val ozelSharedPreference = OzelSharedPreferences(getApplication())
    private val besinApiServis = BesinAPIServis()
    private val disposable =CompositeDisposable()

    fun refreshData(){

        val kaydedilmezamani = ozelSharedPreference.zamaniAl()
        if (kaydedilmezamani!=null && kaydedilmezamani<0 && System.nanoTime() - kaydedilmezamani < guncellemeZamani){
            getDataFromSQL()
        }else{
            getDataFromInternet()
        }

    }
    fun refreshDataSwipeLayout(){
        getDataFromInternet()
    }

    fun getDataFromInternet(){

        besinlerProgressBar.value = true

        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Internetten",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        besinlerHataMesaji.value = true
                        besinlerProgressBar.value = false
                        e.printStackTrace()
                    }

                }

        ))


    }
    private fun besinleriGoster(t: List<Besin>){
        besinler.value = t
        besinlerHataMesaji.value = false
        besinlerProgressBar.value = false
    }
    private fun sqliteSakla( t : List<Besin>){
        launch {

            val dao = BesinDatabase(getApplication()).besinDAO()
            dao.deleteAllBesin()
            val uuidList=dao.instertAll(*t.toTypedArray())
            var i = 0
            while (i<t.size){
                t[i].uuid = uuidList[i].toInt()
                i = i+1
            }
            besinleriGoster(t)
        }
        ozelSharedPreference.zamaniKaydet(System.nanoTime())
    }

    private fun getDataFromSQL(){
        besinlerProgressBar.value = true
        launch {

            val besinList = BesinDatabase(getApplication()).besinDAO().getAllBesin()
            besinleriGoster(besinList)
            Toast.makeText(getApplication(),"ROOMDAN",Toast.LENGTH_LONG).show()

        }
    }
}