export interface questionInterface {
  questionId: string,
  title: string,
  writeDate: string,
  email: string,
}

export interface getQuestionInterface {
    data: questionInterface,
    totalPage: number,
  }

export interface commentInterface {
  commentId: string,
  content: string,
  writeDate: string,
  nickname: string,
}

export interface getQuestionDetailInterface {
  data: {
    questionId: string,
    title: string,
    content: string,
    comments: commentInterface[],
  },
  totalPage: number,
}
