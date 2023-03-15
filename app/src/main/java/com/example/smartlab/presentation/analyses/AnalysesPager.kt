package com.example.smartlab.presentation.analyses

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartlab.presentation.category.CategoryFragment
import com.example.smartlab.presentation.models.ProductModel

class AnalysesPager(
    fragment: Fragment,
    private val categoryList: List<String>,
    private val products: List<ProductModel>,
    private val appBar: ConstraintLayout
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = categoryList.size

    override fun createFragment(position: Int): Fragment {
        return CategoryFragment(position, categoryList, products, appBar)
    }
}