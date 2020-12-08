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
import com.example.udemyandroidkotlin.utility.GlobalApp

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
        Toast.makeText(GlobalApp.getAppContext(), arg.productId.toString(), Toast.LENGTH_LONG)
            .show()

        return root
    }


}