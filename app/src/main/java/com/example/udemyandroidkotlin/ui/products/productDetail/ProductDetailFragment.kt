package com.example.udemyandroidkotlin.ui.products.productDetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.ui.user.UserActivity
import com.example.udemyandroidkotlin.utility.GlobalApp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_detail_fragment.view.*

class ProductDetailFragment : Fragment() {
    val arg: ProductDetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = ProductDetailFragment()
    }

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        var root = inflater.inflate(R.layout.product_detail_fragment, container, false)


        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)
        var p: (Product?) -> Unit = {

            if (it != null) {

                root.txt_product_name.text = it.Name
                root.txt_product_color.text = it.Color
                root.txt_product_price.text = it.Price.toString()
                root.txt_product_stock.text = it.Stock.toString()
                root.txt_product_category_name.text = it.Category?.Name
                var fullPhotoUrl = "${ApiConsts.photoBaseUrl}/${it.PhotoPath}"

                Picasso.get().load(fullPhotoUrl).placeholder(R.drawable.no_image_available)
                    .error(R.drawable.no_image_available).into(root.img_product_picture)

            }

        }


        viewModel.getProduct(arg.productId).observe(viewLifecycleOwner, p)


        return root
    }


}