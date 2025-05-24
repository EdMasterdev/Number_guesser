package com.example.number_guesser
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numeroAdivinar = 0
    private var intentos = 3
    private lateinit var timer: CountDownTimer
    private var tiempoRestante = 60000L // 1 minuto en milisegundos

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textoIntentos = findViewById<TextView>(R.id.txtIntentos)
        val entradaNumero = findViewById<EditText>(R.id.editNumero)
        val botonAdivinar = findViewById<Button>(R.id.btnAdivinar)
        val resultado = findViewById<TextView>(R.id.txtResultado)

        numeroAdivinar = Random.nextInt(0, 101)
        textoIntentos.text = "Tienes $intentos intentos para adivinar"

        // Temporizador de 1 minuto
        timer = object : CountDownTimer(tiempoRestante, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished
            }

            override fun onFinish() {
                resultado.text = "¡Se acabó el tiempo! Era $numeroAdivinar"
                botonAdivinar.isEnabled = false
            }
        }
        timer.start()

        botonAdivinar.setOnClickListener {
            val numeroUsuario = entradaNumero.text.toString().toIntOrNull()
            if (numeroUsuario == null) {
                Toast.makeText(this, "Ingresa un número válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            intentos--
            when {
                numeroUsuario == numeroAdivinar -> {
                    resultado.text = "¡Correcto! Adivinaste el número 🎉"
                    botonAdivinar.isEnabled = false
                    timer.cancel()
                }
                intentos == 0 -> {
                    resultado.text = "¡Se acabaron los intentos! Era $numeroAdivinar"
                    botonAdivinar.isEnabled = false
                    timer.cancel()
                }
                numeroUsuario < numeroAdivinar -> {
                    resultado.text = "El número es mayor. Te quedan $intentos intentos."
                }
                else -> {
                    resultado.text = "El número es menor. Te quedan $intentos intentos."
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
