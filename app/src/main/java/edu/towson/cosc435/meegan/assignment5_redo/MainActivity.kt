package edu.towson.cosc435.meegan.assignment5_redo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import edu.towson.cosc435.meegan.assignment5_redo.ui.theme.Assignment5_redoTheme
import kotlinx.coroutines.delay
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Assignment5_redoTheme {
                NavHost(navController, startDestination = "imageGrid") {
                    composable("imageGrid") { ImageGrid(navController) }
                    composable("imageDetail/{imageUrl}") { backStackEntry ->
                        val imageUrl = backStackEntry.arguments?.getString("imageUrl")!!
                        ImageDetailScreen(imageUrl)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Assignment5_redoTheme {

    }
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

val imageIds = listOf(
   0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21
)

@Composable
fun ImageGrid(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp)
    ) {
        items(imageIds.size) { index ->
            val imageUrl = "https://picsum.photos/id/${imageIds[index]}/200/200"

            Box(
                modifier = Modifier.aspectRatio(1f)
                    .clickable {
                        navController.navigate("imageDetail/${Uri.encode(imageUrl)}")
                        Log.d("ImageGrid", "https://picsum.photos/id/${imageIds[index]}/200/200")
                        Log.d("ImageGrid", "imageUrl value: $imageUrl")
                    }
            ) {
                DelayedImage(url = imageUrl, delayMillis = 100) { delayedImageUrl ->
                    SubcomposeAsyncImage(
                        model = delayedImageUrl!!,
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
}

@Composable
fun ImageDetailScreen(imageUrl: String) {

    Box(modifier = Modifier.fillMaxSize()) {
        Log.d("ImageDetailScreen", "imageUrl value: $imageUrl")
        Image(

            painter = rememberAsyncImagePainter(model = imageUrl.replace("200", "800")),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}