package ru.students.nasaapipics.ui.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.students.nasaapipics.R

class RecyclerViewAdapter(private val data: MutableList<Notes>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater.inflate(R.layout.activity_recycler_item_note, parent, false))
//        return if (viewType == TYPE_EARTH) {
//            EarthViewHolder(
//                inflater.inflate(R.layout.activity_recycler_item_earth, parent, false) as View
//            )
//        } else {
//            MarsViewHolder(
//                inflater.inflate(R.layout.activity_recycler_item_mars, parent, false) as View
//            )
//        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NoteViewHolder
        holder.bind(data[position])
//        if (getItemViewType(position) == TYPE_EARTH) {
//            holder as EarthViewHolder
//            holder.bind(data[position])
//        } else {
//            holder as MarsViewHolder
//            holder.bind(data[position])
//        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (data[position].someDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH
//    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder  {

        fun bind(data: Notes) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.findViewById<TextView>(R.id.title).text = data.header
                itemView.findViewById<TextView>(R.id.body).text = data.text
                //itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(notes: Notes)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

//    companion object {
//        private const val TYPE_EARTH = 0
//        private const val TYPE_MARS = 1
//    }
}