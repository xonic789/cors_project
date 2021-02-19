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
