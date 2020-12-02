package com.example.udemyandroidkotlin.ui.products.productUpdate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.udemyandroidkotlin.R

class ProductUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = ProductUpdateFragment()
    }

    private lateinit var viewModel: ProductUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}