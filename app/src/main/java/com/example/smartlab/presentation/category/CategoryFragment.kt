package com.example.smartlab.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentCategoryBinding
import com.example.smartlab.databinding.FragmentDialogProductBinding
import com.example.smartlab.presentation.models.ProductModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class CategoryFragment(
    private val position: Int,
    private val categoryList: List<String>,
    private val products: List<ProductModel>,
    private val appBar: ConstraintLayout
) :
    Fragment() {

    private lateinit var dialog: BottomSheetDialog
    lateinit var binding: FragmentCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)


        val productsAdapter = ProductsAdapter(resources, appBar)
        binding.recyclerProducts.adapter = productsAdapter

        productsAdapter.apply {
            onClick = { model ->
                createDialog(model)
            }
        }

        val productCategoryList = mutableListOf<ProductModel>()
        for (product in products) {
            if (product.category == categoryList[position])
                productCategoryList.add(product)
        }
        productsAdapter.submitList(productCategoryList)
        productCategoryList.clear()

        return binding.root
    }

    private fun createDialog(product: ProductModel) {
        val binding = FragmentDialogProductBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(binding.root)

        binding.title.text = product.name
        binding.description.text = product.description
        binding.preparation.text = product.preparation
        binding.resultTime.text = product.time_result
        binding.biomaterial.text = product.bio
        binding.buttonAdd.text =
            resources.getString(R.string.add_for) + " " + product.price + " " + resources.getString(
                R.string.ruble
            )
        binding.close.setOnClickListener{
            dialog.cancel()
        }

        binding.buttonAdd.setOnClickListener{
            SharedPrefUserStorage(requireContext()).addProduct(product)
        }
        dialog.show()
    }
}