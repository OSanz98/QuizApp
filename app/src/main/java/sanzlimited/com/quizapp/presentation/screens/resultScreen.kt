package sanzlimited.com.quizapp.presentation.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun resultScreen(result: Int?, onBackHome: () -> Unit) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (result != null) {
            Text(text = when {
                result <= 50 -> {
                    "You got $result\nUnlucky! Keep trying, you'll do better next time"
                }

                result in 51..75 -> {
                    "You got $result\nGood Job! You can still do better!"
                }

                result > 75 -> {
                    "You got $result\nWell done! You passed the quiz!"
                }

                else -> {
                    "There was an error in calculating you score sorry!"
                }
            })
        }

        Button(modifier = Modifier, onClick = onBackHome){
            Text(text = "Done")
        }
    }
}