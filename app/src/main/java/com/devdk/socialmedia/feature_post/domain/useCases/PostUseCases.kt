package com.devdk.socialmedia.feature_post.domain.useCases

data class PostUseCases(
    val getPostUseCase: GetPostUseCase ,
    val addPostUseCase: AddPostUseCase ,
    val deletePostUseCase: DeletePostUseCase ,
    val getPostByIdUseCase: GetPostByIdUseCase,
    val getProfilePic: GetProfilePic,
    val getPostForUserUseCase: GetPostForUserUseCase
)