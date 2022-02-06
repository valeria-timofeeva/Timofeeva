package ru.timofeeva.developerslife

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import ru.timofeeva.developerslife.models.Post
import ru.timofeeva.developerslife.models.Tab
import ru.timofeeva.developerslife.ui.theme.DevelopersLifeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .componentRegistry {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder(applicationContext))
                } else {
                    add(GifDecoder())
                }
            }
            .build()

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
                        currentPost = viewState.currentPost,
                        isLoading = viewState.isLoading,
                        hasError = viewState.hasError
                    )
                    PostNavigationButtons(
                        onNextClick = { viewModel.onNextPostClick() },
                        onPreviousCLick = { viewModel.onPreviousPostClick() },
                        isPreviousButtonActive = viewState.isPreviousButtonIsActive
                    )
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
                    tint = if (isPreviousButtonActive) Color(0xFFFFC107) else Color(0xFFB0B1A3),
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
    private fun ColumnScope.PostCard(
        currentPost: Post?,
        hasError: Boolean,
        isLoading: Boolean
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(color = Color.Red)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    hasError -> LoadingErrorView()
                    isLoading -> CircularProgressIndicator()
                    currentPost != null -> PostContent(currentPost = currentPost)
                }
            }
        }
    }

    @Composable
    private fun LoadingErrorView() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.ic_round_cloud_off_24),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.loading_error),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }

    @Composable
    private fun BoxScope.PostContent(currentPost: Post) {
        Image(
            painter = rememberImagePainter(
                data = currentPost.gifUrl.replace("http://", "https://"),
                imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = currentPost.text,
            color = Color.White,
            modifier = Modifier.Companion
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Gray
                        )
                    )
                )
                .padding(8.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()

        )

    }

    @Composable
    private fun Tabs(selectedTab: Tab) {
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            Modifier
                .height(48.dp)
                .background(color = Color.White)
        ) {
            Tab(
                selected = selectedTab == Tab.Recent,
                onClick = { /*TODO*/ }
            ) {
                Text(text = stringResource(R.string.tab_label_recent), color = Color.Black)
            }
            Tab(selected = selectedTab == Tab.Best, enabled = false, onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.tab_label_best), color = Color.LightGray)
            }
            Tab(selected = selectedTab == Tab.Hot, enabled = false, onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.tab_label_hot), color = Color.LightGray)
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
    fun ButtonsPreview() {
        Column {
            PostNavigationButtons()
        }
    }

    @Preview(showBackground = true, device = Devices.NEXUS_5X)
    @Composable
    fun PostCardPreview() {
        Column {
            PostCard(currentPost = null, hasError = true, isLoading = false)
        }
    }
}

