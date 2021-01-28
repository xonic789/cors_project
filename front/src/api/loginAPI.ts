import token from '../features/login/data/data';

interface tokenInterface {
    data: {
        accessToken: string;
        refreshToken: string;
    }
}

const postLoginAsync = async () => token;

export default postLoginAsync;
