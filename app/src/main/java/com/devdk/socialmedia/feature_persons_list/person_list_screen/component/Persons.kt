package com.devdk.socialmedia.feature_persons_list.person_list_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.domain.modal.PersonList
import com.devdk.socialmedia.core.presentation.components.StandardFollowButton
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@ExperimentalMaterialApi
@Composable
fun PersonItem(
    person: PersonList,
    elevation : Dp = 10.dp,
    roundedCornerShape : Dp = 15.dp,
    profile_pic_size : Dp = 45.dp,
    OnClick : () -> Unit = {},
    OnFollow : () -> Unit = {}
) {
    Card(
        onClick = OnClick ,
        elevation = elevation ,
        shape = RoundedCornerShape(roundedCornerShape),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        backgroundColor = container
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp) ,
            horizontalArrangement = if (person.isUser) Arrangement.Start else Arrangement.SpaceAround ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = OnClick,
                    Modifier
                        .clip(CircleShape)
                        .size(profile_pic_size)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = person.userProfile,
                            builder = {
                                crossfade(true)
                            }),
                        contentDescription = stringResource(id = R.string.profile_pic),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text =  person.username ,
                        color = primaryText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable(
                                onClick = OnClick
                            )
                            .fillMaxWidth(0.7f)
                    )
                }
            }
            if (!person.isUser){
                StandardFollowButton(isFollowing = person.isFollowing , OnClick = OnFollow)
            }
            println("IsUser ${person.isUser}")

        }
    }
}