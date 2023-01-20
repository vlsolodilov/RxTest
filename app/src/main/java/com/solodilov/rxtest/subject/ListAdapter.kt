package com.solodilov.rxtest.subject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solodilov.rxtest.databinding.ItemListBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListHolder>() {

    private val clickSubject = PublishSubject.create<String>()
    val clickEvent: Observable<String> = clickSubject

    private val items: List<String> = (0..10).map { "Item $it" }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder =
        ListHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            ))

    inner class ListHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {

            binding.itemText.text = text

            itemView.setOnClickListener {
                clickSubject.onNext(items[layoutPosition])
            }
        }

    }
}