package com.example.besinlerkitabitekrar.Servis

import com.example.besinlerkitabitekrar.model.Besin
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//rxjava kullandığımız için yoksa gerek yuk
        .build()
        .create(BesinAPI::class.java)

    fun getData() : Single<List<Besin>>{
        return api.getBesin()
    }

}