package com.example.besinlerkitabitekrar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinlerkitabitekrar.model.Besin
import com.example.besinlerkitabitekrar.R
import com.example.besinlerkitabitekrar.View.BesinListesiFragmentDirections
import com.example.besinlerkitabitekrar.databinding.BesinRecyclerRowBinding
import com.example.besinlerkitabitekrar.model.BesinClickListener
import kotlinx.android.synthetic.main.besin_recycler_row.view.*


class   BesinListesiRecyclerAdapter(val besinListesi : ArrayList<Besin>) :BesinClickListener, RecyclerView.Adapter<BesinListesiRecyclerAdapter.BesinListesiVH>() {



    class BesinListesiVH(var view : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinListesiVH {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflater,R.layout.besin_recycler_row,parent,false)
        return BesinListesiVH(view)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinListesiVH, position: Int) {

        holder.view.besin = besinListesi[position]
        holder.view.listener = this


    }
    fun besinListesiniGuncelle(yeniBesinListesi : List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val uuid = view.uuidtxt.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }

    }
}
