export interface noticeInterface {
  title: string,
  writeDate: string,
  noticeId: string,
  description: string,
  active: boolean,
}

export interface getNoticeInterface {
  data: noticeInterface,
  lastPage: number,
}
