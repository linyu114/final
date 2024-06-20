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
import android.graphics.BitmapFactory
import android.widget.ArrayAdapter
import com.example.afinal.firstDAO
import com.example.afinal.AppDatabase
import com.example.afinal.firstEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.appcompat.app.AlertDialog

data class ListItem(
    val photo: Int,
    val name: String,
    val phone: String,
    val pen: Int,
    val delete: Int
)

class CustomAdapter(
    context: Context,
    private val layoutResource: Int,
    private val items: List<ListItem>
) : ArrayAdapter<ListItem>(context, layoutResource, items) {

    private val firstDAO: firstDAO = AppDatabase.getInstance(context).userDao()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)

        // 根據位置從 items 中取出 ListItem
        val item = items[position]

        val photo: ImageView = view.findViewById(R.id.photoShow)
        val name: TextView = view.findViewById(R.id.nameShow)
        val phone: TextView = view.findViewById(R.id.phoneShow)
        val pen: ImageView = view.findViewById(R.id.penFun)
        val delete: ImageView = view.findViewById(R.id.deleteFun)

        // 設置圖片、名字和手機號碼
        photo.setImageResource(item.photo)
        name.text = item.name
        phone.text = item.phone
        pen.setImageResource(item.pen)
        delete.setImageResource(item.delete)

        // 點擊 delete icon 顯示刪除確認對話框
        delete.setOnClickListener {
            showDeleteConfirmationDialog(context, item)
        }


        // 在這裡你可以使用 Room 資料庫來讀取 Contact 資料
        GlobalScope.launch(Dispatchers.IO) {
            val contact = firstDAO.findUserByName(item.name)
            withContext(Dispatchers.Main) {
                if (contact != null) {
                    if (contact.photoPath != null) {
                        val bitmap = BitmapFactory.decodeFile(contact.photoPath)
                        photo.setImageBitmap(bitmap)
                    }
                    name.text = contact.name ?: item.name
                    phone.text = contact.phone ?: item.phone
                } else {
                    name.text = item.name
                    phone.text = item.phone
                }
            }
        }

        return view
    }
}

private fun showDeleteConfirmationDialog(context: Context, item: ListItem) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("確認刪除")
    builder.setMessage("確定要刪除 ${item.name} 嗎？")
    builder.setPositiveButton("確定") { dialog, _ ->
        GlobalScope.launch(Dispatchers.IO) {
            val contact = AppDatabase.getInstance(context).userDao().findUserByName(item.name)
            if (contact != null) {
                AppDatabase.getInstance(context).userDao().delete(contact)
            }
        }
        dialog.dismiss()
    }
    builder.setNegativeButton("取消") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var customAdapter: CustomAdapter
    private lateinit var firstDAO: firstDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 初始化 firstDAO
        firstDAO = AppDatabase.getInstance(requireContext()).userDao()

        // 從資料庫中抓取資料並設置自訂的 ListView adapter
        GlobalScope.launch(Dispatchers.IO) {
            val contacts = firstDAO.getAll() // 取得所有的 Contact 資料
            val items = contacts.map {
                ListItem(
                    photo = it.photoPath?.let { path -> R.drawable.ic_launcher_foreground } ?: R.drawable.ic_launcher_foreground,
                    name = it.name ?: "",
                    phone = it.phone ?: "",
                    pen = R.drawable.pen,
                    delete = R.drawable.delete
                )
            }
            withContext(Dispatchers.Main) {
                customAdapter = CustomAdapter(requireContext(), R.layout.item, items)
                binding.list.adapter = customAdapter
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
