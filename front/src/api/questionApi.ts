import axios, { AxiosResponse } from 'axios';
import moment from 'moment';
import { getQuestionInterface } from '../interfaces/Question.inteface';

export function getQuestionRequestAsync(page: number): Promise<getQuestionInterface> {
  return axios({
    method: 'get',
    url: '/api/mypage/quest',
    params: {
      page: page || 0,
    },
  }).then((result) => {
    const { questionBoardList, totalPage } = result.data.data;
    console.log(questionBoardList);
    console.log(typeof (questionBoardList));
    return {
      data: questionBoardList.map((item: { questionId: string, title: string, writeDate: string, email: string }) => (
        {
          ...item,
          writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
        }
      )),
      totalPage,
    };
  });
}

export function reomveQuestionRequestAsync(questionId: string): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/notice/delete',
    params: questionId,
  });
}
