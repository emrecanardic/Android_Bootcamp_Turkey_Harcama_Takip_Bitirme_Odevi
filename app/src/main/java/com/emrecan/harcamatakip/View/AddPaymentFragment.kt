package com.emrecan.harcamatakip.View

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.canerkaya.harcamatakip.Data.DatabaseManager
import com.canerkaya.harcamatakip.R
import com.canerkaya.harcamatakip.ViewModel.AddPaymentViewModel
import com.canerkaya.harcamatakip.ViewModelFactory.AddPaymentViewModelFactory
import com.canerkaya.harcamatakip.databinding.FragmentAddPaymentBinding
import com.emrecan.harcamatakip.Data.DatabaseManager
import com.emrecan.harcamatakip.R
import com.emrecan.harcamatakip.ViewModel.AddPaymentViewModel
import com.emrecan.harcamatakip.ViewModelFactory.AddPaymentViewModelFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class AddPaymentFragment : Fragment(),CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private var fragmentBinding:FragmentAddPaymentBinding?=null
    private var binding:FragmentAddPaymentBinding?=null
    private lateinit var viewModel: AddPaymentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPaymentBinding.bind(view)
        fragmentBinding = binding
        val application = requireNotNull(this.activity).application
        val database = DatabaseManager.getDatabaseManager(application).paymentDao()
        val viewModelFactory = AddPaymentViewModelFactory(database,application)
        viewModel = ViewModelProvider(this,viewModelFactory)[AddPaymentViewModel::class.java]
        binding?.addButton?.setOnClickListener { addClicked() }
        toggleGroupControl()
        observeLiveData()
    }


    private fun toggleGroupControl(){
        binding?.paymentTypeGroup?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding?.kiraButton?.id -> {viewModel.paymentType.value = "Kira"}
                binding?.faturaButton?.id -> {viewModel.paymentType.value = "Fatura"}
                binding?.digerButton?.id -> {viewModel.paymentType.value = "Diger"}
            }
        }
        binding?.costTypeGroup?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                binding?.tlButton?.id ->{viewModel.costType.value = "₺"}
                binding?.euroButton?.id ->{viewModel.costType.value = "€"}
                binding?.dolarButton?.id ->{viewModel.costType.value = "$"}
                binding?.sterlinButton?.id ->{viewModel.costType.value = "£"}
            }
        }
    }
    private fun observeLiveData(){
        viewModel.loading.observe(viewLifecycleOwner,{
            when(it){
                "Done" ->{findNavController().popBackStack()}
                "loading"->{
                    binding?.progressLayout?.visibility = View.VISIBLE
                    binding?.mainLayout?.visibility = View.GONE
                }
                "Error"->{errorMessage()}
            }
        })

    }

    private fun errorMessage(){
        val alertDialogBuilder = context?.let { AlertDialog.Builder(it) }
        if (alertDialogBuilder != null) {
            alertDialogBuilder.setTitle(R.string.hata)
            alertDialogBuilder.setMessage(R.string.kur_hata)
            alertDialogBuilder.setIcon(R.drawable.error_logo)
            alertDialogBuilder.setPositiveButton(R.string.tamam,DialogInterface.OnClickListener { dialog, which -> viewModel.loading.value = "Done"})
            alertDialogBuilder.show()
        }
    }

    private fun addClicked(){
        if (binding?.paymentNameEditText?.text.isNullOrEmpty()){
            binding?.paymentNameInputLayout?.error = requireContext().getString(R.string.bos_birakma)
            return
        }
        if (binding?.paymentCostEditText?.text.isNullOrEmpty()){
            binding?.paymentCostInputLayout?.error = requireContext().getString(R.string.bos_birakma)
            return
        }
        if (viewModel.costType.value.isNullOrEmpty() || viewModel.paymentType.value.isNullOrEmpty()){
            warningMessage()
            return
        }
        viewModel.paymentName.value = binding?.paymentNameEditText?.text.toString()
        viewModel.paymentCost.value = binding?.paymentCostEditText?.text.toString().toInt()

        when(viewModel.costType.value){
            "₺" -> {viewModel.trySelected(viewModel.paymentCost.value!!)}
            "€" -> {viewModel.euroSelected(viewModel.paymentCost.value!!)}
            "$" -> {viewModel.usdSelected(viewModel.paymentCost.value!!)}
            "£" -> {viewModel.gbpSelected(viewModel.paymentCost.value!!)}
        }

    }
    fun warningMessage(){
        val alertDialogBuilder = context?.let { AlertDialog.Builder(it) }
        if (alertDialogBuilder != null) {
            alertDialogBuilder.setTitle(R.string.hata)
            alertDialogBuilder.setMessage(R.string.bos_birakma2)
            alertDialogBuilder.setIcon(R.drawable.error_logo)
            alertDialogBuilder.setPositiveButton(R.string.tamam, null)
            alertDialogBuilder.show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding =null
    }
}