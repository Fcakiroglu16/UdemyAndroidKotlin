package com.example.udemyandroidkotlin.ui.products.productUpdate

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.consts.ApiConsts
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.ui.products.productAdd.ProductAddFragment
import com.example.udemyandroidkotlin.ui.user.UserActivity
import com.example.udemyandroidkotlin.utility.GlobalApp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_add_fragment.*
import kotlinx.android.synthetic.main.product_add_fragment.view.*
import kotlinx.android.synthetic.main.product_detail_fragment.view.*
import kotlinx.android.synthetic.main.product_update_fragment.view.*
import kotlinx.android.synthetic.main.product_update_fragment.view.image_view_product_pick
import kotlinx.android.synthetic.main.product_update_fragment.view.spinner_add_fragment_categories
import kotlinx.android.synthetic.main.product_update_fragment.view.txt_add_fragment_product_color
import kotlinx.android.synthetic.main.product_update_fragment.view.txt_add_fragment_product_name
import kotlinx.android.synthetic.main.product_update_fragment.view.txt_add_fragment_product_price
import kotlinx.android.synthetic.main.product_update_fragment.view.txt_add_fragment_product_stock

class ProductUpdateFragment : Fragment() {
    private val args: ProductUpdateFragmentArgs by navArgs()
    private var fileURI: Uri? = null
    private lateinit var root: View

    companion object {
        fun newInstance() = ProductUpdateFragment()
        const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    private lateinit var viewModel: ProductUpdateViewModel


    private fun showGallery() {

        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, ProductAddFragment.REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)


        if (requestCode == ProductAddFragment.REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            fileURI = intent!!.data!!
            root.image_view_product_pick.setImageURI(fileURI)


        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            ProductAddFragment.REQUEST_CODE_PERMISSION -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showGallery()
                }


            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProductUpdateViewModel::class.java)
        root = inflater.inflate(R.layout.product_update_fragment, container, false)

        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        var photoUrl = "${ApiConsts.photoBaseUrl}/${args.product.PhotoPath}"

        Picasso.get().load(photoUrl).placeholder(R.drawable.no_image_available)
            .error(R.drawable.no_image_available).into(
                root.image_view_product_pick
            )



        root.txt_add_fragment_product_name.editText?.setText(args.product.Name)
        root.txt_add_fragment_product_color.editText?.setText(args.product.Color)
        root.txt_add_fragment_product_stock.editText?.setText(args.product.Stock.toString())
        root.txt_add_fragment_product_price.editText?.setText(args.product.Price.toString())



        viewModel.getCategories().observe(viewLifecycleOwner, {

            ArrayAdapter<Category>(
                GlobalApp.getAppContext(),
                android.R.layout.simple_spinner_item,
                it
            ).also { categoryAdapter ->

                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                root.spinner_add_fragment_categories.adapter = categoryAdapter


                var selectedPosition = it.map { it.Id }.indexOf(args.product.CategoryId)

                root.spinner_add_fragment_categories.setSelection(selectedPosition)


            }


        })


        root.btn_product_update2.setOnClickListener {

            var productName = root.txt_add_fragment_product_name.editText?.text.toString()
            var productPrice = root.txt_add_fragment_product_price.editText?.text.toString()
            var productStock = root.txt_add_fragment_product_stock.editText?.text.toString()
            var productColor = root.txt_add_fragment_product_color.editText?.text.toString()

            var category = spinner_add_fragment_categories.selectedItem as Category


            var updateProduct = args.product

            updateProduct.Name = productName
            updateProduct.Price = productPrice.toDouble()
            updateProduct.Stock = productStock.toInt()
            updateProduct.Color = productColor
            updateProduct.CategoryId = category.Id

            viewModel.updateProduct(updateProduct, fileURI)
                .observe(viewLifecycleOwner, {


                    if (it) {
                        findNavController().navigate(R.id.productListFragmentNav)


                    }

                })

        }





        root.image_view_product_pick.setOnClickListener {


            if (ContextCompat.checkSelfPermission(
                    GlobalApp.getAppContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    ProductAddFragment.REQUEST_CODE_PERMISSION
                )

            } else {
                showGallery()
            }


        }

        return root
    }


}