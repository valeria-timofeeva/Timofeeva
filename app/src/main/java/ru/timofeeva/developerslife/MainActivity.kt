package ru.timofeeva.developerslife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import ru.timofeeva.developerslife.models.Tab
import ru.timofeeva.developerslife.ui.theme.DevelopersLifeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostScreen()
        }
    }

    @Composable
    fun PostScreen() {
        DevelopersLifeTheme {
            // A surface container using the 'background' color from the theme
            Surface(color = MaterialTheme.colors.background) {
                Column {
                    val viewState: ViewState =
                        viewModel.getState().observeAsState(ViewState()).value

                    AppBar()
                    Tabs(viewState.selectedTab)
                    PostCard(
                        viewState.currentPost,
                        viewState.isLoading
                    )
                    PostNavigationButtons()
                }
            }
        }
    }

    @Composable
    private fun PostNavigationButtons(
        onNextClick: () -> Unit = {},
        onPreviousCLick: () -> Unit = {},
        isPreviousButtonActive: Boolean = false
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.shadow(16.dp, shape = CircleShape)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onPreviousCLick, enabled = isPreviousButtonActive),
                    tint = if (isPreviousButtonActive) Color(0xFFCDDC39) else Color(0xFFB0B1A3),
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "previous post"
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Surface(
                modifier = Modifier.shadow(16.dp, shape = CircleShape)
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onNextClick),
                    tint = Color(0xFF4CAF50),
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "next post"
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.PostCard(currentPost: String, isLoading: Boolean) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
        ) {
            Box(contentAlignment = Alignment.Center) {
                GlideImage(
                    imageModel = "http://static.devli.ru/public/images/gifs/202109/518d3a2b-42eb-4b05-8e81-a5329a1d8288.gif",
                    contentScale = ContentScale.FillBounds,
                    circularReveal = CircularReveal(250),
                    shimmerParams = ShimmerParams(
                        Color.LightGray,
                        highlightColor = Color.Yellow
                    ),
                    error = "Ошибка при загрузке изображения"
                )
                Text(
                    text = "Text of post",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                )
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    private fun Tabs(selectedTab: Tab) {
        TabRow(selectedTabIndex = 0, Modifier.height(48.dp)) {
            Tab(selected = true, onClick = { /*TODO*/ }) {
                Text(text = "Последние")
            }
            Tab(selected = false, enabled = false, onClick = { /*TODO*/ }) {
                Text(text = "Лучшие")
            }
            Tab(selected = false, enabled = false, onClick = { /*TODO*/ }) {
                Text(text = "Горячие")
            }
        }
    }

    @Composable
    private fun AppBar() {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.background(
                    color = Color.LightGray,
                    shape = CircleShape
                ),
                painter = painterResource(id = R.drawable.ic_baseline_person_24),
                tint = Color.White,
                contentDescription = "Profile icon",
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Developers Life",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Preview(showBackground = true, device = Devices.NEXUS_5X)
    @Composable
    fun DefaultPreview() {
        PostScreen()
    }

    @Preview(showBackground = true, device = Devices.NEXUS_5X)
    @Composable
    fun ButtonsPreview() {
        Column {
            PostNavigationButtons()
        }
    }


}

