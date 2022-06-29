import axiosInstanse from "./api";
import TokenService from "./token.service";
const setup = (store) => {
    axiosInstanse.interceptors.request.use(
        (config) => {
            const token = TokenService.getLocalAccessToken();
            /* eslint-disable */
            console.log(token);
            if (token) {
                config.headers["Authorization"] = 'Bearer ' + token;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );
    axiosInstanse.interceptors.response.use(
        (res) => {
            
            return res;
        },
        async (err) => {
            const originalConfig = err.config;
            /* eslint-disable */
            if(originalConfig.url !== "/auth/login" && err.response){
                //Access Token was expired
                if((err.response.status === 401) && !originalConfig._retry){
                    originalConfig._retry = true;
                    try{
                        const rs = await axiosInstanse.post("/auth/refresh", {
                            refreshToken: TokenService.getLocalRefreshToken(),
                        });
                        const accessToken = rs.data.accessToken;

                        store.dispatch('auth/refreshToken', accessToken);;
                        TokenService.updateLocalAccessToken(accessToken);
                        return axiosInstanse(originalConfig);
                    } catch(_error){
                        return Promise.reject(_error);
                    }
                }
            }
            return Promise.reject(err);
        }
    );
};
export default setup;