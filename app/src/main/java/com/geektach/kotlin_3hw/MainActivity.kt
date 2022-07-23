package com.geektach.kotlin_3hw

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.geektach.kotlin_3hw.adapter.Adapter
import com.geektach.kotlin_3hw.base.BaseActivity
import com.geektach.kotlin_3hw.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : BaseActivity<ActivityMainBinding>(), Adapter.Listener {

    private val adapter = Adapter(this)
    private val imageList = arrayListOf<Uri>()

    companion object {
        const val KEY_image = "img"
    }

    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val imageView = it.data?.data
            if (imageView != null) {
                adapter.addImage(imageView)
            }
        }
    }

    override fun inflate(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }

    override fun initView() {
        binding.recycler.layoutManager = GridLayoutManager(this@MainActivity, 3)
        binding.recycler.adapter = adapter
    }

    override fun initListener() {
        binding.fabChose.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            intent.putExtra(Intent.ACTION_PICK, true)
            activityResultLauncher.launch(intent)
        }
        binding.btnNext.setOnClickListener {
            openGallery(imageList)
        }
    }

    private fun openGallery(imageList: ArrayList<Uri>) {
        Intent(this@MainActivity, ResultActivity::class.java).apply {
            putExtra(KEY_image, imageList)
            startActivity(this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCLick(mainImage: Uri) {
        imageList.addAll(listOf(mainImage))
        if (imageList.size >= 0) with(binding) {
            tvTop.text =
                getString(R.string.Selected) + " " + imageList.size + getString(R.string.photo)
            tvTop.setOnClickListener {
                openGallery(imageList)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun deleteClick(mainImage: Uri) {
        imageList.remove(mainImage)
        if (imageList.size >= 0) with(binding) {
            tvTop.text =
                getString(R.string.Selected) + " " + imageList.size + getString(R.string.photo)
        }
    }

}