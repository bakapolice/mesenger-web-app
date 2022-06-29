
class TokenService {
    /* eslint-disable */
    getLocalRefreshToken() {
        const user = JSON.parse(localStorage.getItem("user"));
        return user == null ? void 0 : user.refreshToken;
    }
    getLocalAccessToken() {
        const user = JSON.parse(localStorage.getItem("user"));
        return user == null ? void 0 : user.accessToken;
    }
    updateLocalAccessToken(token) {
        let user = JSON.parse(localStorage.getItem("user"));
        user.accessToken = token;
        localStorage.setItem("user", JSON.stringify(user));
    }
    updateLocalRefreshToken(token) {
        let user = JSON.parse(localStorage.getItem("user"));
        user.refreshToken = token;
        localStorage.setItem("user", JSON.stringify(user));
    }
    getUser() {
        return JSON.parse(localStorage.getItem("user"));
    }
    setUser(user) {
        //console.log(JSON.stringify(user));
        localStorage.setItem("user", JSON.stringify(user));
    }
    removeUser() {
        localStorage.removeItem("user");
    }
}
export default new TokenService();