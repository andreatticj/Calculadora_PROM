package eu.andreatt.calculadora

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.andreatt.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private var firstNumber = 0.0
    private var secondNumber= 0.0
    private var operation: String? = null

    /**
     * Permite hacer referencia directa a los elementos del XML sin instanciarlo antes
     */
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        operation = null

        binding.bt0.setOnClickListener(this)
        binding.bt1.setOnClickListener(this)
        binding.bt2.setOnClickListener(this)
        binding.bt3.setOnClickListener(this)
        binding.bt4.setOnClickListener(this)
        binding.bt5.setOnClickListener(this)
        binding.bt6.setOnClickListener(this)
        binding.bt7.setOnClickListener(this)
        binding.bt8.setOnClickListener(this)
        binding.bt9.setOnClickListener(this)
        binding.btComma.setOnClickListener(this)
        binding.btPlus.setOnClickListener(this)
        binding.btMinus.setOnClickListener(this)
        binding.btMult.setOnClickListener(this)
        binding.btDiv.setOnClickListener(this)
        binding.btEqual.setOnClickListener(this)
        binding.btClear.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view){
            binding.bt0 -> onNumberPressed("0")

            binding.bt1 -> onNumberPressed("")

            binding.bt2 -> onNumberPressed("2")

            binding.bt3 -> onNumberPressed("3")

            binding.bt4 -> onNumberPressed("4")

            binding.bt5 -> onNumberPressed("5")

            binding.bt6 -> onNumberPressed("6")

            binding.bt7 -> onNumberPressed("7")

            binding.bt8 -> onNumberPressed("8")

            binding.bt9 -> onNumberPressed("9")

            binding.btComma -> onNumberPressed(",")

            binding.btPlus -> onOperationPressed("+")

            binding.btMinus -> onOperationPressed("-")

            binding.btMult -> onOperationPressed("X")

            binding.btDiv -> onOperationPressed("/")

            binding.btEqual -> onEqualPressed()

            binding.btClear -> ""

        }
    }

    private fun onNumberPressed(number:String){
        renderScreen(number)
        checkOperation()
    }

    private fun renderScreen(number:String){
    val result: String = if (binding.screen.text == "0" && number != ",")
        number
    else
        "${binding.screen.text}$number"

    binding.screen.text =result
    }

    private fun checkOperation(){
        if (operation == null)
            firstNumber = binding.screen.text.toString().toDouble()
        else
            secondNumber = binding.screen.text.toString().toDouble()
    }

    private fun onOperationPressed(operation:String){
        this.operation = operation
        firstNumber = binding.screen.text.toString().toDouble()

        binding.screen.text = "0"
    }

    private fun onEqualPressed(){
        val result = when(operation){
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            else -> 0
        }
        operation = null
        firstNumber = result.toDouble()

        binding.screen.text = if (result.toString().endsWith("0")) {
            result.toString().replace("0", "")
        }else{
            "%0.2f".format(result)
        }
    }
}