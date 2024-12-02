package com.example.calculatrice

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit


import java.util.Stack

@Composable
fun CalculatorScreen() {
    // Utilisation d'un state pour stocker l'affichage
    val display = remember { mutableStateOf("0") }

    // Fonction pour gérer les chiffres et opérations
    fun onButtonClick(value: String) {
        // Gère l'affichage de l'élément cliqué
        display.value = if (display.value == "0" && value != "=") value else display.value + value
    }

    // Fonction pour effectuer le calcul
    fun onEqualsClick() {
        try {
            val result = evaluateExpression(display.value)
            display.value = result
        } catch (_: Exception) {
            display.value = "Erreur"
        }
    }

    // Fonction pour effacer l'affichage
    fun onClearClick() {
        display.value = "0"
    }

    // Fonction pour effacer le dernier caractère
    fun onBackspaceClick() {
        if (display.value.length > 1) {
            display.value = display.value.dropLast(1)
        } else {
            display.value = "0"
        }
    }

    // Fonction pour ajouter une virgule (décimal)
    fun onDecimalClick() {
        if (!display.value.contains(".")) {
            display.value += "."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Champ d'affichage
        Box(
            modifier = Modifier
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = "Calculatrice",
                fontSize = 32.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Champ d'affichage
        Box(
            modifier = Modifier
                .width(322.dp)
                .padding(16.dp)
                .background(Color(245 / 255f, 205 / 255f, 115 / 255f))
                .padding(16.dp) // Espacement interne pour le texte
                .verticalScroll(rememberScrollState()) // Permettre le défilement vertical
        ) {
            Text(
                text = display.value,
                fontSize = 32.sp,
                color = Color.Black,
                maxLines = Int.MAX_VALUE, // Permet de gérer plusieurs lignes
                softWrap = true, // Permet au texte de se couper sur plusieurs lignes
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Les boutons de la calculatrice
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton("(", onClick = { onButtonClick("(") })
                CustomButton(")", onClick = { onButtonClick(")") })
                CustomButtonErase("⌫", onClick = { onBackspaceClick() })
                CustomButtonErase("C", onClick = { onClearClick() })
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton("7", onClick = { onButtonClick("7") })
                CustomButton("8", onClick = { onButtonClick("8") })
                CustomButton("9", onClick = { onButtonClick("9") })
                CustomButtonOperators("÷", onClick = { onButtonClick("/") })
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton("4", onClick = { onButtonClick("4") })
                CustomButton("5", onClick = { onButtonClick("5") })
                CustomButton("6", onClick = { onButtonClick("6") })
                CustomButtonOperators("x", onClick = { onButtonClick("*") })
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButton("1", onClick = { onButtonClick("1") })
                CustomButton("2", onClick = { onButtonClick("2") })
                CustomButton("3", onClick = { onButtonClick("3") })
                CustomButtonOperators("-", onClick = { onButtonClick("-") })
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                CustomButtonDouble("0", onClick = { onButtonClick("0") })
                CustomButton(".", onClick = { onDecimalClick() })
                CustomButtonOperators("+", onClick = { onButtonClick("+") })
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CustomButtonEqual("=", onClick = { onEqualsClick() })
            }
        }
    }
}
// Création du style du bouton de base
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 60.dp,
    height: Dp = 60.dp,
    containerColor: Color = Color(186 / 255f, 201 / 255f, 195 / 255f),
    contentColor: Color = Color.White,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = contentColor
        )
    }
}

// Ajout des particularités
@Composable
fun CustomButtonDouble(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        width = 136.dp,
    )
}
@Composable
fun CustomButtonOperators(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        containerColor = Color(74 / 255f, 78 / 255f, 76 / 255f)
    )
}
@Composable
fun CustomButtonEqual(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        width = 288.dp,
        containerColor = Color(26 / 255f, 146 / 255f, 0 / 255f)
    )
}
@Composable
fun CustomButtonErase(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        containerColor = Color(135 / 255f, 145 / 255f, 141 / 255f),
    )
}
// Fonction pour le calcul
@SuppressLint("DefaultLocale")
fun evaluateExpression(expression: String): String {
    val tokens = expression.replace("÷", "/").toCharArray()
    val values = Stack<Double>()
    val ops = Stack<Char>()
    var i = 0

    // Parcours de chaque caractère de l'expression
    while (i < tokens.size) {
        when {
            // Gestion des nombres, y compris les nombres négatifs
            tokens[i] in '0'..'9' || tokens[i] == '.' ||
            (tokens[i] == '-' && (i == 0 || tokens[i - 1] in "+-*/(")) -> {
                val sbuf = StringBuilder()
                if (tokens[i] == '-') sbuf.append(tokens[i++]) // Gérer le signe négatif
                while (i < tokens.size && (tokens[i] in '0'..'9' || tokens[i] == '.')) {
                    sbuf.append(tokens[i++])
                }
                values.push(sbuf.toString().toDouble())
                i-- // Revenir en arrière pour éviter de manquer le prochain caractère
            }

            tokens[i] == '(' -> ops.push(tokens[i])  // Gestion des parenthèses
            tokens[i] == ')' -> {
                // Résoudre les parenthèses en appliquant les opérations à l'intérieur
                while (ops.peek() != '(') values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                ops.pop()  // Retirer la parenthèse ouverte
            }
            tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' -> {
                // Si le caractère est une opération, appliquer les règles de priorité
                while (ops.isNotEmpty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                }
                ops.push(tokens[i])  // Ajouter l'opération à la pile des opérateurs
            }
        }
        i++
    }

    // Appliquer les opérations restantes
    while (ops.isNotEmpty()) {
        values.push(applyOp(ops.pop(), values.pop(), values.pop()))
    }

    // Résultat final
    val result = values.pop()

    // Si le résultat est un entier, retourner sans décimales
    return if (result == result.toInt().toDouble()) {
        result.toInt().toString() // Entier, pas de virgule
    } else {
        // Si c'est un nombre à virgule, formater le résultat pour avoir au maximum 10 chiffres après la virgule
        String.format("%.7f", result).trimEnd('0').trimEnd('.') // Limiter à 10 décimales et enlever les zéros inutiles
    }
}

// Vérifie la priorité des opérateurs
fun hasPrecedence(op1: Char, op2: Char): Boolean {
    return (op2 == '(' || op2 == ')').not() && (op1 == '*' || op1 == '/').not()
}

// Applique l'opérateur à deux valeurs
fun applyOp(op: Char, b: Double, a: Double): Double {
    return when (op) {
        '+' -> a + b
        '-' -> a - b
        '*' -> a * b
        '/' -> a / b
        else -> throw UnsupportedOperationException("Opérateur inconnu : $op")
    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    CalculatorScreen()
}