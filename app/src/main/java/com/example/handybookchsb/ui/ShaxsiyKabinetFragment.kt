package com.example.handybookchsb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.akbar.handybook.ui.HomeFragment
import com.akbar.handybook.ui.LogOutFragment
import com.akbar.handybook.ui.MainFragment
import com.akbar.handybook.ui.OqilayotganFragment
import com.example.handybookchsb.adapter.Kitob2Adapter
import com.example.handybookchsb.adapter.KitobAdapter
import com.example.handybookchsb.databinding.FragmentShaxsiyKabinetBinding
import com.example.handybookchsb.model.Book
import com.example.handybookchsb.networking.APIClient
import com.example.handybookchsb.networking.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ShaxsiyKabinetFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    var books = mutableListOf<Book>()
    var allBooks = mutableListOf<Book>()

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

        val binding= FragmentShaxsiyKabinetBinding.inflate(inflater,container,false)
        val api = APIClient.getInstance().create(APIService::class.java)
        val shared = MyShared.getInstance(requireContext())
        var user = shared.getUser()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, MainFragment())
                .commit()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }
        api.getAllBooks().enqueue(object : Callback<List<Book>>{
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                if (response.isSuccessful && response.body() != null){
                    allBooks = response.body()!!.toMutableList()
                    for (book in allBooks) {
                        if (book.status == 1){
                            books.add(book)
                        }
                    }
                    var adapter = KitobAdapter(books)
                    binding.recyclerView.adapter = adapter

                    var adapter1 = Kitob2Adapter(allBooks)
                    binding.recyclerView2.adapter = adapter1

                    binding.reading.text = books.size.toString()
                    binding.readFinished.text = allBooks.size.toString()
                    binding.savedbooks.text = "0"
                }
            }

            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

        binding.name.text = user!!.username
        binding.username.text = user.id.toString()
        binding.imageView2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, LogOutFragment())
                .commit()
        }
        binding.textView8.setOnClickListener {
            parentFragmentManager.beginTransaction()
            .replace(R.id.main, OqilganFragment())
            .commit()
}
        binding.textView5.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, OqilayotganFragment())
                .commit()
        }

    return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShaxsiyKabinetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}