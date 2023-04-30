package edu.towson.cosc435.meegan.assignment5_redo

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable //for testing of circular progress indicator
fun ImageDisplay(
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