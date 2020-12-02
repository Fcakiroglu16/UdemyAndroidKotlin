package com.example.udemyandroidkotlin.ui.products.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.utility.GlobalApp
import kotlinx.android.synthetic.main.product_add_fragment.view.*

class ProductAddFragment : Fragment() {

    companion object {
        fun newInstance() = ProductAddFragment()
    }

    private lateinit var viewModel: ProductAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProductAddViewModel::class.java)
        var root = inflater.inflate(R.layout.product_add_fragment, container, false)


        viewModel.getCategories().observe(viewLifecycleOwner, {

            ArrayAdapter<Category>(
                GlobalApp.getAppContext(),
                android.R.layout.simple_spinner_item,
                it
            ).also {categoryAdapter->

                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                root.spinner_add_fragment_categories.adapter = categoryAdapter

            }


        })





        return root;
    }


}