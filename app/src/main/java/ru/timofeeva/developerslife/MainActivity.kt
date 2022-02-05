package ru.timofeeva.developerslife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
                    Row {
                        Icon(
                            modifier = Modifier.background(
                                color = Color.LightGray,
                                shape = CircleShape
                            ),
                            painter = painterResource(id = R.drawable.ic_baseline_person_24),
                            tint = Color.White,
                            contentDescription = "Profile icon",
                        )
                        Text(text = "Developers Life")
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        PostScreen()
    }
}

