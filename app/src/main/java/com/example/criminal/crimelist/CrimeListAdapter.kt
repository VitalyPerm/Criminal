package com.example.criminal.crimelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.R
import com.example.criminal.databinding.ListItemCrimeBinding
import com.example.criminal.db.Crime
import java.util.*

class CrimeListAdapter(private val onClick: (UUID) -> Unit) :
    RecyclerView.Adapter<CrimeListAdapter.CrimeListViewHolder>() {

    private var crimes: List<Crime> = emptyList()

    fun updateUi(crimes: List<Crime>) {
        this.crimes = crimes
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListViewHolder =
        CrimeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
        )

    override fun onBindViewHolder(holder: CrimeListViewHolder, position: Int) {
        holder.binding.run {
            crimeTitle.text = crimes[position].title
            crimeDate.text = crimes[position].date.toString()
            crimeSolved.isVisible = crimes[position].isSolved
            cl.setOnClickListener { onClick.invoke(crimes[position].id) }
        }
    }

    override fun getItemCount(): Int = crimes.size


    inner class CrimeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding by viewBinding(ListItemCrimeBinding::bind)
    }
}
