package com.example.handybookchsb

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.akbar.handybook.ui.LoginFragment
import com.example.handybookchsb.R
import com.example.handybookchsb.databinding.FragmentOqilayotganBinding
import com.example.handybookchsb.databinding.FragmentRegistrationBinding
import com.example.handybookchsb.databinding.FragmentReviewBinding
import com.example.handybookchsb.databinding.FragmentShaxsiyKabinetBinding
import com.example.handybookchsb.model.AddComment
import com.example.handybookchsb.model.Book
import com.example.handybookchsb.model.Comment
import com.example.handybookchsb.model.UserReg
import com.example.handybookchsb.networking.APIClient
import com.example.handybookchsb.networking.APIService
import org.jetbrains.annotations.ApiStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegistrationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegistrationBinding.inflate(inflater,container,false)
        binding.bacKBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, LoginFragment())
                .commit()
        }
        val name:String=binding.nameEditTextId.toString()
        val second_name:String=binding.surnameEditTextId.text.toString()
        val email:String=binding.emailEdittextId.text.toString()
        val parol:String=binding.passwordEdittextId.text.toString()
        val second_parol=binding.cPasswordEdittextId.text.toString()
        val api = APIClient.getInstance().create(APIService::class.java)
        binding.regBtnId.setOnClickListener {
            if (check_info()) {
                val registerInfo = UserReg(name, second_name, email, parol, second_parol)
                api.register(registerInfo).enqueue(object : Callback<UserReg> {
                    override fun onResponse(call: Call<UserReg>, response: Response<UserReg>) {
                        if (response.code() == 201) {
                            Toast.makeText(context, "Registration success!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<UserReg>, t: Throwable) {
                        Log.d(ContentValues.TAG, "onFailure: $t")
                        Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            }
        }
        return binding.root
    }

    fun check_info(): Boolean {
        if (binding.passwordEdittextId.text.toString()!=(binding.cPasswordEdittextId.text.toString())) {
            Toast.makeText(requireContext(),"Parollar mos kelmadi",Toast.LENGTH_SHORT).show()
            binding.passwordEdittextId.error="Parollar mos kelmadi"
            binding.cPasswordEdittextId.error="Parollar mos kelmadi"
            return false
        }
        if (binding.passwordEdittextId.text!!.count()<8 && binding.cPasswordEdittextId.text!!.count()<8){
            Toast.makeText(requireContext(),"Parol 8 ta belgidan kam bolmasligi kerak",Toast.LENGTH_SHORT).show()
            binding.passwordEdittextId.error="Parol 8 ta belgidan kam bolmasligi kerak"
            binding.cPasswordEdittextId.error="Parol 8 ta belgidan kam bolmasligi kerak"
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEdittextId.text.toString()).matches()){
            Toast.makeText(requireContext(),"Email hato",Toast.LENGTH_SHORT).show()
            binding.emailEdittextId.error="Emailni to'g'ri kiriting"
            return false
        }
        if(binding.nameEditTextId==null){
            Toast.makeText(requireContext(),"Ismingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.passwordEdittextId==null){
            Toast.makeText(requireContext(),"Parol kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.cPasswordEdittextId==null){
            Toast.makeText(requireContext(),"Parolni tasdiqlash uchun yana bir bor kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.emailEdittextId==null){
            Toast.makeText(requireContext(),"Email manzilingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.surnameEditTextId==null){
            Toast.makeText(requireContext(),"Familiyangizni kiriting",Toast.LENGTH_SHORT).show()
        }
        if(binding.nameEditTextId==null && binding.surnameEditTextId==null && binding.passwordEdittextId==null && binding.cPasswordEdittextId==null && binding.emailEdittextId==null){
            Toast.makeText(requireContext(),"Ismingizni kiriting",Toast.LENGTH_SHORT).show()
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}