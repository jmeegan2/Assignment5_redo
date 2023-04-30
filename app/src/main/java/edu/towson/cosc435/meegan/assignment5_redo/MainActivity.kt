package edu.towson.cosc435.meegan.assignment5_redo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import edu.towson.cosc435.meegan.assignment5_redo.ui.theme.Assignment5_redoTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment5_redoTheme {
               ImageGrid()
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

@Composable //for testing of circular progress indicator
fun DelayedImage(
    url: String,
    delayMillis: Long,
    content: @Composable (String) -> Unit
) {
    var imageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(url) {
        delay(delayMillis)
        imageUrl = url
    }

    if (imageUrl != null) {
        content(imageUrl!!)
    }
}


@Composable
fun ImageGrid() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp)
    ) {
        items(20) { index ->
            val imageUrl = "https://picsum.photos/200/200?random=$index"

            Box(
                modifier = Modifier.aspectRatio(1f)
                    .clickable { }
            ) {
                DelayedImage(url = imageUrl, delayMillis = 100) { delayedImageUrl ->
                    SubcomposeAsyncImage(
                        model = delayedImageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun ImageDetailScreen(imageUrl: String) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl.replace("200", "800")),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}