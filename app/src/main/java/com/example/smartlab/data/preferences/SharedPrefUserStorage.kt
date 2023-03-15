package com.example.smartlab.data.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.smartlab.presentation.models.ProductModel
import com.example.smartlab.presentation.models.UpdateProfileModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val SHARED_PREF_NAME = "SHARD_PREF"

class SharedPrefUserStorage(context: Context) : UserStorage {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)

    override fun saveOnBoarding(isSave: Boolean) =
        sharedPreferences.edit().putBoolean("isSave", isSave).apply()

    override fun getOnBoarding(): Boolean = sharedPreferences.getBoolean("isSave", false)

    override fun addProduct(product: ProductModel){
        if (sharedPreferences.getString("PRODUCTS", null) == null) {
            val listId = mutableListOf<ProductModel>()
            listId.add(product)
            sharedPreferences.edit().putString("PRODUCTS", Gson().toJson(listId)).apply()
        } else {
            val listId: MutableList<ProductModel> = Gson().fromJson(
                sharedPreferences.getString("PRODUCTS", null),
                object : TypeToken<MutableList<ProductModel>>() {}.type
            )
            listId.add(product)
            sharedPreferences.edit().putString("PRODUCTS", Gson().toJson(listId)).apply()
        }
    }

    override fun deleteProduct(product: ProductModel) {
        val listId: MutableList<ProductModel> = Gson().fromJson(
            sharedPreferences.getString("PRODUCTS", null),
            object : TypeToken<MutableList<ProductModel>>() {}.type
        )
        listId.remove(product)
        sharedPreferences.edit().putString("PRODUCTS", Gson().toJson(listId)).apply()
    }

    override fun getProductsList(): List<ProductModel> {
        return if (sharedPreferences.getString("PRODUCTS", null) == null) emptyList<ProductModel>()
        else Gson().fromJson(
            sharedPreferences.getString("PRODUCTS", null),
            object : TypeToken<MutableList<ProductModel>>() {}.type
        )
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("TOKEN", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("TOKEN", null)
    }

    override fun saveUser(user: UpdateProfileModel) {
        if (sharedPreferences.getString("USERS", null) == null) {
            val listUsers = mutableListOf<UpdateProfileModel>()
            listUsers.add(user)
            sharedPreferences.edit().putString("USERS", Gson().toJson(listUsers)).apply()
        } else {
            val listUsers: MutableList<UpdateProfileModel> = Gson().fromJson(
                sharedPreferences.getString("USERS", null),
                object : TypeToken<MutableList<UpdateProfileModel>>() {}.type
            )
            listUsers.add(user)
            sharedPreferences.edit().putString("USERS", Gson().toJson(listUsers)).apply()
        }
    }

    override fun getUserList(): List<UpdateProfileModel> {
        return if (sharedPreferences.getString("USERS", null) == null) emptyList<UpdateProfileModel>()
        else Gson().fromJson(
            sharedPreferences.getString("USERS", null),
            object : TypeToken<MutableList<UpdateProfileModel>>() {}.type
        )
    }

}