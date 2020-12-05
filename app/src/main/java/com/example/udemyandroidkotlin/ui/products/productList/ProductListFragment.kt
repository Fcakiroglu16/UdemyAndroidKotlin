package com.example.udemyandroidkotlin.ui.products.productList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.adapters.ProductListRecyclerAdapter
import com.example.udemyandroidkotlin.utility.GlobalApp
import kotlinx.android.synthetic.main.product_list_fragment.view.*

class ProductListFragment : Fragment() {


    lateinit var linearLayoutManager: LinearLayoutManager
    var productListRecyclerAdapter: ProductListRecyclerAdapter? = null

    var page: Int = 0
    var isLoading = false
    var isLastPage = false
    private lateinit var viewModel: ProductListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.product_list_fragment, container, false)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

        root.btn_product_add.setOnClickListener {

            it.findNavController().navigate(R.id.productAddFragmentNav)

        }

        linearLayoutManager = LinearLayoutManager(GlobalApp.getAppContext())

        root.recycler_view_products.layoutManager = linearLayoutManager

        if (page == 0) {
            viewModel.getProducts(page)
        } else {
            root.recycler_view_products.adapter = productListRecyclerAdapter
        }


        viewModel.products.observe(viewLifecycleOwner, {

            if (it.size == 0 && page != 0) {
                productListRecyclerAdapter?.removeLoading()
                isLoading = false
                isLastPage = true
            } else {
                if (page == 0) {
                    root.recycler_view_products.apply {

                        productListRecyclerAdapter = ProductListRecyclerAdapter(it) { product ->
                            // Recyclerview içerisindeki bir item tıklandığından burası çalışacak


                        }

                        adapter = productListRecyclerAdapter

                    }


                }

                if (page != 0) {
                    productListRecyclerAdapter?.removeLoading()

                    isLoading = false

                    var isExist = productListRecyclerAdapter!!.products.contains(it[0])

                    if (!isExist) productListRecyclerAdapter!!.addProduct(it)


                }


            }


        })


        return root


    }


}