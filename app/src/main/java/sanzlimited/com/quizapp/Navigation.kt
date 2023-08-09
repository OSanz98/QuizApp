package sanzlimited.com.quizapp

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import sanzlimited.com.quizapp.presentation.screens.homeScreen
import sanzlimited.com.quizapp.presentation.screens.quizScreen
import androidx.compose.ui.res.stringResource

interface Destination {
    val route: String
    val title: Int
}
object Home : Destination {
    override val route: String = "home"
    override val title: Int = R.string.app_name
}
object Quiz: Destination {
    override val route: String = "quiz"
    override val title: Int = R.string.quiz
    const val routeArg: String = "category"
    val routeWithArg: String = "quiz/{$routeArg}"
    val arguments = listOf(navArgument("category"){ type = NavType.StringType })
    //fun getNavigationToQuiz(category: String) = "category/$category"
}


@Composable
fun navigationRouter() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Home.route,) {
        composable(Home.route) {
            customScaffold(appBarTitle = stringResource(id = Home.title), content = {
                homeScreen(navController)
            })
        }
        composable(route = Quiz.routeWithArg, arguments = Quiz.arguments){ navBackStackEntry ->
            customScaffold(appBarTitle = stringResource(id = Quiz.title), content = {
                quizScreen(category = navBackStackEntry.arguments?.getString("category"))
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customScaffold(appBarTitle: String, content: @Composable () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = appBarTitle)
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.Black
            ))
        },
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
        ){
            content()
        }
    }
}