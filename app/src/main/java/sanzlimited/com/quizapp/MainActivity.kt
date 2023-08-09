package sanzlimited.com.quizapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import sanzlimited.com.quizapp.presentation.viewmodel.QuestionsViewModel
import sanzlimited.com.quizapp.ui.theme.QuizAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                navigationRouter()
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