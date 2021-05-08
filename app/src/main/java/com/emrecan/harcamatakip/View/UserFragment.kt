package com.emrecan.harcamatakip.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.canerkaya.harcamatakip.Model.UserModel
import com.canerkaya.harcamatakip.R
import com.canerkaya.harcamatakip.Util.CustomSharedPreferences
import com.canerkaya.harcamatakip.databinding.FragmentUserBinding
import com.emrecan.harcamatakip.R
import com.emrecan.harcamatakip.Util.CustomSharedPreferences
import com.google.android.material.snackbar.Snackbar


class UserFragment : Fragment() {
    private var fragmentBinding:FragmentUserBinding?=null
    private var binding:FragmentUserBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        fragmentBinding = binding
        binding?.saveButton?.setOnClickListener { saveClicked() }

    }
    fun saveClicked(){
        if (binding?.nameEditText?.text.isNullOrEmpty()){
            binding?.nameInputLayout?.error = "İsim Alanı Boş Bırakılamaz"
            return
        }
        var gender = ""
        val genderId = binding?.genderGroup?.checkedRadioButtonId
        when(genderId!!){
            binding?.erkekRadio?.id ->{gender = "Erkek"}
            binding?.kadinRadio?.id ->{gender = "Kadın"}
            binding?.otherRadio?.id ->{gender = "Diger"}
            else-> {gender = ""}
        }
        if (gender!=""){
            val userName = binding?.nameEditText?.text.toString()
            CustomSharedPreferences(requireContext()).saveUser(userName,gender)
            view?.hideKeyboard()
            findNavController().popBackStack()
        }else{
            Snackbar.make(requireView(),"Cinsiyet Seçimi Boş Bırakılamaz",Snackbar.LENGTH_LONG).show()
        }
    }
    fun View.hideKeyboard() {
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentBinding = null
    }
}