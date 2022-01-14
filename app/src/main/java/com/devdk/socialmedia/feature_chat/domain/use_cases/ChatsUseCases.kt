package com.devdk.socialmedia.feature_chat.domain.use_cases

data class ChatsUseCases(
    val getChatsUseCase: GetChatsUseCase,
    val getFollowingsForChatUseCase: GetFollowingsForChatUseCase ,
    val getMessageUseCase: GetMessageUseCase,
    val initializeRepositoryUseCase: InitializeRepositoryUseCase,
    val observeChatEventsUseCase: ObserveChatEventsUseCase,
    val observeMessageUseCase: ObserveMessageUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val userOnlineUseCase: UserOnlineUseCase ,
    val userOfflineUseCase: UserOfflineUseCase
)
