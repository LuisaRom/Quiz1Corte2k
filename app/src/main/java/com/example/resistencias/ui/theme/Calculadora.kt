package com.example.resistencias.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


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

@Composable
fun MostrarResultadoResistencia() {
    val resultado = calcularResistencia("Rojo", "Verde", "Amarillo")
    Text(text = "El valor de la resistencia es: $resultado Ω")
}


@Preview(showBackground = true)
@Composable
fun PreviewMostrarResultadoResistencia() {
    MostrarResultadoResistencia()
}

