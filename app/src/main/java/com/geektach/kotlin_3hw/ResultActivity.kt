package com.geektach.kotlin_3hw

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.geektach.kotlin_3hw.adapter.AdapterResult
import com.geektach.kotlin_3hw.base.BaseActivity
import com.geektach.kotlin_3hw.databinding.ActivityResultBinding

class ResultActivity : BaseActivity<ActivityResultBinding>() {

    private val adapterResult = AdapterResult()

    override fun inflate(inflater: LayoutInflater): ActivityResultBinding {
        return ActivityResultBinding.inflate(inflater)
    }

    override fun initView() {
        binding.recycler.layoutManager = GridLayoutManager(this@ResultActivity, 3)
        binding.recycler.adapter = adapterResult
    }

    override fun initListener() {
        val uri: ArrayList<Uri>? = intent.getParcelableArrayListExtra(MainActivity.KEY_image)
        if (uri != null) {
            adapterResult.addImage(uri)
        }
        binding.resultBack.setOnClickListener {
            Intent().apply {
                setResult(RESULT_OK, this)
                finish()
            }
        }
    }
}