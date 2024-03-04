import axios from "axios";

const  USER_API_BASE_URL = "http://loclalhost:8000/api/login";

class UserService {
    validateUser(user){
        return axios.post(USER_API_BASE_URL, user);
    }
}

export default new UserService();