package sanzlimited.com.quizapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import sanzlimited.com.quizapp.Destinations.HOME_ROUTE
import sanzlimited.com.quizapp.Destinations.QUIZ_ROUTE

object Destinations {
    const val HOME_ROUTE = "home"
    const val QUIZ_ROUTE = "quiz"
}

@Composable
fun QuizAppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE,) {
        composable(HOME_ROUTE) {
//            onCategoryChosen = {
//                navController.navigate(QUIZ_ROUTE)
//            }
        }
    }
}