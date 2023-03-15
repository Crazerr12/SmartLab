package com.example.smartlab.presentation.payment

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentPaymentBinding
import com.example.smartlab.presentation.base.BaseFragment

class PaymentFragment : BaseFragment() {

    override val showBottomNavigationView: Boolean = false
    lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        val navigation = findNavController()

        val makePayment = resources.getString(R.string.make_the_payment)

        val handler = HandlerCompat.createAsync(Looper.getMainLooper())

        handler.postDelayed({

            binding.textLoad.text = makePayment

            handler.postDelayed({
                binding.textLoad.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.buttonToMain.visibility = View.VISIBLE
                binding.buttonPurchaseReceipt.visibility = View.VISIBLE
                binding.orderSuccessfullyPaid.visibility = View.VISIBLE
                binding.infoAboutDelivery.visibility = View.VISIBLE
                binding.rulesForPreparing.visibility = View.VISIBLE
                binding.image.visibility = View.VISIBLE
            }, 4000)
        }, 4000)

        binding.buttonToMain.setOnClickListener {
            navigation.navigate(R.id.action_paymentFragment_to_analysesFragment)
        }

        return binding.root
    }
}