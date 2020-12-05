package com.example.udemyandroidkotlin.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udemyandroidkotlin.R
import kotlinx.android.synthetic.main.recyclerview_product_item.view.*

class HolderProduct(view: View) : RecyclerView.ViewHolder(view) {


    var txtName = view.findViewById<TextView>(R.id.txt_recyclerview_product_name)
    var txtPrice = view.findViewById<TextView>(R.id.txt_recylerview_product_price)
    var txtProductCategory = view.findViewById<TextView>(R.id.txt_recylerview_product_category_name)
    var imageProduct= view.findViewById<ImageView>(R.id.img_recyclerview_product_photo)
}