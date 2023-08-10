package sanzlimited.com.quizapp.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import sanzlimited.com.quizapp.data.Resource
import sanzlimited.com.quizapp.data.model.Question
import sanzlimited.com.quizapp.presentation.viewmodel.QuestionsViewModel

@Composable
fun quizScreen(category: String?, viewModel: QuestionsViewModel = hiltViewModel()) {
    val quizResource by viewModel.quizData.observeAsState()
    LaunchedEffect(key1 = Unit) {
        if (category != null) {
            viewModel.getAllQuestions(category)
        }
    }

    when (quizResource){
        is Resource.Loading -> {
            loadingAnimation()
        }
        is Resource.Success -> {
            quizSuccess(category, (quizResource as Resource.Success).data)
        }
        is Resource.Error -> {

        }
        else -> {
            return
        }
    }
}


@Composable
fun quizSuccess(category: String?, questions: Question?){
    Surface(modifier = Modifier.fillMaxSize()) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            TODO: DISPLAY QUESTIONNAIRE AND KEEP TRACK WITH A PROGRESS BAR
            Text(text = category ?: "")
        }
    }
}

@Composable
fun loadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 25.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
    )
    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable, block = {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        })
    }
    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current){
        travelDistance.toPx()
    }
    val lastCircle = circleValues.size - 1
    Surface(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            circleValues.forEachIndexed { index, value ->
                Box(modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(color = circleColor, shape = CircleShape)
                )
                if(index != lastCircle) {
                    Spacer(modifier = Modifier.width(spaceBetween))
                }
            }
        }
    }

}