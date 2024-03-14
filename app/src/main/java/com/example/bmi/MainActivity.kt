package com.example.bmi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    private lateinit var sexMButton: Button
    private lateinit var sexFButton: Button
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var calculateButton: Button
    private lateinit var seekBarHeight: SeekBar
    private lateinit var heightLabel: TextView
    private var isMale = true
    private  var heightVariable = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sexMButton = findViewById(R.id.sexm)
        sexFButton = findViewById(R.id.sexf)
        weightEditText = findViewById(R.id.slide_w)
        resultTextView = findViewById(R.id.result)
        calculateButton = findViewById(R.id.calculate_button)
        seekBarHeight = findViewById(R.id.seek_bar)
        heightLabel = findViewById(R.id.label_h)

        sexMButton.setOnClickListener { onSexButtonClicked(it) }
        sexFButton.setOnClickListener { onSexButtonClicked(it)}
        calculateButton.setOnClickListener{ onCalculateClicked(it)}

        seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val heightInMeters = progress / 100.0
                val heightFormatted = String.format("%.2f", heightInMeters)
                heightLabel.text = " Height(m): " + heightFormatted
                heightVariable = heightFormatted

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    private fun onSexButtonClicked(view: View){
        isMale = view.id == R.id.sexm
        updateButtonBackground()
    }

    private fun updateButtonBackground() {
        val maleColor = if(isMale) R.color.green else R.color.white
        val femaleColor = if(isMale) R.color.white else R.color.green
        sexMButton.setBackgroundColor(maleColor)
        sexFButton.setBackgroundColor(femaleColor)
    }
    private  fun onCalculateClicked(view: View) {
        val weightStr = weightEditText.text.toString()
        val heightStr = heightVariable

        if(weightStr.isNotEmpty()&& heightStr.isNotEmpty()) {
            val weight = weightStr.toFloat()
            var height = heightStr.toFloat()
            val bmi = calculateBMI(weight, height)
            displayBMIResult(bmi)
        } else {
            resultTextView.text = "Please enter valid weight and height."
        }
    }
    private fun  calculateBMI(weight:Float, height:Float): Float {
        return weight / (height * height)
    }
    private fun displayBMIResult(bmi: Float) {
        resultTextView.text = "%.2f".format(bmi)
    }

}