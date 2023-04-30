package edu.towson.cosc435.meegan.assignment5_redo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.towson.cosc435.meegan.assignment5_redo.ui.theme.Assignment5_redoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

/// USE THIS: https://www.google.com/search?q=save+an+image+url+to+bitmap&oq=save+an+image+url+to+bitmap&aqs=chrome..69i57.4560j0j4&sourceid=chrome&ie=UTF-8#fpstate=ive&vld=cid:3c61c4f6,vid:xoOecJZ2Q-0
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Assignment5_redoTheme {
                NavHost(navController, startDestination = "image_grid") {
                    composable("image_grid") { ImageGrid(navController) }
                    composable("image_detail/{bitmap}") { backStackEntry ->
                        ImageDetailScreen(backStackEntry.arguments?.get("bitmap") as Bitmap)
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




//  columns = GridCells.Adaptive(minSize = 200.dp)
@Composable
fun ImageGrid(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp)
    ) {
        items(20) { index ->

            val imageUrl = "https://picsum.photos/200/200?random=$index"
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }
            var noImage by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = imageUrl) {
                bitmap = fetchImage(imageUrl)
                if (bitmap == null) {
                    noImage = true
                }
            }

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        print("U clicked this image")
                        bitmap?.let { imageBitmap ->
                            Log.d("IMAGE CLICKED VALUE :", imageBitmap.toString())
                        }
                    }
            ) {
                if (bitmap == null && !noImage) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (noImage) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                } else {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                    Log.d("BITMAP VALUE OF IMAGE:" , bitmap.toString())
                }
            }
        }
    }
}


private suspend fun fetchImage(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val inputStream = URL(url).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            null
        }
    }
}


@Composable
fun ImageDetailScreen(bitmap: Bitmap) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}