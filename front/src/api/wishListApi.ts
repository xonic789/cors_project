import axios, { AxiosResponse } from 'axios';

const getWishListAsync = (page: number): Promise<AxiosResponse> => {
  console.log(page);
  return axios({
    method: 'get',
    url: '/api/mypage/wishs',
    params: {
      page,
    },
  }).catch((error) => {
    if (error.response.status === 403) {
      throw new Error('NON_LOGIN');
    } else if (error.response.status === 500) {
      throw new Error('SERVER_ERROR');
    }
    return error;
  });
};

export default getWishListAsync;
