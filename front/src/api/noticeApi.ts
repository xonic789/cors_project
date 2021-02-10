import axios, { AxiosResponse } from 'axios';
import moment from 'moment';
import { getNoticeInterface } from '../interfaces/NoticeInterface';

export function getNoticeRequestAsync(page: number): Promise<getNoticeInterface> {
  return axios({
    method: 'get',
    url: '/api/notice',
    params: {
      size: 10,
      page: page || 1,
    },
  }).then((result) => result.data.data.map((item: { noticeId: string, title: string, writeDate: string }) => (
    {
      ...item,
      writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
      active: false,
    }
  )));
}
// export function getNoticeRequestAsync(page: number): Promise<getNoticeInterface> {
//   return axios({
//     method: 'get',
//     url: '/api/notice',
//     params: {
//       size: 10,
//       page: page || 1,
//     },
//   }).then((result) => {
//     const { data, lastPage } = result.data.data;
//     return {
//       data: data.map((item: { noticeId: string, title: string, writeDate: string }) => (
//         {
//           ...item,
//           writeDate: moment(item.writeDate).format('YYYY-MM-DD'),
//           active: false,
//         }
//       )),
//       lastPage,
//     };
//   });
// }

export function reomveNoticeRequestAsync(noticeId: string): Promise<AxiosResponse> {
  return axios({
    method: 'post',
    url: '/api/notice/delete',
    params: noticeId,
  });
}
