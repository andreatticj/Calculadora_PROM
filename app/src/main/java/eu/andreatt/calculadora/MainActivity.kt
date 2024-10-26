package eu.andreatt.calculadora

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.andreatt.calculadora.databinding.ActivityMainBinding

/**
 * Clase MainActivity que representa la actividad principal de la calculadora.
 * Implementa la interfaz OnClickListener para manejar los eventos de clic en los botones de la calculadora.
 */
class MainActivity : AppCompatActivity(), OnClickListener {

    private var firstNumber = 0.0  // Primer número ingresado por el usuario
    private var secondNumber = 0.0 // Segundo número ingresado por el usuario
    private var operation: String? = null  // Operación seleccionada por el usuario

    /**
     * Permite hacer referencia directa a los elementos del XML sin necesidad de instanciar cada elemento manualmente.
     */
    private lateinit var binding: ActivityMainBinding

    /**
     * Método que se llama cuando se crea la actividad.
     * Configura el contenido de la vista y los eventos de clic para cada botón de la calculadora.
     *
     * @param savedInstanceState Contiene el estado previamente guardado de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        // Inicializa el objeto binding y establece la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el comportamiento de la vista para ajustar el padding según las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        operation = null  // Inicializa la operación como nula

        // Configura los eventos de clic para cada botón numérico y de operación
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

    /**
     * Maneja los eventos de clic para los botones de la calculadora.
     *
     * @param view La vista que fue clicada.
     */
    override fun onClick(view: View?) {
        when (view) {
            binding.bt0 -> onNumberPressed("0")   // Botón 0
            binding.bt1 -> onNumberPressed("1")   // Botón 1
            binding.bt2 -> onNumberPressed("2")   // Botón 2
            binding.bt3 -> onNumberPressed("3")   // Botón 3
            binding.bt4 -> onNumberPressed("4")   // Botón 4
            binding.bt5 -> onNumberPressed("5")   // Botón 5
            binding.bt6 -> onNumberPressed("6")   // Botón 6
            binding.bt7 -> onNumberPressed("7")   // Botón 7
            binding.bt8 -> onNumberPressed("8")   // Botón 8
            binding.bt9 -> onNumberPressed("9")   // Botón 9
            binding.btComma -> onNumberPressed(",")  // Botón coma
            binding.btPlus -> onOperationPressed("+") // Botón suma
            binding.btMinus -> onOperationPressed("-") // Botón resta
            binding.btMult -> onOperationPressed("X")  // Botón multiplicación
            binding.btDiv -> onOperationPressed("/")   // Botón división
            binding.btEqual -> onEqualPressed()        // Botón igual
            binding.btClear -> onClearPressed()        // Botón limpiar
        }
    }

    /**
     * Método para manejar la entrada de un número en pantalla.
     *
     * @param number Número en forma de String que se agregará a la pantalla.
     */
    private fun onNumberPressed(number: String) {
        renderScreen(number)  // Muestra el número en la pantalla
        checkOperation()      // Actualiza los números de la operación
    }

    /**
     * Muestra el número ingresado en la pantalla.
     *
     * @param number Número en forma de String que se mostrará en la pantalla.
     */
    private fun renderScreen(number: String) {
        val result: String = if (binding.screen.text == "0" && number != ",") {
            number  // Reemplaza "0" por el número ingresado
        } else {
            "${binding.screen.text}$number"  // Agrega el número al final del texto actual
        }
        binding.screen.text = result  // Actualiza la pantalla con el nuevo valor
    }

    /**
     * Verifica si la operación ya ha sido seleccionada y actualiza los números correspondientes.
     */
    private fun checkOperation() {
        if (operation == null) {
            firstNumber = binding.screen.text.toString().toDouble()  // Primer número si no hay operación
        } else {
            secondNumber = binding.screen.text.toString().toDouble() // Segundo número si hay operación
        }
    }

    /**
     * Maneja la selección de la operación.
     *
     * @param operation Operación seleccionada en forma de String (ej. "+", "-", "*", "/").
     */
    private fun onOperationPressed(operation: String) {
        this.operation = operation  // Guarda la operación seleccionada
        firstNumber = binding.screen.text.toString().toDouble()  // Asigna el primer número
        binding.screen.text = "0"  // Resetea la pantalla
    }

    /**
     * Calcula el resultado según la operación seleccionada y actualiza la pantalla.
     */
    private fun onEqualPressed() {
        // Realiza la operación según el valor de `operation`
        val result = when (operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "X" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            else -> 0
        }

        // Resetea la operación y guarda el resultado en `firstNumber`
        operation = null
        firstNumber = result.toDouble()

        // Muestra el resultado en pantalla, eliminando el ".0" si no es necesario
        try {
            binding.screen.text = if (result.toString().endsWith(".0")) {
                result.toString().replace(".0", "")  // Quita el ".0" para enteros
            } else {
                "%0.2f".format(result)  // Formatea a 2 decimales
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Muestra cualquier error en la consola
        }
    }

    /**
     * Restablece la pantalla y los valores almacenados a sus valores iniciales.
     */
    private fun onClearPressed() {
        binding.screen.text = "0"  // Resetea la pantalla a "0"
        firstNumber = 0.0          // Resetea el primer número
        secondNumber = 0.0         // Resetea el segundo número
    }
}
