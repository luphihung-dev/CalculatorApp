package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

class CalculatorActivity : ComponentActivity() {

    private lateinit var editNumberOne: EditText
    private lateinit var editNumberTwo: EditText
    private lateinit var textResultValue: TextView
    private lateinit var textError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNumberOne = findViewById(R.id.editNumberOne)
        editNumberTwo = findViewById(R.id.editNumberTwo)
        textResultValue = findViewById(R.id.textResultValue)
        textError = findViewById(R.id.textError)

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val operands = parseOperands() ?: return@setOnClickListener
            showResult(operands.first + operands.second)
        }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener {
            val operands = parseOperands() ?: return@setOnClickListener
            showResult(operands.first - operands.second)
        }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener {
            val operands = parseOperands() ?: return@setOnClickListener
            showResult(operands.first * operands.second)
        }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener {
            val operands = parseOperands() ?: return@setOnClickListener
            if (operands.second == 0.0) {
                showError(getString(R.string.error_divide_by_zero))
                return@setOnClickListener
            }
            showResult(operands.first / operands.second)
        }
    }

    private fun parseOperands(): Pair<Double, Double>? {
        val rawNumberOne = editNumberOne.text.toString().trim()
        val rawNumberTwo = editNumberTwo.text.toString().trim()

        if (rawNumberOne.isEmpty() || rawNumberTwo.isEmpty()) {
            showError(getString(R.string.error_empty_input))
            return null
        }

        val numberOne = rawNumberOne.toDoubleOrNull()
        val numberTwo = rawNumberTwo.toDoubleOrNull()
        if (numberOne == null || numberTwo == null) {
            showError(getString(R.string.error_invalid_number))
            return null
        }

        return numberOne to numberTwo
    }

    private fun showResult(value: Double) {
        textError.visibility = View.GONE
        textResultValue.text = formatResult(value)
    }

    private fun showError(message: String) {
        textError.text = message
        textError.visibility = View.VISIBLE
        textResultValue.text = getString(R.string.result_error_placeholder)
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            "%.6f".format(value).trimEnd('0').trimEnd('.')
        }
    }
}
