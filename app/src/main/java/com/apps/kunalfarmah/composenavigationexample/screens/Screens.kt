package com.apps.kunalfarmah.composenavigationexample.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.result.launch
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apps.kunalfarmah.composenavigationexample.components.BottomTabBar
import com.apps.kunalfarmah.composenavigationexample.navigators.BottomNavigator
import com.apps.kunalfarmah.composenavigationexample.routes.LoginResponse
import com.apps.kunalfarmah.composenavigationexample.routes.Screens
import com.apps.kunalfarmah.composenavigationexample.viewModel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLogin: (data: LoginResponse) -> Unit, goToRegister: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onLogin(
                    LoginResponse(
                        token = "token", userId = 1
                    )
                )
            }) {
            Text("Login")
        }

        Button(
            onClick = {
                goToRegister()
            }) {
            Text("Register")
        }
    }
}

@Composable
fun RegisterScreen(onRegister: (data: LoginResponse) -> Unit, goToLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onRegister(
                    LoginResponse(
                        token = "token", userId = 1
                    )
                )
            }

        ) {
            Text("Register")
        }

        Button(
            onClick = {
                goToLogin()
            }) {
            Text("Login")
        }
    }
}

/**
 * Composable functions can have the same parameter type as their Type param for their route
 * Here HomeScreen is defined with Home as route
 */
@Composable
fun HomeScreen(homeData: Screens.Home, goToTabs: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        Text(text = "Token: ${homeData.token}")
        Text(text = "UserId: ${homeData.userId}")
        Button(
            onClick = {
                goToTabs()
            }) {
            Text("Go to Tabs")
        }

        Button(
            onClick = {
                onBack()
            }) {
            Text("Go back")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberSaveablePagerState(pageCount: () -> Int): PagerState {
    val initialPage = rememberSaveable { mutableStateOf(0) }
    val pagerState = rememberPagerState(
        initialPage = initialPage.value,
        pageCount = pageCount
    )
    LaunchedEffect(pagerState.currentPage) {
        initialPage.value = pagerState.currentPage
    }
    return pagerState
}

data class Tabs(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)

val topTabs = listOf(
    Tabs("All", Icons.Outlined.Home, Icons.Filled.Home),
    Tabs("Prepaid", Icons.Outlined.Phone, Icons.Filled.Phone),
    Tabs("Profile", Icons.Outlined.Person, Icons.Filled.Person),
    Tabs("Track\nRequests", Icons.Outlined.Build, Icons.Filled.Build),
    Tabs("Cart", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart)
)

@Composable
fun TopPagerScreen1(mainViewModel: MainViewModel) {
    val pagerState = rememberSaveablePagerState(pageCount = { topTabs.size })
    val coroutineScope = rememberCoroutineScope()


    // --- Animation Logic for Initial Right-to-Left Slide ---
    var hasAnimatedIn by rememberSaveable { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp // Get screen width

    // The initial offset will be the screen width (to place it offscreen to the right)
    // The target offset will be 0.dp (to place it onscreen)
    val offsetX by animateDpAsState(
        targetValue = if (hasAnimatedIn) 0.dp else screenWidth,
        animationSpec = tween(durationMillis = 500, delayMillis = 100), // Adjust duration/delay
        label = "TabRowOffsetX"
    )

    // Trigger the animation once when this composable enters the composition
    LaunchedEffect(Unit) {
        if (!hasAnimatedIn) {
            hasAnimatedIn = true
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = Color(0xFFECEDFF)), horizontalAlignment = Alignment.Start) {
        ScrollableTabRow(modifier = Modifier.offset(x = offsetX), containerColor = Color(0xFFECEDFF), divider = {null}, edgePadding = 20.dp, selectedTabIndex = pagerState.currentPage) {
            topTabs.forEachIndexed { index, tab: Tabs ->
                Tab(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(85.dp)
                        .width(95.dp),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = Color.Black,
                    text = { Text(text = tab.title, fontSize = 12.sp) },
                    icon = { Icon(imageVector = if(pagerState.currentPage == index) tab.selectedIcon else tab.unselectedIcon, contentDescription = null) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) {

            val lazyListState = rememberLazyListState()
            var previousFirstVisibleItemIndex by remember { mutableIntStateOf(lazyListState.firstVisibleItemIndex) }
            var previousFirstVisibleItemScrollOffset by remember { mutableIntStateOf(lazyListState.firstVisibleItemScrollOffset) }

            // LaunchedEffect to detect scroll direction
            LaunchedEffect(lazyListState) {
                snapshotFlow { // Create a flow of pairs (index, offset)
                    Pair(lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset)
                }
                    .collect { (currentIndex, currentOffset) ->
                        val direction = when {
                            currentIndex > previousFirstVisibleItemIndex -> 1
                            currentIndex < previousFirstVisibleItemIndex -> 0
                            // Indices are the same, compare offsets
                            currentOffset > previousFirstVisibleItemScrollOffset -> 1
                            currentOffset < previousFirstVisibleItemScrollOffset -> 0
                            else -> -1 // Or keep previous direction if no change
                        }

                       direction.let{
                           Log.d("Direction", "$direction")
                           if(it == 0){
                               mainViewModel.expandTopAppBar()
                               mainViewModel.expandBottomNav()
                           }else if( it == 1){
                               mainViewModel.collapseBottomNav()
                               mainViewModel.collapseTopAppBar()
                           }
                       }
                        // Update previous values for the next comparison
                        previousFirstVisibleItemIndex = currentIndex
                        previousFirstVisibleItemScrollOffset = currentOffset
                    }
            }

            // Content for each page
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(state = lazyListState) {
                    items(100) {
                        Text(modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, color = MaterialTheme.colorScheme.primary), text = "Item $it : ${topTabs[pagerState.currentPage]}")
                    }
                }

            }
        }
    }
}

@Composable
fun TopPagerScreen2(mainViewModel: MainViewModel) {
    val pagerState = rememberSaveablePagerState(pageCount = { topTabs.size })
    val coroutineScope = rememberCoroutineScope()


    // --- Animation Logic for Initial Right-to-Left Slide ---
    var hasAnimatedIn by rememberSaveable { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp // Get screen width

    // The initial offset will be the screen width (to place it offscreen to the right)
    // The target offset will be 0.dp (to place it onscreen)
    val offsetX by animateDpAsState(
        targetValue = if (hasAnimatedIn) 0.dp else screenWidth,
        animationSpec = tween(durationMillis = 500, delayMillis = 100), // Adjust duration/delay
        label = "TabRowOffsetX"
    )

    // Trigger the animation once when this composable enters the composition
    LaunchedEffect(Unit) {
        if (!hasAnimatedIn) {
            hasAnimatedIn = true
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(color = androidx.compose.material.MaterialTheme.colors.primary), horizontalAlignment = Alignment.Start) {
        ScrollableTabRow(modifier = Modifier.offset(x = offsetX), containerColor = androidx.compose.material.MaterialTheme.colors.primary, divider = {null}, edgePadding = 20.dp, indicator = {null}, selectedTabIndex = pagerState.currentPage) {
            topTabs.forEachIndexed { index, tab: Tabs ->
                Tab(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(85.dp)
                        .width(95.dp)
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            width = if (pagerState.currentPage == index) 2.dp else 0.dp,
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.onPrimary else Color.Transparent
                        ),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedContentColor = Color.LightGray,
                    text = { Text(text = tab.title, fontSize = 12.sp) },
                    icon = { Icon(imageVector = if(pagerState.currentPage == index) tab.selectedIcon else tab.unselectedIcon, contentDescription = null) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().background(color = Color(0xFFECEDFF))
        ) {

            val lazyListState = rememberLazyListState()
            var previousFirstVisibleItemIndex by remember { mutableIntStateOf(lazyListState.firstVisibleItemIndex) }
            var previousFirstVisibleItemScrollOffset by remember { mutableIntStateOf(lazyListState.firstVisibleItemScrollOffset) }

            // LaunchedEffect to detect scroll direction
            LaunchedEffect(lazyListState) {
                snapshotFlow { // Create a flow of pairs (index, offset)
                    Pair(lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset)
                }
                    .collect { (currentIndex, currentOffset) ->
                        val direction = when {
                            currentIndex > previousFirstVisibleItemIndex -> 1
                            currentIndex < previousFirstVisibleItemIndex -> 0
                            // Indices are the same, compare offsets
                            currentOffset > previousFirstVisibleItemScrollOffset -> 1
                            currentOffset < previousFirstVisibleItemScrollOffset -> 0
                            else -> -1 // Or keep previous direction if no change
                        }

                        direction.let{
                            Log.d("Direction", "$direction")
                            if(it == 0){
                                mainViewModel.expandTopAppBar()
                                mainViewModel.expandBottomNav()
                            }else if( it == 1){
                                mainViewModel.collapseBottomNav()
                                mainViewModel.collapseTopAppBar()
                            }
                        }
                        // Update previous values for the next comparison
                        previousFirstVisibleItemIndex = currentIndex
                        previousFirstVisibleItemScrollOffset = currentOffset
                    }
            }

            // Content for each page
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(state = lazyListState) {
                    items(100) {
                        Text(modifier = Modifier
                            .padding(10.dp)
                            .border(1.dp, color = MaterialTheme.colorScheme.primary), text = "Item $it : ${topTabs[pagerState.currentPage]}")
                    }
                }

            }
        }
    }
}



/**
 * Composable functions can have destructed params as their Type param for their route
 * Here DetailsScreen is defined with Detail as route but we directly take id from the Detail class
 */
@Composable
fun DetailsScreen(id: Long? = 0, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Details Screen")
        Text(text = "Id: $id")

        Button(
            onClick = {
                onBack()
            }) {
            Text("Go back")
        }
    }

}

@Composable
fun TabAScreen(onDetails: (Long?) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab A")
        Button(
            onClick = { onDetails(100) }
        ) {
            Text("Go To Details")
        }
    }
}

@Composable
fun TabBScreen(onDetails: (Long?) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab B")
        Button(
            onClick = { onDetails(200) }) {
            Text("Go To Details")
        }
    }
}

@Composable
fun TabCScreen(onDetails: (Long?) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tab C")
        Button(
            onClick = { onDetails(300) }) {
            Text("Go To Details")
        }
    }
}


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TabsScreen(navController: NavHostController, mainViewModel: MainViewModel) {
    val bottomTabsNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomTabBar(bottomTabsNavController, mainViewModel)
        }
    ) { paddingValues ->
        Column() {
           BottomNavigator(bottomTabsNavController, navController, mainViewModel)
        }
    }
}