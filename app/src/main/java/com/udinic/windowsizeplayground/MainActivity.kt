package com.udinic.windowsizeplayground

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.udinic.windowsizeplayground.databinding.ActivityMainBinding
import com.udinic.windowsizeplayground.largescreens.*


class MainActivity : SharedParentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<Button>(R.id.btn_goto_second_act).setOnClickListener(View.OnClickListener { startActivity(
            Intent(this, SecondActivity::class.java )
        ) })
        updateText()
    }

    override fun getSameLayoutGroups(): List<List<Pair<WindowWidthSizeClass, WindowHeightSizeClass>>> {
        return LAYOUT_GROUPS_W600_W840
    }

    override fun updateText() {
        findViewById<TextView>(R.id.text_winmetrics).setText("${LargeScreenUtil.getCurrentWindowSizeClass()}")
        findViewById<TextView>(R.id.text_act_hash).setText("Activity hash [${Integer.toHexString(hashCode())}]")
    }

    fun addUtilityView() {
        binding.root.addView(object : View(this) {
            override fun onConfigurationChanged(newConfig: Configuration?) {
                super.onConfigurationChanged(newConfig)
            }
        })
    }
}
