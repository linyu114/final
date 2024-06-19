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
    val name: String,
    val phone: String,
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
        val name: TextView = view.findViewById(R.id.nameTextView)
        val phone: TextView = view.findViewById(R.id.phoneTextView)
        val pen: ImageView = view.findViewById(R.id.penImageView)
        val delete: ImageView = view.findViewById(R.id.deleteImageView)

        imageView.setImageResource(item.imageResId)
        name.text = item.name
        phone.text = item.phone
        pen.setImageResource(item.penResId)
        delete.setImageResource(item.deleteResId)

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

        // 設置自訂的 ListView adapter
        val listView: ListView = binding.list
        val items = listOf(
            ListItem(R.drawable.ic_launcher_foreground, "Item 1", "123-456-7890", R.drawable.pen, R.drawable.delete),
            ListItem(R.drawable.ic_launcher_foreground, "Item 2", "098-765-4321", R.drawable.pen, R.drawable.delete),
            ListItem(R.drawable.ic_launcher_foreground, "Item 3", "555-555-5555", R.drawable.pen, R.drawable.delete)
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
