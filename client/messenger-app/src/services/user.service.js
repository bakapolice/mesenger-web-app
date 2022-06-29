import api from './api';

class UserService {
    getUserProfile(){
        return api.get("/user");
    }
    getUserFriends(){
        return api.get("/user/friends")
    }
    getUser(id){
        return api.get("/user/"+id)
    }
    
}
export default new UserService();