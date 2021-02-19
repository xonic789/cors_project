import axios, { AxiosResponse } from 'axios';

const getMyMarketListAsync = (page: number): Promise<AxiosResponse> => {
  console.log(page);
  return axios({
    method: 'get',
    url: '/api/mypage/markets',
    params: {
      page,
    },
  });
};

export default getMyMarketListAsync;
