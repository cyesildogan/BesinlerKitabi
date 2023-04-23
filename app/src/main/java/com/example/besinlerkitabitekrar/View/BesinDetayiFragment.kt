package com.example.besinlerkitabitekrar.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide


import com.example.besinlerkitabitekrar.R
import com.example.besinlerkitabitekrar.ViewModel.BesinDetayiViewModel
import com.example.besinlerkitabitekrar.databinding.FragmentBesinDetayiBinding
import com.example.besinlerkitabitekrar.util.gorselIndir
import com.example.besinlerkitabitekrar.util.placeHolderYap
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {
    private lateinit var besinDetayiViewModel : BesinDetayiViewModel
    private var besinId = 0
    private lateinit var dataBinding : FragmentBesinDetayiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId

        }
        besinDetayiViewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        besinDetayiViewModel.roomVerisiniAl(besinId)
        observeLiveData()

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData(){
        besinDetayiViewModel.besinler.observe(viewLifecycleOwner, Observer {
            it?.let {

            dataBinding.besinDetails = it
            /*
                besinIsim.text = it.besinIsim
                besinKalori.text = it.besinKalori
                besinYag.text = it.besinYag
                besinProtein.text = it.besinProtein
                besinKarbonhidrat.text = it.besinKarbonhidrat
                context?.let {c->
                    besinImageview.gorselIndir(it.besinGorsel, placeHolderYap(c))
                }
*/

            }
        })
    }

}