import axios, { AxiosResponse } from 'axios';

const getWishListAsync = (page: number): Promise<AxiosResponse> => {
  console.log(page);
  return axios({
    method: 'get',
    url: '/api/mypage/wishs',
    params: {
      page,
    },
  });
};

export default getWishListAsync;
