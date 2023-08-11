package sanzlimited.com.quizapp.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import sanzlimited.com.quizapp.data.model.Answers
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
    Surface(modifier = Modifier.fillMaxSize()) {
        when (quizResource){
            is Resource.Loading -> {
                loadingAnimation()
            }
            is Resource.Success -> {
                (quizResource as Resource.Success).data?.let {
                    quizSuccess(category, it)
                }
            }
            is Resource.Error -> {
//                TODO: Display error message here
            }
            else -> {
//                TODO: Display error message here
            }
        }
    }

}


@Composable
fun quizSuccess(category: String?, questions: Question){
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val selectedAnswers = remember {
        mutableStateMapOf<Int, String>()
    }
//    keep track of current question so can move onto the next
    val currentQuestionItem = remember {
        mutableStateOf(questions[currentQuestionIndex])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        LinearProgressIndicator(
            progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        currentQuestionItem?.let { questionItem ->
            questionCard(
                questionText = questionItem.value.question,
                answers = questionItem.value.answers,
                selectedAnswer = selectedAnswers[currentQuestionIndex],
                onAnswerSelected = { answer -> selectedAnswers[currentQuestionIndex] = answer },
                isCurrentQuestion = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
            } else {
                //TODO: Carry out functionality for when questionnaire is complete
            }
        }) {
            Text(text = if (currentQuestionIndex == questions.size - 1) "Finish" else "Next")
        }
    }
}

@Composable
fun questionCard(questionText: String, answers: Answers, selectedAnswer: String?, onAnswerSelected: (String) -> Unit, isCurrentQuestion: Boolean){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentQuestion) 8.dp else 0.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = questionText)
            Spacer(modifier = Modifier.height(8.dp))
            answers.javaClass.declaredFields.forEach { field ->
                val answer = field.get(answers) as? String
                if (answer != null){
                    answerOption(
                        answer = answer,
                        selected = answer == selectedAnswer,
                        onAnswerSelected = onAnswerSelected,
                        enabled = isCurrentQuestion
                    )
                }
            }

        }
    }
}

@Composable
fun answerOption(answer: String, selected: Boolean, onAnswerSelected: (String) -> Unit, enabled: Boolean){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = answer, modifier = Modifier.weight(1f))
            RadioButton(selected = selected, onClick = {
                onAnswerSelected(answer)
            }, enabled = enabled)
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