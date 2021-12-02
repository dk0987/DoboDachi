package com.devdk.socialmedia.feature_post.presentation.feed_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.Post
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@ExperimentalMaterialApi
@Composable
fun Feed(
    navController: NavController
) {
   Column(
       modifier = Modifier
           .fillMaxSize()
           .padding(10.dp),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(horizontal = 8.dp),
           horizontalArrangement = Arrangement.SpaceBetween,
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text(
               text = stringResource(id = R.string.discover),
               fontWeight = FontWeight.Bold,
               fontSize = 35.sp ,
               color = primaryText ,
           )
           IconButton(
               onClick = {},
               Modifier
                   .clip(CircleShape)
                   .size(38.dp)
           ) {
               Image(
                   painter = painterResource(id = R.drawable.profile_pic),
                   contentDescription = stringResource(id = R.string.profile_pic),
                   contentScale = ContentScale.FillBounds
               )
           }
       }
       Spacer(modifier = Modifier.height(5.dp))
       LazyColumn(Modifier.fillMaxHeight(0.94f)){
           items(2){
               Post(
                   username = "Izuku Midoriya",
                   profilePic = painterResource(id = R.drawable.profile_pic),
                   postImage = painterResource(id = R.drawable.post_pic),
                   numberOfLike = 45,
                   numberOfComment = 7,
                   description = "used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret"
               )
               Post(
                   username = "Yuji Itadori",
                   profilePic = painterResource(id = R.drawable.profile_pic),
                   postImage = painterResource(id = R.drawable.image),
                   numberOfLike = 500,
                   numberOfComment = 80,
                   isLiked = true,
                   description = "used in various expressions indicating that a description or amount being stated is not exact a wry look, something between amusement and regret"
               )
           }
       }
   }
}