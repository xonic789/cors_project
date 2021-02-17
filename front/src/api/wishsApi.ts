import axios from 'axios';

export const addWishs = (articleId: string): Promise<boolean> => (
  axios({
    method: 'post',
    url: '/api/wish/save',
    params: {
      articleId,
    },
  }).then((res) => true).catch((error) => false)
);

export const removeWishs = (articleId: string): Promise<boolean> => (
  axios({
    method: 'post',
    url: '/api/wish/delete',
    params: {
      articleId,
    },
  }).then((res) => true).catch((error) => false)
);
