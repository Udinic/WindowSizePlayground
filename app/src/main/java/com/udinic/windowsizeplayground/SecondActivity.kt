package com.udinic.windowsizeplayground

import android.os.Bundle
import android.widget.TextView
import com.udinic.windowsizeplayground.databinding.ActivityMainBinding
import com.udinic.windowsizeplayground.databinding.ActivitySecondBinding
import com.udinic.windowsizeplayground.largescreens.LargeScreenUtil


class SecondActivity : SharedParentActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateText()
    }

    override fun updateText() {
        findViewById<TextView>(R.id.text_winmetrics).setText("${LargeScreenUtil.getCurrentWindowSizeClass()}")
        findViewById<TextView>(R.id.text_act_hash).setText("Activity hash [${Integer.toHexString(hashCode())}]")
    }
}
