package com.devdk.socialmedia.core.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.onBackground
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.BottomNavigationItem

@Composable
fun StandardScaffold(
    isVisible : Boolean = true,
    roundedCornerShape: Dp = 30.dp,
    elevation :Dp = 10.dp ,
    scaffoldState: ScaffoldState,
    bottomNavigationItems : List<BottomNavigationItem> ,
    bottomNavigationItemColor : Color = onBackground,
    content : (@Composable () -> Unit ),
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (isVisible){
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation(
                    backgroundColor = container, modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = roundedCornerShape,
                                topEnd = roundedCornerShape
                            )
                        ),
                    elevation = elevation,
                    contentColor = bottomNavigationItemColor
                ) {
                    bottomNavigationItems.forEach { BottomNavigationItem ->
                        BottomNavigationItem(
                            selected = BottomNavigationItem.route == navController.currentDestination?.route,
                            onClick = {
                                   navController.navigate(BottomNavigationItem.route)
                            },
                            icon = { Icon(
                                imageVector = BottomNavigationItem.icon,
                                contentDescription = BottomNavigationItem.description
                            ) },
                            selectedContentColor = primaryText,
                            unselectedContentColor = bottomNavigationItemColor
                        )
                    }
                }
            }
        ) {
            content()
        }
    }
    else{
        content()
    }

}