package com.example.resistencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RegistroCalculadora()
            }
        }
    }
}

@Composable
fun RegistroCalculadora() {
    val colores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")

    var color1 by remember { mutableStateOf("Rojo") }
    var color2 by remember { mutableStateOf("Verde") }
    var multiplicador by remember { mutableStateOf("Amarillo") }
    var resultado by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Calculadora de Resistencias",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp, fontWeight = FontWeight.Bold)
        )

        MenuSeleccionable("Banda 1", colores, color1) { color1 = it }
        MenuSeleccionable("Banda 2", colores, color2) { color2 = it }
        MenuSeleccionable("Multiplicador", colores, multiplicador) { multiplicador = it }

        Button(onClick = {
            resultado = calcularResistencia(color1, color2, multiplicador)
        }) {
            Text("Calcular")
        }

        VisualizarResultado(color1, color2, multiplicador, resultado)
    }
}

@Composable
fun MenuSeleccionable(label: String, opciones: List<String>, seleccionado: String, onSeleccion: (String) -> Unit) {
    var expandido by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clickable { expandido = true }
                .padding(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(seleccionado)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Seleccionar")
            }

            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onSeleccion(opcion)
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VisualizarResultado(color1: String, color2: String, multiplicador: String, resultado: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        BandaColor(color1)
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .background(Color.LightGray)
                .border(1.dp, Color.Black)
        )
        BandaColor(color2)
        BandaColor(multiplicador)
    }
    Text(
        "$resultado Ω",
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun BandaColor(colorName: String) {
    val colorMap = mapOf(
        "Negro" to Color.Black,
        "Marrón" to Color(0xFF8B4513),
        "Rojo" to Color.Red,
        "Naranja" to Color(0xFFFFA500),
        "Amarillo" to Color.Yellow,
        "Verde" to Color.Green,
        "Azul" to Color.Blue,
        "Violeta" to Color(0xFF8A2BE2),
        "Gris" to Color.Gray,
        "Blanco" to Color.White
    )

    Box(
        modifier = Modifier
            .size(20.dp, 60.dp)
            .background(colorMap[colorName] ?: Color.LightGray)
            .border(1.dp, Color.Black)
    )
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

    return ((valor1 * 10) + valor2) * 10.0.pow(multi.toDouble()).toInt()
}



