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

    private lateinit var input: EditText
    private lateinit var binaryValue: TextView
    private lateinit var octalValue: TextView
    private lateinit var decimalValue: TextView
    private lateinit var hexaDecimalValue: TextView
    private lateinit var convertButton: Button
    private lateinit var systemSpinner :Spinner

    private val systemTypes = listOf(
        SystemNumber.DECIMAL,
        SystemNumber.BINARY,
        SystemNumber.OCTAL ,
        SystemNumber.HEXADECIMAL,
    )

    private var selectedSystemType : String = SystemNumber.DECIMAL.name
    private lateinit var systemNumberModel :SystemNumberModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUp()
        addCallbacks()
    }

     fun setUp() {
        initView()
        addSystemType(systemTypes)
    }

    private fun initView() {
        input = findViewById(R.id.input)
        binaryValue = findViewById(R.id.binaryValue)
        octalValue = findViewById(R.id.octalValue)
        decimalValue = findViewById(R.id.decimalValue)
        hexaDecimalValue = findViewById(R.id.hexaDecimalValue)
        convertButton = findViewById(R.id.convertButton)
        systemSpinner = findViewById(R.id.systemSpinner)

    }

    private fun addSystemType(systemTypes: List<SystemNumber>) {
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item,
            systemTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        systemSpinner.adapter = adapter
    }


     fun addCallbacks() {
        onInputAdd()
        onChooseItemFromSystemType()
        onClickConvertButton()
    }

    private fun onChooseItemFromSystemType() {
        systemSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long , ) {
                selectedSystemType = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }


    private fun onInputAdd() = input.addTextChangedListener( object : TextWatcher
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

        val number = input.text.toString()
        val base = checkSystemBase(systemType = selectedSystemType)
        val checkedSystemInput = checkSystemInput(systemType = selectedSystemType , number = number)


        if (base != NOT_HAVE_BASE && checkedSystemInput){

            systemNumberModel = SystemNumberModel()
            convertToBinary(number= number , base= base ,)
            convertToSystemNumber()
            viewData(systemNumberModel)

        } else {
            showDialog(title= getString(R.string.title ,) ,
                message = getString(R.string.message) + selectedSystemType ,
            context = this ,)
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



    private fun checkSystemInput(systemType: String , number: String): Boolean {

        return when(systemType){
            SystemNumber.HEXADECIMAL.name -> {
                checkedInput(number = number , range = HEXADECIMAL_RANGE)
            }
            SystemNumber.BINARY.name -> {
                checkedInput(number = number , range = BINARY_RANGE,)
            }
            SystemNumber.OCTAL.name -> {
                checkedInput(number = number , range = OCTAL_RANGE,)
            }
            SystemNumber.DECIMAL.name -> {
                checkedInput(number = number , range = DECIMAL_RANGE ,)
            }
            else ->{
                false
            }
        }
    }


    private fun checkedInput(number: String , range : List<String>) : Boolean {
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


    private fun convertToBinary(number: String, base: Int) {
        systemNumberModel.convertToBinary(number= number , base= base ,)
    }

    private fun convertToSystemNumber() {
        systemNumberModel.convertToSystemNumber()
    }


    private fun viewData(systemNumberModel: SystemNumberModel) {
        systemNumberModel.apply {
            binaryValue.text = this.binary
            octalValue.text = this.octal
            decimalValue.text = this.decimal
            hexaDecimalValue.text = this.hexadecimal
        }
    }


}