package com.devdk.socialmedia.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.MenuItems
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@ExperimentalMaterialApi
@Composable
fun Post(
    username : String,
    timeStamp : Long = System.currentTimeMillis(),
    profilePic : Painter,
    profile_pic_size : Dp = 38.dp,
    postImage : Painter,
    onDropDown : () -> Unit = {},
    dropDownExpanded : Boolean = false,
    numberOfLike : Int,
    numberOfComment : Int,
    description : String,
    onPost : () -> Unit = {},
    onLike : () -> Unit = {},
    isLiked : Boolean = false,
    onComment : () -> Unit = {},
    onLikedText : () -> Unit = {},
    onShare : () -> Unit = {},
    onProfilePic : () -> Unit = {},
    roundedCornerShape : Dp = 15.dp,
    elevation : Dp = 10.dp,
    postCardColor : Color = container,
    postTextColor : Color = primaryText,
    dropDownItem : List<String> = MenuItems.dropDown,
    maxLines : Int = 3,
    isUser : Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        Card(
            onClick = onPost,
            elevation = 10.dp ,
            shape = RoundedCornerShape(roundedCornerShape),
            backgroundColor = postCardColor,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
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
                    Row(modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onProfilePic,
                            Modifier
                                .clip(CircleShape)
                                .size(profile_pic_size)
                        ) {
                            Image(
                                painter = profilePic,
                                contentDescription = stringResource(id = R.string.profile_pic),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = username,
                                color = postTextColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable(
                                    onClick = onProfilePic
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
                    if (isUser){
                        Row{
                            IconButton(onClick =  onDropDown ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = stringResource(
                                        id = R.string.drop_down_menu
                                    ),
                                    tint = postTextColor
                                )
                                DropdownMenu(
                                    expanded = dropDownExpanded,
                                    onDismissRequest = onDropDown,
                                    modifier = Modifier.background(container)
                                ) {
                                    dropDownItem.forEach { DropDownItem ->
                                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                                            Text(
                                                text = DropDownItem,
                                                color = postTextColor,
                                                fontSize = 14.sp ,
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = postImage,
                    contentDescription = stringResource(id = R.string.post_image),
                    contentScale = ContentScale.FillWidth ,
                    modifier = Modifier
                        .clip(RoundedCornerShape(roundedCornerShape))
                        .shadow(elevation)
                        .clickable(
                            onClick = onPost
                        )
                        .fillMaxSize()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row{
                        IconButton(onClick = onLike,  modifier = Modifier.size(25.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_heart),
                                contentDescription = stringResource(
                                    id = R.string.heart
                                ),
                                tint = if (isLiked) Color.Red else containerText,
                            )

                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(onClick = onComment,  modifier = Modifier.size(25.dp)) {
                            Icon(
                               imageVector = Icons.Filled.Comment,
                                contentDescription = stringResource(
                                    id = R.string.comment
                                ),
                                tint = containerText
                            )
                        }
                    }
                    IconButton(onClick = onShare,  modifier = Modifier.size(25.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = stringResource(
                                id = R.string.share
                            ),
                            tint = containerText
                        )

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = stringResource(id = R.string.liked_by)
                                + numberOfLike.toString() +" "
                                + stringResource(id = R.string.people),
                        fontWeight = FontWeight.SemiBold,
                        color = primaryText,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.clickable(
                            onClick = onLikedText
                        )
                    )
                    Text(
                        text =  numberOfComment.toString() + " " + stringResource(id = R.string.comment),
                        color = primaryText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.clickable(
                            onClick = onComment
                        )
                    )
                }
                Text(
                    text = description,
                    color = containerText,
                    fontSize = 16.sp ,
                    fontStyle = FontStyle.Italic,
                    maxLines = maxLines ,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
    
}

























