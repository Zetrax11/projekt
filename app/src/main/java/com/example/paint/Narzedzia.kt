package com.example.paint

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Tools(drawColor:MutableState<Color>,
          drawBrush:MutableState<Float>,
          usedColors:MutableSet<Color>){
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        WyborColoru(onColorSeleted = { color ->
            drawColor.value = color
        })

        Row(modifier = Modifier
            .horizontalGradientBackground(listOf(Color.Gray, Color.Black))
            .padding(8.dp, 4.dp)
            .horizontalScroll(rememberScrollState())
            .animateContentSize()){
            usedColors.forEach { 
                Icon(imageVector = Icons.Default.Anchor, contentDescription = null,
                    tint = it,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            drawColor.value = it
                        }
                )
            }
        }

        var showBrushes by remember {
            mutableStateOf(false)
        }
        val strokes = remember {
            (1..50 step 5).toList()
        }
        FloatingActionButton(onClick = { showBrushes = !showBrushes },
            modifier = Modifier.padding(vertical = 4.dp)){
            Icon(imageVector = Icons.Default.Brush,
                contentDescription = null,
                tint = drawColor.value)
        }
        AnimatedVisibility(visible = showBrushes) {
            LazyColumn{
                items(strokes){
                    IconButton(onClick = { drawBrush.value = it.toFloat()
                    showBrushes=false},
                        modifier = Modifier.padding(8.dp)
                            .border(border = BorderStroke(width = with(LocalDensity.current){it.toDp()},
                                color = Color.Gray),
                                shape = CircleShape)
                        ) {

                    }
                }
            }
        }
    }
}

fun Modifier.horizontalGradientBackground(
    colors:List<Color>
)=gradientBackGround(colors) {gradientColors, size ->
   Brush.horizontalGradient(
       colors = gradientColors,
       startX = 0f,
       endX = size.width
   )
}

fun Modifier.gradientBackGround(
    colors: List<Color>,
    brushProvider:(List<Color>, androidx.compose.ui.geometry.Size) -> Brush
):Modifier=composed {
    var size by remember { mutableStateOf(Size(0f, 0f)) }
    val gradient = remember (colors,size) { brushProvider(colors, size) }
    drawWithContent {
        size = this.size
        drawRect(brush = gradient)
        drawContent()
    }
}
