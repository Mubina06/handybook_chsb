package com.example.handybookchsb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.handybookchsb.R
import com.example.handybookchsb.model.Book

class Kitob3Adapter( var kitob3List:MutableList<Book>) : RecyclerView.Adapter<Kitob3Adapter.CartHolder>(){

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.kitob3_name)
        var author: TextView = itemView.findViewById(R.id.kitob3_author)
        var imageView: ImageView =itemView.findViewById(R.id.kitob3_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CartHolder {
        return CartHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_reading, parent, false))
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        var kitob = kitob3List[position]
        holder.name.text = kitob.name
        holder.author.text=kitob.author
        holder.imageView.load(kitob.image)

    }

    override fun getItemCount(): Int {
        return kitob3List.size
    }


}