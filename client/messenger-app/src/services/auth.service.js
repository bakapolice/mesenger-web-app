import api from './api';
import TokenService from './token.service';

class AuthService{
    login(user) {
        return api
            .post('/auth/login',{
                login: user.username,
                password: user.password
            })
            .then(response => {
                if(response.data.accessToken){
                    TokenService.setUser(response.data);
                }
                return response.data;
            })
    }
    logout(){
        TokenService.removeUser();
    }
    getValidate(){
        return api.get('/auth/validate');
    }
    register(user){
        return api
            .post('/auth', {
                login: user.username,
                password: user.password,
                firstName: user.firstName,
                lastName: user.lastName,
                email: user.email
            })
    }
}
export default new AuthService();