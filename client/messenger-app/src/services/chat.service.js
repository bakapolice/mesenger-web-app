import api from './chat-api';

class ChatService {
    getChatId(senderId, recipientId) {
        return api.get('/chat/' + senderId + "/" + recipientId);
    }

    getChatMessages(senderId, recipientId) {
        return api.get('/msg/' + senderId + "/" + recipientId)
    }

    getChatMessagesByChatId(chatId) {
        return api.get('/msg/' + chatId);
    }

    getRecipientIdByChatAndSenderId(chatId, senderId){
        return api.get('/chat/' + chatId + "/" + senderId + "/recipient");
    }
    getDialogs(senderId){
        return api.get('/chat/dialogs/'+ senderId);
    }
    createChat(name, serverId, ownerId){
        return api.post('/chat/create', {
            chatName: name,
            chatDescription: null,
            serverId: serverId,
            ownerId: ownerId
        })
    }
    

}
export default new ChatService();