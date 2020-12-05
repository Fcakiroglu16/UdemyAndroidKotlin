package com.example.udemyandroidkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.holders.HolderLoading
import com.example.udemyandroidkotlin.holders.HolderProduct
import com.example.udemyandroidkotlin.models.Product
import com.squareup.picasso.Picasso


class ProductListRecyclerAdapter(
    var products: ArrayList<Product>,
    private val itemClick: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1


    override fun getItemViewType(position: Int): Int {

        return if (products[position].Id == 0) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        var inflater = LayoutInflater.from(parent.context)

        if (viewType == VIEW_TYPE_LOADING) {
            return HolderLoading(
                inflater.inflate(
                    R.layout.recyclerview_loading_item,
                    parent,
                    false
                )
            )
        } else {
            return HolderProduct(
                inflater.inflate(
                    R.layout.recyclerview_product_item,
                    parent,
                    false
                )
            )
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val product = products[position]

        if (holder is HolderProduct) {

            holder.txtName.text = product.Name
            holder.txtPrice.text = product.Price.toString()
            holder.txtProductCategory.text = product.Category?.Name


            val product_photo_url = "${ApiConsts.photoBaseUrl}/${product.PhotoPath}"

            Picasso.get().load(product_photo_url).placeholder(R.drawable.no_image_available).error(R.drawable.no_image_available).into(holder.imageProduct)


        }


    }

    override fun getItemCount(): Int {
        return products.size
    }


    fun addLoading() {
        var loadingProduct = Product(0, "", 0.0, "", 0, "", 0, null)

        products.add(loadingProduct)
        notifyDataSetChanged()


    }

    fun removeLoading() {


        var position = products.size - 1

        products.removeAt(position)
        notifyDataSetChanged()
    }


    fun addProduct(newProducts: ArrayList<Product>) {
        products.addAll(newProducts)
        notifyDataSetChanged()

    }


}