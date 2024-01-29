package com.example.paint

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingBar(onDelete:() -> Unit){
    TopAppBar(title = { Text("Paint")},
        actions = {
            IconButton(onClick = onDelete, content = {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            })
        })
}

@Composable
fun DrawBody(paths: MutableState<MutableList<PathState>>){
    Box(modifier = Modifier.fillMaxSize()){
        val drawColor = remember {
            mutableStateOf(Color.Black)
        }
        val drawBrush = remember {
            mutableStateOf(5f)
        }
        val usedColors= remember {
            mutableStateOf(mutableSetOf(Color.Black, Color.Red, Color.Gray))
        }
        paths.value.add(PathState(path = Path(), color = drawColor.value, stroke = drawBrush.value))

        DrawingCanvas(drawColor, drawBrush, usedColors, paths.value )
        Tools(drawColor, drawBrush, usedColors.value)
    }
}

@Composable
fun Paint(){
val paths = remember {
    mutableStateOf(mutableListOf<PathState>())
}

    Scaffold(topBar = { DrawingBar {
        paths.value = mutableListOf()
    }}) {
        DrawBody(paths)
    }
}

data class PathState(
    var path:Path,
    var color:Color,
    val stroke:Float
)