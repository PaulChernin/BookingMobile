package com.paulchernin.booking.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.paulchernin.booking.R
import com.paulchernin.booking.data.Room
import com.squareup.picasso.Picasso

class RoomsListAdapter(
    private val context: Context,
    private val roomsList: Array<Room>,
    val onClick: (Room) -> Unit
) : BaseAdapter() {

    override fun getCount() = roomsList.size

    override fun getItem(position: Int): Any {
        return roomsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, _view: View?, parent: ViewGroup?): View {
        val room = roomsList[position]

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = _view ?: inflater.inflate(R.layout.fragment_rooms_list_item, parent, false)

        val nameView = view.findViewById<TextView>(R.id.name)
        nameView.text = room.name

        val priceView = view.findViewById<TextView>(R.id.price)
        priceView.text = room.price.toString() + " за ночь"

        val imageView = view.findViewById<ImageView>(R.id.image)
        Picasso.get().load(room.picture).fit().centerCrop().into(imageView)

        view.setOnClickListener { onClick(room) }

        return view
    }

}