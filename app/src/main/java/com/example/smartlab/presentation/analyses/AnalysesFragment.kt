package com.example.smartlab.presentation.analyses

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.data.api.RetrofitInstance
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentAnalysesBinding
import com.example.smartlab.presentation.base.BaseFragment
import com.example.smartlab.presentation.models.NewsModel
import com.example.smartlab.presentation.models.ProductModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class AnalysesFragment : BaseFragment() {

    override val showBottomNavigationView = true
    lateinit var binding: FragmentAnalysesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnalysesBinding.inflate(inflater, container, false)

        if (SharedPrefUserStorage(requireContext()).getProductsList().isNotEmpty())
            binding.bottomAppBarWithButton.isVisible = true

        val searchedList = mutableListOf<ProductModel>()
        val navigation = this.findNavController()
        val newsAdapter = NewsAdapter()
        val products = mutableListOf<ProductModel>()
        binding.recyclerNews.adapter = newsAdapter
        val categoryList = mutableListOf<String>()
        val news = mutableListOf<NewsModel>()
        val twentyDP = resources.getDimensionPixelSize(R.dimen.spacing_20dp)
        val sixteenDP = resources.getDimensionPixelSize(R.dimen.spacing_16dp)
        binding.recyclerNews.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.left = twentyDP
                    outRect.right = twentyDP
                } else outRect.right = twentyDP
            }
        })


        lifecycleScope.launch {
            RetrofitInstance.retrofit.getNews().body()?.let { news.addAll(it) }
            newsAdapter.submitList(news)

            RetrofitInstance.retrofit.getProducts().body()?.let { products.addAll(it) }

            for (product in products)
                if (!categoryList.contains(product.category))
                    categoryList.add(product.category)

            val analysesPager = AnalysesPager(
                this@AnalysesFragment,
                categoryList,
                products,
                binding.bottomAppBarWithButton
            )
            binding.viewPagerProducts.adapter = analysesPager

            TabLayoutMediator(binding.tabs, binding.viewPagerProducts) { tab, position ->
                tab.text = categoryList[position]
            }.attach()

            for (i in 0 until binding.tabs.tabCount) {
                if (i == 0) {
                    val tab = (binding.tabs.getChildAt(0) as ViewGroup).getChildAt(i)
                    val margins = tab.layoutParams as MarginLayoutParams
                    margins.setMargins(
                        sixteenDP,
                        0,
                        sixteenDP,
                        0
                    )
                    tab.requestLayout()
                } else {
                    val tab = (binding.tabs.getChildAt(0) as ViewGroup).getChildAt(i)
                    val margins = tab.layoutParams as MarginLayoutParams
                    margins.setMargins(
                        0,
                        0,
                        sixteenDP,
                        0
                    )
                    tab.requestLayout()
                }
            }
            binding.cancel.setOnClickListener {
                binding.searchView.setQuery(null, true)
                binding.searchView.clearFocus()
                binding.recyclerSearch.isVisible = false
                binding.coordinatorLayout.isVisible = true
                if (SharedPrefUserStorage(requireContext()).getProductsList().isNotEmpty())
                    binding.bottomAppBarWithButton.isVisible = true
                binding.cancel.isVisible = false
            }

            val searchAdapter = SearchAdapter(resources, requireContext())
            binding.recyclerSearch.adapter = searchAdapter


            binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.recyclerSearch.isVisible = true
                    binding.coordinatorLayout.isVisible = false
                    binding.bottomAppBarWithButton.isVisible = false
                    binding.cancel.isVisible = true
                    searchAdapter.submitList(emptyList())
                } else {
                    binding.recyclerSearch.isVisible = false
                    binding.coordinatorLayout.isVisible = true
                    if (SharedPrefUserStorage(requireContext()).getProductsList().isNotEmpty())
                        binding.bottomAppBarWithButton.isVisible = true
                    binding.cancel.isVisible = false
                    searchAdapter.submitList(products)
                }
            }
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchedList.clear()
                    for (product in products) {
                        if (p0?.let { product.name.contains(it) } == true)
                            searchedList.add(product)
                    }
                    childFragmentManager.setFragmentResult("request_key", bundleOf("bundle" to p0))
                    searchAdapter.submitList(searchedList, p0)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    searchedList.clear()
                    for (product in products) {
                        if (p0?.let { product.name.contains(it) } == true)
                            searchedList.add(product)
                    }
                    searchAdapter.submitList(searchedList, p0)
                    return false
                }
            })

            binding.materialButton.setOnClickListener {
                navigation.navigate(R.id.action_analysesFragment_to_shoppingCartFragment)
            }
        }


        return binding.root
    }
}