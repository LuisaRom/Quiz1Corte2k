package com.example.resistencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.resistencias.ui.theme.calcularResistencia

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            registroCalculadora()
        }
    }
}

@Composable
fun registroCalculadora() {
    val colores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")

    var color1 by remember { mutableStateOf("Rojo") }
    var color2 by remember { mutableStateOf("Verde") }
    var multiplicador by remember { mutableStateOf("Amarillo") }
    var resultado by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Calculadora de Resistencias", style = MaterialTheme.typography.headlineMedium)

        menuSeleccionable("Primer color", colores, color1) { color1 = it }
        menuSeleccionable("Segundo color", colores, color2) { color2 = it }
        menuSeleccionable("Multiplicador", colores, multiplicador) { multiplicador = it }

        Button(onClick = {
            resultado = calcularResistencia(color1, color2, multiplicador)
        }) {
            Text("Calcular")
        }

        Text("Resultado: $resultado Ω", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun menuSeleccionable(label: String, options: List<String>, selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        Box {
            Button(onClick = { expanded = true }) {
                Text(selected)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(option) }, onClick = {
                        onSelected(option)
                        expanded = false
                    })
                }
            }
        }
    }
}

fun calcularResistencia(color1: String, color2: String, multiplicador: String): Int {
    val valores = mapOf(
        "Negro" to 0, "Marrón" to 1, "Rojo" to 2,
        "Naranja" to 3, "Amarillo" to 4, "Verde" to 5,
        "Azul" to 6, "Violeta" to 7, "Gris" to 8, "Blanco" to 9
    )

    val valor1 = valores[color1] ?: 0
    val valor2 = valores[color2] ?: 0
    val multi = valores[multiplicador] ?: 0

    return ((valor1 * 10) + valor2) * Math.pow(10.0, multi.toDouble()).toInt()
}