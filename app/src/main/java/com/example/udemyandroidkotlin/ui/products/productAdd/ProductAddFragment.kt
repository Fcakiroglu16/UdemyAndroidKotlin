package com.example.udemyandroidkotlin.ui.products.productAdd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.utility.GlobalApp
import kotlinx.android.synthetic.main.product_add_fragment.*
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
            ).also { categoryAdapter ->

                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                root.spinner_add_fragment_categories.adapter = categoryAdapter

            }


        })



        root.btn_product_save.setOnClickListener {
            //validation
            var productName = root.txt_add_fragment_product_name.editText?.text.toString()
            var productPrice = root.txt_add_fragment_product_price.editText?.text.toString()
            var productStock = root.txt_add_fragment_product_stock.editText?.text.toString()
            var productColor = root.txt_add_fragment_product_color.editText?.text.toString()

            var category = spinner_add_fragment_categories.selectedItem as Category

            var product = Product(
                0,
                productName,
                productPrice.toDouble(),
                productColor,
                productStock.toInt(),
                "",
                category.Id, null
            )

            viewModel.addProduct(product, null).observe(viewLifecycleOwner, {


                if (it != null) {
                    Toast.makeText(activity, "Ürün Kaydedildi", Toast.LENGTH_LONG).show()
                }


            })


        }



        return root;
    }


}