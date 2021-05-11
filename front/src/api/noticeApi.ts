import axios, { AxiosResponse } from 'axios';
import moment from 'moment';
import { getNoticeInterface } from '../interfaces/NoticeInterface';

// export function getNoticeRequestAsync(page: number): Promise<getNoticeInterface> {
//   return axios({
//     method: 'get',
//     url: '/api/notice',
//     params: {
//       size: 10,
//       page: page || 1,
//     },
//   }).then((result) => result.data.data.map((item: { noticeId: string, title: string, writeDate: string }) => (
//     {
//       ...item,
//       writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
//       active: false,
//     }
//   )));
// }
export function getNoticeRequestAsync(page: number): Promise<getNoticeInterface> {
  return axios({
    method: 'get',
    url: '/api/notice',
    params: {
      page: page || 0,
    },
  }).then((result) => {
    const { noticeList, totalPage } = result.data.data;
    console.log(noticeList);
    console.log(typeof (noticeList));
    return {
      data: noticeList.map((item: { noticeId: string, title: string, writeDate: string, active: boolean, content: string }) => (
        {
          ...item,
          writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
          active: false,
        }
      )),
      totalPage,
    };
  });
}

export function reomveNoticeRequestAsync(noticeId: string): Promise<AxiosResponse> {
  console.log(noticeId, 'test');
  return axios({
    method: 'delete',
    url: '/api/notice',
    params: { noticeId },
  });
}

export function modifyNoticeRequestAsync(notice: FormData): Promise<AxiosResponse> {
  return axios({
    method: 'put',
    url: '/api/notice',
    data: notice,
  });
}

export function addNoticeRequestAsync(notice: FormData): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/notice',
    data: notice,
  });
}
