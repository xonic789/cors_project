import { createSlice } from '@reduxjs/toolkit';
import { questionInterface } from '../../../interfaces/Question.inteface';

const initialquestionList: questionInterface[] = [];

const questionSlice = createSlice({
  name: 'question',
  initialState: {
    questionList: initialquestionList,
    totalPage: 0,
    isGetQuestionLoading: false,
    isGetQuestionSucceed: false,
    isGetQuestionError: null,
  },
  reducers: {
    getQuestionRequest: (state, action) => {
      state.isGetQuestionLoading = true;
    },
    getQuestionRequestSuccess: (state, action) => {
      state.isGetQuestionLoading = false;
      state.isGetQuestionSucceed = true;
      state.questionList = action.payload.data;
      state.totalPage = action.payload.totalPage;
    },
    getQuestionRequestError: (state, action) => {
      state.isGetQuestionLoading = false;
      state.isGetQuestionError = action.payload;
    },
  },
});

export const {
  getQuestionRequest,
  getQuestionRequestSuccess,
  getQuestionRequestError,
} = questionSlice.actions;

export default questionSlice.reducer;
