package sanzlimited.com.quizapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import sanzlimited.com.quizapp.presentation.screens.resultScreen

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
}

object Result: Destination {
    override val route: String = "result"
    override val title: Int = R.string.result
    const val routeArg: String = "userResult"
    val routeWithArg: String = "result/{$routeArg}"
    val arguments = listOf(navArgument("userResult"){ type = NavType.IntType })
}


@Composable
fun navigationRouter() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = Home.route,) {
        composable(Home.route) {
            customScaffold(appBarTitle = stringResource(id = Home.title), hasBackButton = false, content = {
                homeScreen(onNavigate = { value ->
                    navController.navigate(route = Quiz.route + "/$value")
                })
            })
        }
        composable(route = Quiz.routeWithArg, arguments = Quiz.arguments){ navBackStackEntry ->
            val argumentValue = navBackStackEntry.arguments?.getString(Quiz.routeArg)
            customScaffold(appBarTitle = stringResource(
                id = Quiz.title) + " - $argumentValue",
                hasBackButton = true,
                backButton = {
                     navController.popBackStack()
                },
                content = {
                    quizScreen(category = argumentValue, onNavigate = { value ->
                        navController.navigate(route = "${Result.route}/${value}")
                    })
                }
            )
        }

        composable(route = Result.routeWithArg, arguments = Result.arguments) { navBackStackEntry ->
            val argumentValue = navBackStackEntry.arguments?.getInt(Result.routeArg)
            customScaffold(appBarTitle = stringResource(id = Result.title), hasBackButton = false, content = {
                resultScreen(result = argumentValue, onBackHome = {
                    navController.navigate(route = Home.route)
                })
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customScaffold(appBarTitle: String, content: @Composable () -> Unit, hasBackButton: Boolean, backButton: () -> Unit = {}){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = appBarTitle)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    if (hasBackButton) {
                        IconButton(onClick = { backButton() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    } else {
                        null
                    }
                }
            )
        },
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
        ){
            content()
        }
    }
}