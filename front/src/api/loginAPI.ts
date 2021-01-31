import axios from 'axios';

interface tokenInterface {
    data: {
        accessToken: string;
        refreshToken: string;
    }
}

function postLoginAsync(user: { email: string, passwd: string }) {
  return axios({
    method: 'post',
    url: '/api/login',
    data: {
      email: user.email,
      passwd: user.passwd,
    },
  }).then((result) => result.data);
}

export default postLoginAsync;
