package test.app.fabapplication.screens.dateList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.app.fabapplication.databinding.ListItemLayoutBinding

class ItemViewHolder(
    private val binding: ListItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        dateItem: String
    ) {
        with(binding) {
            itemTextView.text = dateItem
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewHolder =
            ItemViewHolder(
                ListItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }
}