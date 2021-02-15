export interface noticeInterface {
  title: string,
  writeDate: string,
  noticeId: string,
  description: string,
  active: boolean,
  content: string,
}

export interface getNoticeInterface {
  data: noticeInterface,
  totalPage: number,
}
