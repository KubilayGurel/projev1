package com.kubilaygurel.artbookfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kubilaygurel.artbookfragment.R
import com.kubilaygurel.artbookfragment.databinding.RecyclerRowBinding
import com.kubilaygurel.artbookfragment.model.ArtList

class ArtListAdapter(
    private val artList: List<ArtList>,
) : RecyclerView.Adapter<ArtListAdapter.ArtlistHolder>() {

    class ArtlistHolder(val recyclerRowBinding: RecyclerRowBinding) :
        RecyclerView.ViewHolder(recyclerRowBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtlistHolder {
        val recyclerRowBinding =
            RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtlistHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    override fun onBindViewHolder(holder: ArtlistHolder, position: Int) {
        val art = artList[position]
        holder.recyclerRowBinding.RecylerViewTextView.text = art.artname

        holder.itemView.setOnClickListener{

        }
    }
}
