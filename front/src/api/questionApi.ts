import axios, { AxiosResponse } from 'axios';
import moment from 'moment';
import { getQuestionInterface, getQuestionDetailInterface, commentInterface } from '../interfaces/Question.inteface';

export function getQuestionRequestAsync(page: number): Promise<getQuestionInterface> {
  return axios({
    method: 'get',
    url: '/api/mypage/question',
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

export function getQuestionDetailRequestAsync(id: string): Promise<getQuestionDetailInterface> {
  return axios({
    method: 'post',
    url: '/api/question/view',
    params: {
      questionId: id,
      page: 0,
    },
  }).then((result) => {
    const { questionId, title, content, comment, totalPage } = result.data.data;

    return {
      data: {
        questionId,
        title,
        content,
        comments: comment.map((item: commentInterface) => (
          {
            ...item,
            writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
          }
        )),
      },
      totalPage,
    };
  });
}

export function addQuestionRequestAsync(questionInputs: { title: string, content: string }): Promise<AxiosResponse> {
  const { title, content } = questionInputs;
  return axios({
    method: 'post',
    url: '/api/question/save',
    params: {
      title,
      content,
    },
  }).catch((error) => error);
}

export function reomveQuestionRequestAsync(questionId: string): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/notice/delete',
    params: questionId,
  });
}
