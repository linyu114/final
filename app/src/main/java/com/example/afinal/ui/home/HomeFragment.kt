package com.example.afinal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.afinal.R
import com.example.afinal.databinding.FragmentHomeBinding
import android.content.Context
import android.widget.ArrayAdapter

data class ListItem(
    val imageResId: Int,
    val text: String,
    val penResId: Int,
    val deleteResId: Int
)

class CustomAdapter(
    context: Context,
    private val layoutResource: Int,
    private val items: List<ListItem>
) : ArrayAdapter<ListItem>(context, layoutResource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)

        val item = items[position]

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
        val penImageView: ImageView = view.findViewById(R.id.imageView2)
        val deleteImageView: ImageView = view.findViewById(R.id.imageView3)

        imageView.setImageResource(item.imageResId)
        textView.text = item.text
        penImageView.setImageResource(item.penResId)
        deleteImageView.setImageResource(item.deleteResId)

        return view
    }
}

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up the ListView with the custom adapter
        val listView: ListView = binding.list
        val items = listOf(
            ListItem(R.drawable.ic_launcher_foreground,"Item 1", R.drawable.pen, R.drawable.delete),
            ListItem(R.drawable.ic_launcher_foreground, "Item 2", R.drawable.pen, R.drawable.delete),
            ListItem(R.drawable.ic_launcher_foreground, "Item 3", R.drawable.pen, R.drawable.delete)
        )
        val adapter = CustomAdapter(requireContext(), R.layout.item, items)
        listView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
