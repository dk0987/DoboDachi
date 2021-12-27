package com.devdk.socialmedia.core.presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.MenuItems
import com.devdk.socialmedia.core.presentation.util.TimeStampConverter
import com.devdk.socialmedia.feature_post.domain.modal.Post
import kotlin.time.ExperimentalTime

@ExperimentalCoilApi
@OptIn(ExperimentalTime::class)
@ExperimentalMaterialApi
@Composable
fun Post(
    post : Post,
    profile_pic_size : Dp = 38.dp,
    onPost : () -> Unit = {},
    onLike : () -> Unit = {},
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
    dropDownSelectedItem : (String) -> Unit = {},
    shouldShowActionRow : Boolean = true
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val size = 150.dp
    val animatedSize by animateDpAsState(
        targetValue = if (post.isLiked) size else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 500f
        )
    )
    val isVisible = animatedSize != size
    val timeStampConverter  = TimeStampConverter()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Card(
            onClick = onPost,
            elevation = elevation,
            shape = RoundedCornerShape(roundedCornerShape),
            backgroundColor = postCardColor,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 7.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onProfilePic,
                            Modifier
                                .clip(CircleShape)
                                .size(profile_pic_size)
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                   data = post.userProfileUrl ,
                                    builder = {
                                        crossfade(true)
                                    }
                                ),
                                contentDescription = stringResource(id = R.string.profile_pic),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = post.userName,
                                color = postTextColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.clickable(
                                    onClick = onProfilePic
                                )
                            )
                            Text(
                                text = timeStampConverter(post.timeStamp),
                                color = containerText,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light,
                            )
                        }
                    }
                    if (post.isUser) {
                        Row {
                            IconButton(onClick = {
                                expanded = !expanded
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = stringResource(
                                        id = R.string.drop_down_menu
                                    ),
                                    tint = postTextColor
                                )
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = {
                                        expanded = false
                                    },
                                    modifier = Modifier.background(container)
                                ) {
                                    dropDownItem.forEach { DropDownItem ->
                                        DropdownMenuItem(onClick = {
                                            dropDownSelectedItem(DropDownItem)
                                            expanded = false
                                        }) {
                                            Text(
                                                text = DropDownItem,
                                                color = postTextColor,
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
                Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                    Image(
                        painter = rememberImagePainter(data = post.postImageUrl , builder = {
                            crossfade(true)
                            size(OriginalSize)
                        }),
                        contentDescription = stringResource(id = R.string.post_image),
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 100.dp),
                    )
                    if (post.isLiked && isVisible){
                        Image(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "",
                            modifier = Modifier.size(animatedSize),
                            colorFilter = ColorFilter.tint(color = Color.Red)
                        )
                    }
                }
                post.description?.let {
                    if (it.isNotBlank()){
                        Text(
                            text = it,
                            color = containerText,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic,
                            maxLines = maxLines,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.liked_by)
                                + post.liked + " "
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
                        text = post.comment.toString() + " " + stringResource(id = R.string.comment),
                        color = primaryText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.clickable(
                            onClick = onComment
                        )
                    )
                }
                if (shouldShowActionRow){
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            IconButton(onClick = onLike, modifier = Modifier.size(25.dp)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_heart),
                                    contentDescription = stringResource(
                                        id = R.string.heart
                                    ),
                                    tint = if (post.isLiked) Color.Red else containerText,
                                )

                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(onClick = onComment, modifier = Modifier.size(25.dp)) {
                                Icon(
                                    imageVector = Icons.Filled.Comment,
                                    contentDescription = stringResource(
                                        id = R.string.comment
                                    ),
                                    tint = containerText
                                )
                            }
                        }
                        IconButton(onClick = onShare, modifier = Modifier.size(25.dp)) {
                            Icon(
                                imageVector = Icons.Filled.Share,
                                contentDescription = stringResource(
                                    id = R.string.share
                                ),
                                tint = containerText
                            )

                        }
                    }
                }


            }
        }
    }
}



























