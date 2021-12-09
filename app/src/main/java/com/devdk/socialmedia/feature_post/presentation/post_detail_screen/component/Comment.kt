package com.devdk.socialmedia.feature_post.presentation.post_detail_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.MenuItems

@ExperimentalMaterialApi
@Composable
fun Comment(
    profileURL : Painter,
    username : String,
    timeStamp : Long,
    liked : Int =0,
    comment : String,
    onLike : () -> Unit = {},
    onProfile : () -> Unit = {},
    onDropDown : () -> Unit = {},
    isExpanded : Boolean = false,
    backgroundColor : Color = container,
    commentTextColor : Color = primaryText,
    elevation : Dp = 10.dp,
    roundedCornerShape : Dp = 15.dp,
    profile_pic_size : Dp = 38.dp,
    isLiked : Boolean = false,
    dropDownItem : List<String> = MenuItems.dropDown,
    isUser : Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        backgroundColor = backgroundColor ,
        elevation = elevation ,
        shape = RoundedCornerShape(roundedCornerShape)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 7.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onProfile,
                        Modifier
                            .clip(CircleShape)
                            .size(profile_pic_size)
                    ) {
                        Image(
                            painter = profileURL,
                            contentDescription = stringResource(id = R.string.profile_pic),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = username,
                            color = commentTextColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable(
                                onClick = onProfile
                            )
                        )
                        Text(
                            text = "4:00 P.M",
                            color = containerText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    IconButton(
                        onClick = onLike,
                        Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = stringResource(id = R.string.profile_pic),
                            tint = if (isLiked) Color.Red else containerText,
                        )
                    }
                    Text(
                        text = liked.toString(),
                        color = commentTextColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                    if (isUser){
                        IconButton(onClick = onDropDown) {
                            Icon(painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = stringResource(
                                    id = R.string.drop_down_menu
                                ),
                                tint = commentTextColor)
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = onDropDown,
                                modifier = Modifier.background(container)
                            ) {
                                dropDownItem.forEach { DropDownItem ->
                                    DropdownMenuItem(onClick = { /*TODO*/ }) {
                                        Text(
                                            text = DropDownItem,
                                            color = commentTextColor,
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = comment,
                color = commentTextColor,
                fontSize = 16.sp ,
                fontWeight = FontWeight.Normal ,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }

}











