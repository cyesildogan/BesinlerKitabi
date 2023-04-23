package com.example.besinlerkitabitekrar.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinlerkitabitekrar.Adapter.BesinListesiRecyclerAdapter
import com.example.besinlerkitabitekrar.R
import com.example.besinlerkitabitekrar.ViewModel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {

    private lateinit var besinListesiViewModel : BesinListesiViewModel
    private val recyclerBesinAdapter = BesinListesiRecyclerAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        besinListesiViewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        besinListesiViewModel.refreshData()

        besinListesiRecycler.layoutManager = LinearLayoutManager(context)
        besinListesiRecycler.adapter = recyclerBesinAdapter
        observeLiveData()
        swipeRefreshLayout.setOnRefreshListener {
            besinYukleniyor.visibility = View.VISIBLE
            hataMesaji.visibility = View.GONE
            besinListesiRecycler.visibility = View.GONE
            besinListesiViewModel.refreshDataSwipeLayout()
            swipeRefreshLayout.isRefreshing = false

        }


        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData(){

        besinListesiViewModel.besinler.observe(viewLifecycleOwner, Observer {
            it?.let {
                besinListesiRecycler.visibility = View.VISIBLE
                besinYukleniyor.visibility = View.GONE
                recyclerBesinAdapter.besinListesiniGuncelle(it)
            }
        })

        besinListesiViewModel.besinlerProgressBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    besinListesiRecycler.visibility = View.GONE
                    hataMesaji.visibility = View.GONE
                    besinYukleniyor.visibility=View.VISIBLE

                }
            }
        })
        besinListesiViewModel.besinlerHataMesaji.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    hataMesaji.visibility = View.VISIBLE
                    besinListesiRecycler.visibility = View.GONE
                    besinYukleniyor.visibility = View.GONE
                }else{
                    hataMesaji.visibility = View.GONE
                }
            }
        })

    }

}