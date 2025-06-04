package com.example.deliciousfood.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.deliciousfood.R
import com.example.deliciousfood.domain.model.RecipesItem
import com.example.deliciousfood.ui.detail.RecipeDetails
import com.example.deliciousfood.ui.favorite.FavoriteScreen
import com.example.deliciousfood.ui.home.DestinationBar
import com.example.deliciousfood.ui.list.ListScreen
import com.example.deliciousfood.ui.search.SearchScreen
import com.example.deliciousfood.ui.utils.backHandler

sealed class NavScreens(val route: String) {
    data object MAIN : NavScreens("main")
    data object DETAIL : NavScreens("detail")
}

@Composable
fun NavGraph(startDestination: NavScreens = NavScreens.MAIN) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val selectedTab = remember { mutableStateOf(BottomNavTabs.LIST) }
    NavHost(navController = navController , startDestination = startDestination.route) {
        composable(
            route = NavScreens.MAIN.route
        ) { NavScreen(actions = actions , selectedTab) }

        composable(
            route = "${NavScreens.DETAIL.route}/{id}" ,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { RecipeDetails(viewModel = hiltViewModel() , navController = navController) }
    }
}


enum class BottomNavTabs(@StringRes val label: Int, val icon: Int) {
    LIST(R.string.recipes_tab, R.drawable.ic_recipes),
    SEARCH(R.string.search, R.drawable.ic_search), // Add this line
    FAVORITE(R.string.feature_tab, R.drawable.ic_heart),
}

@Composable
fun NavScreen(
    actions: MainActions ,
    selectedTab: MutableState<BottomNavTabs>
) {
    Scaffold(
        topBar = {
            DestinationBar()
        } ,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding(),
                containerColor = MaterialTheme.colorScheme.onBackground
            ) {
                for (tab in BottomNavTabs.entries) {
                    NavigationBarItem(
                        selected = selectedTab.value == tab ,
                        onClick = {
                            if (selectedTab.value == tab) return@NavigationBarItem
                            selectedTab.value = tab
                        } ,
                        icon = {
                            Icon(
                                painterResource(id = tab.icon) ,
                                contentDescription = ""
                            )
                        } ,
                        label = {
                            Text(
                                text = stringResource(id = tab.label) ,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } ,
                        alwaysShowLabel = false ,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = Color.LightGray,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = Color.LightGray,
                        )
                       //  alwaysShowLabel = false ,
                       // unselectedContentColor = Color.LightGray ,
                       // selectedContentColor = MaterialTheme.colorScheme.primary ,
                    )
                }
            }
        } ,
    ) {
        val modifier = Modifier.padding(it)
        when (selectedTab.value) {
            BottomNavTabs.LIST -> ListScreen(
                hiltViewModel() ,
                actions.moveDetail ,
                modifier
            )
            BottomNavTabs.SEARCH -> SearchScreen( // Add this case
                hiltViewModel(),
                actions.moveDetail,
                modifier
            )
            BottomNavTabs.FAVORITE -> FavoriteScreen(
                hiltViewModel() ,
                actions.moveDetail ,
                modifier ,
            )
        }
    }

    backHandler(
        enabled = selectedTab.value != BottomNavTabs.LIST ,
        onBack = { selectedTab.value = BottomNavTabs.LIST }
    )
}

class MainActions(navController: NavHostController) {

    val moveDetail: (RecipesItem) -> Unit = { recipe ->
        navController.navigate("${NavScreens.DETAIL.route}/${recipe.id}")
    }
}
