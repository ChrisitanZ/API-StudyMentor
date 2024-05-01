package com.example.studymentor

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextDateOfBirth = findViewById<EditText>(R.id.editTextDateOfBirth)

        val btregister = findViewById<Button>(R.id.btRegister)


        btregister.setOnClickListener {
            val intent = Intent(
                this@RegisterActivity,
                LoginActivity::class.java
            )
            startActivity(intent)
        }

        editTextDateOfBirth.setOnClickListener {
            showDatePickerDialog()
        }


        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)

        genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val gender = radioButton.text
            Toast.makeText(this, "Género seleccionado: $gender", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                // El usuario ha seleccionado una fecha
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateString = dateFormat.format(selectedDate.time)

                val editTextDateOfBirth = findViewById<EditText>(R.id.editTextDateOfBirth)
                editTextDateOfBirth.setText(dateString)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
