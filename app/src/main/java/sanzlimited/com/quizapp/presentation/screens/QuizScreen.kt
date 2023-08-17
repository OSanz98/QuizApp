package sanzlimited.com.quizapp.presentation.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import kotlin.reflect.KProperty1

@Composable
fun quizScreen(category: String?, viewModel: QuestionsViewModel = hiltViewModel()) {
    val quizResource by viewModel.quizData.observeAsState(initial = Resource.Loading())
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
                Log.d("QuizQuestions", "quizScreen: ${(quizResource as Resource.Success<Question>).data}")
                val questions = (quizResource as Resource.Success).data
                if (questions != null) {
                    quizSuccess(questions)
                }
            }
            is Resource.Error -> {
                val errorMessage = (quizResource as Resource.Error).message
                //TODO: Display error message here
            }
            else -> {
//                TODO: Display error message here
            }
        }
    }

}


@Composable
fun quizSuccess(questions: Question){
    //keep track of current question so can move onto the next
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val selectedAnswers = remember {
        mutableStateMapOf<Int, String>()
    }
    var isPopupVisible by remember { mutableStateOf(false) }
    var totalScore by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LinearProgressIndicator(
            progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            questionCard(
                questionText = questions[currentQuestionIndex].question,
                answers = questions[currentQuestionIndex].answers,
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
                totalScore = calculateQuizScore(questions, selectedAnswers)
                isPopupVisible = true

            }
        }) {
            Text(text = if (currentQuestionIndex == questions.size - 1) "Finish" else "Next")
        }
        //TODO: FIX app failing when calculating score and that
        customAnimatedPopup(
            isVisible = isPopupVisible,
            onDismiss = { isPopupVisible = false },
            message = when {
                totalScore <= 50 -> {
                    "You got $totalScore\nUnlucky! Keep trying, you'll do better next time"
                }
                totalScore in 51..75 -> {
                    "You got $totalScore\nGood Job! You can still do better!"
                }
                totalScore > 75 -> {
                    "You got $totalScore\nWell done! You passed the quiz!"
                }

                else -> {
                    "There was an error in calculating you score sorry!"
                }
            }
        )
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

            //If all answers are available better to use LazyColumn so user can scroll and see all answers
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ){
                item {
                    if (answers.answer_a != null && !answers.answer_a.isBlank()) {
                        answerOption(
                            answer = answers.answer_a,
                            selected = answers.answer_a == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                    if (answers.answer_b != null && !answers.answer_b.isBlank()) {
                        answerOption(
                            answer = answers.answer_b,
                            selected = answers.answer_b == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                    if (answers.answer_c != null && !answers.answer_c.isBlank()) {
                        answerOption(
                            answer = answers.answer_c,
                            selected = answers.answer_c == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                    if (answers.answer_d != null && !answers.answer_d.isBlank()) {
                        answerOption(
                            answer = answers.answer_d,
                            selected = answers.answer_d == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                    if (answers.answer_e != null && !answers.answer_e.isBlank()) {
                        answerOption(
                            answer = answers.answer_e,
                            selected = answers.answer_e == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                    if (answers.answer_f != null && !answers.answer_f.isBlank()) {
                        answerOption(
                            answer = answers.answer_f,
                            selected = answers.answer_f == selectedAnswer,
                            onAnswerSelected = onAnswerSelected,
                            enabled = isCurrentQuestion
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun answerOption(answer: String, selected: Boolean, onAnswerSelected: (String) -> Unit, enabled: Boolean){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
        onClick = {
            onAnswerSelected(answer)
        }
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

fun calculateQuizScore(question: Question, selectedAnswers: MutableMap<Int, String>) : Int {
    var totalQuestionsRight = 0
    val correctAnswers = extractCorrectAnswers(question)
    var currentIndex = 0
    selectedAnswers.forEach { (i, s) ->
        if (s == correctAnswers[currentIndex]) {
            totalQuestionsRight++
        }
        currentIndex++
    }
    return (totalQuestionsRight / question.size) * 100
}

fun extractCorrectAnswers(question: Question): ArrayList<String> {
    val extractedAnswers = arrayListOf<String>()
    var currentIndex = 0
    question.forEach { questionItem ->
        val correctAnswers = questionItem.correct_answers
        val answers = questionItem.answers
        if (correctAnswers.answer_a_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_a
        } else if (correctAnswers.answer_b_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_b
        } else if (correctAnswers.answer_c_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_c
        }else if (correctAnswers.answer_d_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_d
        } else if (correctAnswers.answer_e_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_e
        }else if (correctAnswers.answer_f_correct == "true") {
            extractedAnswers[currentIndex] = answers.answer_f
        }
        currentIndex++
    }
    return extractedAnswers
}


@Composable
fun customAnimatedPopup(isVisible: Boolean, onDismiss: () -> Unit, message: String) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = Modifier.fillMaxSize()
    ) {
        customPopup(isVisible = isVisible, onDismiss = onDismiss, message = message)
    }
}

@Composable
fun customPopup(isVisible: Boolean, onDismiss: () -> Unit, message: String) {
    if (isVisible) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(8.dp))
                .alpha(0.95f)
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { onDismiss }){
                    Text(text = "Complete")
                }
            }

        }
    }
}