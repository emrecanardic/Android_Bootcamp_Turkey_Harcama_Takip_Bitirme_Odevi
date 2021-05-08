package com.emrecan.harcamatakip.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.canerkaya.harcamatakip.Adapter.PaymentsAdapter
import com.canerkaya.harcamatakip.Data.DatabaseManager
import com.canerkaya.harcamatakip.Model.PaymentModel
import com.canerkaya.harcamatakip.R
import com.canerkaya.harcamatakip.View.HomeFragmentDirections
import com.canerkaya.harcamatakip.ViewModel.HomeViewModel
import com.canerkaya.harcamatakip.ViewModelFactory.HomeViewModelFactory
import com.canerkaya.harcamatakip.databinding.FragmentHomeBinding
import com.emrecan.harcamatakip.Adapter.PaymentsAdapter
import com.emrecan.harcamatakip.Data.DatabaseManager
import com.emrecan.harcamatakip.Model.PaymentModel
import com.emrecan.harcamatakip.R
import com.emrecan.harcamatakip.ViewModel.HomeViewModel
import com.emrecan.harcamatakip.ViewModelFactory.HomeViewModelFactory


class HomeFragment : Fragment(), PaymentsAdapter.Listener {
    private var fragmentBinding:FragmentHomeBinding?=null
    private var binding:FragmentHomeBinding?=null
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        fragmentBinding = binding
        val application = requireNotNull(this.activity).application
        val database = DatabaseManager.getDatabaseManager(application).paymentDao()
        val viewModelFactory = HomeViewModelFactory(database,application)
        viewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]
        moveFab()
        setClicks()
        observeLiveData()
        getInfo()
        toggleGroupControl()

    }

    fun toggleGroupControl(){
        binding?.buttonGroup?.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when(checkedId){
                binding?.tlButton?.id ->{viewModel.checkedButton.value = "₺"}

                binding?.dolarButton?.id ->{viewModel.checkedButton.value = "$"}

                binding?.euroButton?.id ->{viewModel.checkedButton.value = "€"}

                binding?.sterlinButton?.id ->{viewModel.checkedButton.value = "£"}
            }
        }
    }
    fun getInfo(){
        viewModel.getUser()
        viewModel.getPayments()
    }

    override fun onItemClick(paymentModel: PaymentModel, costType:String) {
        val action = HomeFragmentDirections.actionHomeFragmentToPaymentDetailsFragment(paymentModel,costType)
        findNavController().navigate(action)
    }

    fun observeLiveData(){

        viewModel.user.observe(viewLifecycleOwner,{
            when(it.gender){
                "Erkek" ->{binding?.nameTv?.text = getString(R.string.user,it.name,"Bey")}
                "Kadın" ->{binding?.nameTv?.text = getString(R.string.user,it.name,"Hanım")}
                "Diger" ->{binding?.nameTv?.text = getString(R.string.user,it.name,"")}
            }
        })

        viewModel.paymentList.observe(viewLifecycleOwner,{
            val adapter = PaymentsAdapter(it,viewModel.checkedButton.value!!,this)
            binding?.paymentsRecyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        viewModel.checkedButton.observe(viewLifecycleOwner,{
            val adapter = viewModel.paymentList.value?.let { it1 -> PaymentsAdapter(it1,it,this) }
            binding?.paymentsRecyclerView?.adapter = adapter
            binding?.toplamTv?.setText(getString(R.string.total,viewModel.checkedButtonControl(),it))
            adapter?.notifyDataSetChanged()
        })
        viewModel.tlTotal.observe(viewLifecycleOwner,{
            binding?.toplamTv?.setText(getString(R.string.total,viewModel.checkedButtonControl(),viewModel.checkedButton.value))
        })
        viewModel.dolarTotal.observe(viewLifecycleOwner,{
            binding?.toplamTv?.setText(getString(R.string.total,viewModel.checkedButtonControl(),viewModel.checkedButton.value))
        })
        viewModel.sterlinTotal.observe(viewLifecycleOwner,{
            binding?.toplamTv?.setText(getString(R.string.total,viewModel.checkedButtonControl(),viewModel.checkedButton.value))
        })
        viewModel.euroTotal.observe(viewLifecycleOwner,{
            binding?.toplamTv?.setText(getString(R.string.total,viewModel.checkedButtonControl(),viewModel.checkedButton.value))
        })
    }


    fun setClicks(){
        binding?.addButton?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddPaymentFragment()
            findNavController().navigate(action)
        }
        binding?.cardView?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUserFragment()
            findNavController().navigate(action)
        }
    }

    fun moveFab(){
        binding?.paymentsRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding?.addButton?.visibility == View.VISIBLE) {
                    binding?.addButton?.hide()
                } else if (dy < 0 && binding?.addButton?.visibility != View.VISIBLE) {
                    binding?.addButton?.show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }
}