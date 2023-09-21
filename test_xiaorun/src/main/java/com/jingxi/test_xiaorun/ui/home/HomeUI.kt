package com.jingxi.test_xiaorun.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jingxi.test_xiaorun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUIMain(activityController: NavController){

    LocalFocusManager.current.clearFocus()

    val homeNavController = rememberNavController()

    val items = listOf(
        HomeBottomNavItem.HOME,
        HomeBottomNavItem.RECENT,
        HomeBottomNavItem.NEIGHBOR,
        HomeBottomNavItem.MINE,
    )

    Scaffold(bottomBar = {
        BottomNavigation(Modifier.height(98.dp), backgroundColor = Color.White){
            val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach{ page ->
                val isSelected = currentDestination?.hierarchy?.any{it.route == page.route} == true
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.Transparent, shape = RoundedCornerShape(48.dp))
                    /**
                     * 去除水波纹
                     * indication = null
                     */
                    .clickable(interactionSource = remember { MutableInteractionSource() },indication = null, onClick = {
                        homeNavController.navigate(page.route) {
                            popUpTo(homeNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Image(painter = painterResource(id = if(isSelected){page.selectIcon}else{page.unSelectIcon}), contentDescription = null,
                        Modifier
                            .size(48.dp))

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(text = page.label,color = colorResource(id = R.color.tv_gray_8591a3),fontSize = 20.sp)
                }
            }
        }
    }) { innerPadding ->
        NavHost(navController = homeNavController, startDestination = HomePage.HOME, Modifier.padding(innerPadding)){
            composable(HomePage.HOME){
                HomePageHome(activityNavController = activityController)
            }
            
            composable(HomePage.RECENT){
                HomePageRecent(activityNavController = activityController)
            }

            composable(HomePage.Neighbor){
                HomePageNeighbor(activityNavController = activityController)
            }

            composable(HomePage.Mine){
                HomePageMine(activityNavController = activityController)
            }
        }
    }
}

sealed class HomeBottomNavItem(val route: String, val selectIcon: Int, val unSelectIcon: Int,val label: String){
    object HOME : HomeBottomNavItem(HomePage.HOME,R.mipmap.icon_home_selected,R.mipmap.icon_home_unselect,label = "首页")
    object RECENT : HomeBottomNavItem(HomePage.RECENT,R.mipmap.icon_message_selected,R.mipmap.icon_message_unselect,label = "消息")
    object NEIGHBOR : HomeBottomNavItem(HomePage.Neighbor,R.mipmap.icon_neighbor_selected,R.mipmap.icon_neighbor_unselect,label = "邻里圈")
    object MINE : HomeBottomNavItem(HomePage.Mine,R.mipmap.icon_mine_selected,R.mipmap.icon_mine_unselect,label = "我的")
}