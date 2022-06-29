import api from './chat-api';

class ServerService{
    getAllServers() {
        return api.get('/server/all');
    }
    getUserServers(userId){
        return api.get('/server/all/'+userId);
    }
    createServer(name, id) {
        return api.post('/server/create', {
            serverName: name,
            serverDescription: "Join Me!",
            ownerId: id
        })
    }
    getServerChats(serverId){
        return api.get('/server/chats/'+serverId);
    }
    serverJoin(sId, uId){
        return api.post('/server/join', 
        {
            serverId: sId,
            userId: uId
        })
    }
    getServerMembers(serverId){
        return api.get('server/'+serverId+'/users');
    }
}

export default new ServerService();