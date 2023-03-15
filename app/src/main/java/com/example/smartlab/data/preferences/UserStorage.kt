package com.example.smartlab.data.preferences

import com.example.smartlab.presentation.models.ProductModel
import com.example.smartlab.presentation.models.UpdateProfileModel

interface UserStorage {
    fun saveOnBoarding(isSave: Boolean)

    fun getOnBoarding(): Boolean

    fun addProduct(product: ProductModel)

    fun deleteProduct(product: ProductModel)

    fun getProductsList(): List<ProductModel>

    fun saveToken(token: String)

    fun getToken(): String?

    fun saveUser(user: UpdateProfileModel)

    fun getUserList(): List<UpdateProfileModel>
}