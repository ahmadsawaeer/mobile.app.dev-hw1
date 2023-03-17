import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var textViewResult: TextView
    private lateinit var buttonConvert: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinner_from)
        spinnerTo = findViewById(R.id.spinner_to)
        editTextAmount = findViewById(R.id.edit_text_amount)
        textViewResult = findViewById(R.id.text_view_result)
        buttonConvert = findViewById(R.id.button_convert)

        val currencies = arrayOf("USD", "EUR", "GBP")
        spinnerFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spinnerTo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)

        buttonConvert.setOnClickListener {
            val amount = editTextAmount.text.toString().toDouble()
            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()

            val result = convertCurrency(amount, from, to)
            textViewResult.text = "%.2f".format(result)
        }

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateResult()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateResult()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateResult() {
        val amount = editTextAmount.text.toString().toDoubleOrNull()
        if (amount != null) {
            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()

            val result = convertCurrency(amount, from, to)
            textViewResult.text = "%.2f".format(result)
        }
    }

    private fun convertCurrency(amount: Double, from: String, to: String): Double {
        val usdToGbp = 0.72
        val eurToGbp = 0.85
        val usdToEur = 0.84

        return when {
            from == "USD" && to == "GBP" -> amount * usdToGbp
            from == "USD" && to == "EUR" -> amount * usdToEur
            from == "EUR" && to == "GBP" -> amount * eurToGbp
            from == "EUR" && to == "USD" -> amount / usdToEur
            from == "GBP" && to == "USD" -> amount / usdToGbp
            from == "GBP" && to == "EUR" -> amount / eurToGbp
            else -> amount
        }
    }
}
