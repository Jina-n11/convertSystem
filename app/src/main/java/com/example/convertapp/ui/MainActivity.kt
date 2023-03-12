package com.example.convertapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.convertapp.model.SystemNumberModel
import com.example.convertapp.util.Constant.BINARY_BASE
import com.example.convertapp.util.Constant.BINARY_RANGE
import com.example.convertapp.util.Constant.DECIMAL_BASE
import com.example.convertapp.util.Constant.DECIMAL_RANGE
import com.example.convertapp.util.Constant.HEXADECIMAL_BASE
import com.example.convertapp.util.Constant.HEXADECIMAL_RANGE
import com.example.convertapp.util.Constant.NOT_HAVE_BASE
import com.example.convertapp.util.Constant.OCTAL_BASE
import com.example.convertapp.util.Constant.OCTAL_RANGE
import com.example.convertapp.util.SystemNumber
import com.example.convertapp.util.showDialog


class MainActivity() : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var binaryTextView: TextView
    private lateinit var octalTextView: TextView
    private lateinit var decimalTextView: TextView
    private lateinit var hexadecimalTextView: TextView
    private lateinit var convertButton: Button
    private lateinit var numberSystemSpinner :Spinner

    private var selectedNumberSystemType : String = SystemNumber.DECIMAL.name
    private lateinit var systemNumberModel :SystemNumberModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        addCallbacks()
    }

    private fun initViews() {
        inputEditText = findViewById(R.id.input)
        binaryTextView = findViewById(R.id.binaryValue)
        octalTextView = findViewById(R.id.octalValue)
        decimalTextView = findViewById(R.id.decimalValue)
        hexadecimalTextView = findViewById(R.id.hexaDecimalValue)
        convertButton = findViewById(R.id.convertButton)
        numberSystemSpinner = findViewById(R.id.systemSpinner)

        setUpSpinnerAdapter()
    }

    private fun setUpSpinnerAdapter() {
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            SystemNumber.values()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        numberSystemSpinner.adapter = adapter
    }


     private fun addCallbacks() {
         setEditTextListener()
         onChooseItemFromSystemNumberType()
         onClickConvertButton()
    }

    private fun onChooseItemFromSystemNumberType() {
        numberSystemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long , ) {
                selectedNumberSystemType = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun setEditTextListener() = inputEditText.addTextChangedListener( object : TextWatcher
    {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            visibleButton()
        }
    })

    private fun visibleButton() {
        convertButton.visibility = View.VISIBLE
        if (convertButton.text.isNotEmpty()) {
            convertButton.visibility = View.VISIBLE
        } else {
            convertButton.visibility = View.INVISIBLE
        }
    }


    private fun onClickConvertButton() = convertButton.setOnClickListener {

        val number = inputEditText.text.toString()
        val base = checkSystemBase(systemType = selectedNumberSystemType)
        val checkedSystemInput = validateInputSystemNumber(systemType = selectedNumberSystemType , number = number)


        if (base != 0 && checkedSystemInput){

            systemNumberModel = SystemNumberModel()
            systemNumberModel.convertToBinary(number= number , base= base ,)
            systemNumberModel.convertToSystemNumber()
            updateTextViewState(systemNumberModel)

        } else {
            this.showDialog(title= getString(R.string.title ,) ,
                message = getString(R.string.message) + selectedNumberSystemType ,)
        }
    }



    private fun checkSystemBase(systemType: String) : Int {

        return when(systemType){
            SystemNumber.BINARY.name -> {
                BINARY_BASE
            }
            SystemNumber.OCTAL.name -> {
                OCTAL_BASE
            }
            SystemNumber.DECIMAL.name -> {
                DECIMAL_BASE
            }
            SystemNumber.HEXADECIMAL.name -> {
                HEXADECIMAL_BASE
            }
            else ->{
                NOT_HAVE_BASE
            }
        }
    }



    private fun validateInputSystemNumber(systemType: String , number: String): Boolean {

        return when(systemType){
            SystemNumber.HEXADECIMAL.name -> {
                validateInput(number = number , range = HEXADECIMAL_RANGE)
            }
            SystemNumber.BINARY.name -> {
                validateInput(number = number , range = BINARY_RANGE,)
            }
            SystemNumber.OCTAL.name -> {
                validateInput(number = number , range = OCTAL_RANGE,)
            }
            SystemNumber.DECIMAL.name -> {
                validateInput(number = number , range = DECIMAL_RANGE ,)
            }
            else ->{
                false
            }
        }
    }


    private fun validateInput(number: String , range : List<String>) : Boolean {
        var flag = false
        for (digit in number.map { it.toString() }) {
             when (digit) {
                in range -> {
                    flag = true
                }
            }
        }
        return flag
    }



    private fun updateTextViewState(systemNumberModel: SystemNumberModel) {
        systemNumberModel.apply {
            binaryTextView.text = this.binary
            octalTextView.text = this.octal
            decimalTextView.text = this.decimal
            hexadecimalTextView.text = this.hexadecimal
        }
    }

}