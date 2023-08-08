package sanzlimited.com.quizapp.presentation.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import sanzlimited.com.quizapp.ui.theme.QuizAppTheme
import sanzlimited.com.quizapp.util.constants.Categories


@Composable
fun HomeScreen(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Categories.values().forEach {
                CustomCard(cardAction = {}){
                    Text(it.categoryName, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    cardAction: () -> Unit,
    elevation: Dp = 4.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
){
    Card(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .clickable { cardAction },
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            content()
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewCustomCard(){
    QuizAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Categories.values().forEach {
                    CustomCard(cardAction = {}){
                        Text(it.categoryName, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }

}