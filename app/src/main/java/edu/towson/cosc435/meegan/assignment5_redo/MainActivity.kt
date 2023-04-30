package edu.towson.cosc435.meegan.assignment5_redo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import edu.towson.cosc435.meegan.assignment5_redo.ui.theme.Assignment5_redoTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment5_redoTheme {
                SingleImage()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Assignment5_redoTheme {
        Greeting("Android")
    }
}

@Composable
fun SingleImage() {
    val imageUrl = "https://i.ibb.co/njkHJHs/70438902852-4-B295341-8-AAA-4678-AC5-E-2-C7-B56-C80-FF4.jpg"
    val painter: Painter = rememberAsyncImagePainter(imageUrl)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}