package com.example.smartlab.presentation.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentShoppingCartBinding
import com.example.smartlab.presentation.base.BaseFragment

class ShoppingCartFragment : BaseFragment() {

    override val showBottomNavigationView = false
    lateinit var binding: FragmentShoppingCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingCartBinding.inflate(inflater, container, false)

        var prices = 0U
        val navigation = this.findNavController()
        val sharedPreferences = SharedPrefUserStorage(requireContext())
        val shopCartAdapter = ShoppingCartAdapter(resources)
        binding.recyclerView.adapter = shopCartAdapter
        shopCartAdapter.submitList(sharedPreferences.getProductsList())

        binding.buttonBack.setOnClickListener {
            navigation.popBackStack()
        }

        binding.imageBasket.setOnClickListener {
            val listProducts = sharedPreferences.getProductsList()
            for (product in listProducts) {
                sharedPreferences.deleteProduct(product)
                shopCartAdapter.removeItem(product)
                binding.summaPrice.text = null
                binding.summaPrice.isVisible = false
                binding.summa.isVisible = false
            }
        }

        shopCartAdapter.apply {
            deleteItem = { model, size ->
                removeItem(model)
                sharedPreferences.deleteProduct(model)
                prices -= model.price.toUInt() * size
                binding.summaPrice.text = "$prices ${resources.getString(R.string.ruble)}"
            }

            plusPatient = { model ->
                prices += model.price.toUInt()
                binding.summaPrice.text = "$prices ${resources.getString(R.string.ruble)}"
            }

            minusPatient = { model ->
                prices -= model.price.toUInt()
                binding.summaPrice.text = "$prices ${resources.getString(R.string.ruble)}"
            }
        }

        for (product in sharedPreferences.getProductsList()) {
            prices += product.price.toUInt()
        }

        binding.summaPrice.text = "$prices ${resources.getString(R.string.ruble)}"

        binding.goToCheckout.setOnClickListener{
            navigation.navigate(R.id.action_shoppingCartFragment_to_makingOrderFragment)
        }

        return binding.root
    }
}