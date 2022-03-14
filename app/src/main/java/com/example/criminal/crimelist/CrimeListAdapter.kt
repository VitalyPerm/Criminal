package com.example.criminal.crimelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.criminal.R
import com.example.criminal.crime.getDate
import com.example.criminal.databinding.ListItemCrimeBinding
import com.example.criminal.db.Crime
import java.util.*

class CrimeListAdapter(private val onClick: (UUID) -> Unit) :
    ListAdapter<Crime, CrimeListAdapter.CrimeListViewHolder>(CrimeListDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeListViewHolder =
        CrimeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
        )

    override fun onBindViewHolder(holder: CrimeListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.run {
            crimeTitle.text = item.title
            crimeDate.text = getDate(item.date)
            crimeSolved.isVisible = item.isSolved
            cl.setOnClickListener { onClick.invoke(item.id) }
        }
    }

    inner class CrimeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding by viewBinding(ListItemCrimeBinding::bind)
    }
}

class CrimeListDiffUtil : DiffUtil.ItemCallback<Crime>() {

    override fun areItemsTheSame(oldItem: Crime, newItem: Crime) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime) =
        oldItem == newItem
}
