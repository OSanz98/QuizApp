package sanzlimited.com.quizapp

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object QuizScreen : Screen("quiz")
}
