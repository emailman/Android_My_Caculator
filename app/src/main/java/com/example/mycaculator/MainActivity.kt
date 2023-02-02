package com.example.mycaculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var tvEntry: TextView
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvEntry = findViewById(R.id.tvEntry)
    }

    fun onDigit(view: View) {
        tvEntry.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvEntry.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvEntry.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvEntry.text?.let{
            if(!isOperatorAdded(it.toString())){
                tvEntry.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }


    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvEntry.text.toString()
            var prefix = ""

            try {
                Log.i("Eric", tvValue)
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                // Handle subtraction
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var leftSplit = splitValue[0]
                    val rightSplit = splitValue[1]

                    Log.i("Eric", prefix)
                    // if (prefix.isNotEmpty()) {
                    if (prefix == "-") {
                        leftSplit = prefix + leftSplit
                        Log.i("Eric", "leading -")
                    }

                    tvEntry.text =
                        removeDotZero((leftSplit.toDouble() -
                                rightSplit.toDouble()).toString())

                // Handle addition
                } else if (tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var leftSplit = splitValue[0]
                        val rightSplit = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            leftSplit = prefix + leftSplit
                        }

                        tvEntry.text =
                            removeDotZero((leftSplit.toDouble() +
                                    rightSplit.toDouble()).toString())

                // Handle multiplication
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var leftSplit = splitValue[0]
                    val rightSplit = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSplit = prefix + leftSplit
                    }

                    tvEntry.text =
                        removeDotZero((leftSplit.toDouble() *
                                rightSplit.toDouble()).toString())

                // Handle division
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var leftSplit = splitValue[0]
                    val rightSplit = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        leftSplit = prefix + leftSplit
                    }

                    tvEntry.text =
                        removeDotZero((leftSplit.toDouble() /
                                rightSplit.toDouble()).toString())
                }

            } catch (e: java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-") && value.length == 1) {
            false
        }else if (value.contains("/") ||
            value.contains("*") ||
            value.contains("+")){
            true
        }else if (value.length > 1){
            val temp = value.subSequence(1, value.length-1)
            temp.contains("-")
        }else{
            false
        }
    }

    private fun removeDotZero(result: String) : String {
        var value = result
        if (result.endsWith(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}