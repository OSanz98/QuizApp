package sanzlimited.com.quizapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import sanzlimited.com.quizapp.presentation.viewmodel.QuestionsViewModel
import sanzlimited.com.quizapp.ui.theme.QuizAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Quiz App")
                        },)
                    }
                ) { contentPadding ->
                    Box(modifier = Modifier.padding(contentPadding)){
                        QuizHome()
                    }
                }
            }
        }
    }
}

@Composable
fun QuizHome(viewModel: QuestionsViewModel = hiltViewModel()){
    Questions(viewModel = viewModel)
}


@Composable
fun Questions(viewModel: QuestionsViewModel){
    viewModel.getAllQuestions("Linux")
    val response = viewModel.data.value.data?.toMutableList()
    if(viewModel.data.value.loading == true) {
        Log.d("Loading", "Questions: .... Loading ....")
    } else {
        Log.d("Loading", "Questions: Loading stopped....")
        response?.forEach {
            Log.d("Result", "Questions: ${it.question}")
        }
    }
}