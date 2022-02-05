package ru.timofeeva.developerslife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.timofeeva.developerslife.ui.theme.DevelopersLifeTheme

class MainActivity : ComponentActivity() {
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
                    AppBar()
                    Tabs()
                    PostCard()
                    PostNavigationButtons()
                }
            }
        }
    }

    @Composable
    private fun PostNavigationButtons() {
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
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFFCDDC39),
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "previous post"
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            Surface(
                modifier = Modifier.shadow(16.dp, shape = CircleShape)
            ) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50),
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "next post"
                )
            }
        }
    }

    @Composable
    private fun ColumnScope.PostCard() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(top = 8.dp)
                .padding(horizontal = 8.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_baseline_person_24),
                    contentDescription = null
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
    private fun Tabs() {
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

