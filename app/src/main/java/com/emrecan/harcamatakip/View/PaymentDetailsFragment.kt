package com.emrecan.harcamatakip.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canerkaya.harcamatakip.Data.DatabaseManager
import com.canerkaya.harcamatakip.R
import com.canerkaya.harcamatakip.View.PaymentDetailsFragmentArgs
import com.canerkaya.harcamatakip.ViewModel.PaymentDetailsViewModel
import com.canerkaya.harcamatakip.ViewModelFactory.PaymentDetailsViewModelFactory
import com.canerkaya.harcamatakip.databinding.FragmentPaymentDetailsBinding
import com.emrecan.harcamatakip.Data.DatabaseManager
import com.emrecan.harcamatakip.R
import com.emrecan.harcamatakip.ViewModel.PaymentDetailsViewModel
import com.emrecan.harcamatakip.ViewModelFactory.PaymentDetailsViewModelFactory


class PaymentDetailsFragment : Fragment() {
    private var fragmentBinding: FragmentPaymentDetailsBinding?=null
    private var binding: FragmentPaymentDetailsBinding?=null
    private lateinit var viewModel:PaymentDetailsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentDetailsBinding.bind(view)
        fragmentBinding = binding
        val application = requireNotNull(this.activity).application
        val database = DatabaseManager.getDatabaseManager(application).paymentDao()
        val viewModelFactory = PaymentDetailsViewModelFactory(database,application)
        viewModel = ViewModelProvider(this,viewModelFactory)[PaymentDetailsViewModel::class.java]
        viewModel.payment.value = PaymentDetailsFragmentArgs.fromBundle(requireArguments()).payment
        viewModel.costType.value = PaymentDetailsFragmentArgs.fromBundle(requireArguments()).costType
        observeLiveData()
        binding?.deleteButton?.setOnClickListener { deleteClicked() }

    }
    private fun observeLiveData(){
        when(viewModel.payment.value?.paymentType){
            "Kira"->{binding?.paymentLogoImageView?.setImageResource(R.drawable.home_icon)}
            "Fatura"->{binding?.paymentLogoImageView?.setImageResource(R.drawable.bill_logo)}
            else->{binding?.paymentLogoImageView?.setImageResource(R.drawable.shop_icon)}
        }
        when(viewModel.costType.value){
            "₺" ->{binding?.paymentCostTv?.setText(requireContext().getString(R.string.cost,viewModel.payment.value?.tlCost,viewModel.costType.value))}
            "$" ->{binding?.paymentCostTv?.setText(requireContext().getString(R.string.cost,viewModel.payment.value?.dolarCost,viewModel.costType.value))}
            "€" ->{binding?.paymentCostTv?.setText(requireContext().getString(R.string.cost,viewModel.payment.value?.euroCost,viewModel.costType.value))}
            "£" ->{binding?.paymentCostTv?.setText(requireContext().getString(R.string.cost,viewModel.payment.value?.sterlinCost,viewModel.costType.value))}
        }
        binding?.paymentNameTv?.text = viewModel.payment.value?.paymentName
    }

    private fun deleteClicked(){
        viewModel.deletePayment()
        Toast.makeText(requireContext(),R.string.silindi,Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }
}