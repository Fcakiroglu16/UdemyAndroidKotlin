package com.example.udemyandroidkotlin.ui.products.productAdd

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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.udemyandroidkotlin.R
import com.example.udemyandroidkotlin.models.Category
import com.example.udemyandroidkotlin.models.Product
import com.example.udemyandroidkotlin.ui.user.UserActivity
import com.example.udemyandroidkotlin.utility.GlobalApp
import kotlinx.android.synthetic.main.product_add_fragment.*
import kotlinx.android.synthetic.main.product_add_fragment.view.*

class ProductAddFragment : Fragment() {

    companion object {
        fun newInstance() = ProductAddFragment()

        const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_PERMISSION = 200
    }

    private lateinit var viewModel: ProductAddViewModel
    private lateinit var root: View
    private var fileURI: Uri? = null
    private fun showGallery() {

        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)


        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {

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

            REQUEST_CODE_PERMISSION -> {

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
        viewModel = ViewModelProvider(this).get(ProductAddViewModel::class.java)
        root = inflater.inflate(R.layout.product_add_fragment, container, false)


        UserActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        UserActivity.setErrorStatus(viewModel, viewLifecycleOwner)

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

            viewModel.addProduct(product, fileURI).observe(viewLifecycleOwner, {


                if (it != null) {
                    Toast.makeText(activity, "Ürün Kaydedildi", Toast.LENGTH_LONG).show()

                    root.findNavController().navigate(R.id.productListFragmentNav)
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
                    REQUEST_CODE_PERMISSION
                )

            } else {
                showGallery()
            }


        }


        return root;
    }


}