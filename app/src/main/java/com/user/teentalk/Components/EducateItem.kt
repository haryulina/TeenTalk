package com.user.teentalk.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.user.teentalk.ui.theme.PoppinsFontFamily

@Composable
fun EducateItem(
    id : Int,
    judul : String,
    imageUrl : String,
    modifier: Modifier = Modifier
){
    Card(modifier = modifier
        .size(width = 350.dp, height = 250.dp)
        .shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp)
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)

    ){
        Column(
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
                    .padding(
                        top = 12.dp,
                        bottom = 12.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
                    .height(90.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column (modifier = Modifier.weight(2f)) {
                Text(
                    text = judul,
                    fontFamily = PoppinsFontFamily,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        top = 5.dp
                    )
                )
            }
        }
    }
}