import { createSlice } from '@reduxjs/toolkit';
import { questionInterface } from '../../../interfaces/Question.inteface';

const initialquestionList: questionInterface[] = [];
const initailQuestDetail = {
  questionId: '',
  title: '',
  content: '',
  comments: [],
  totalPage: 0,
};

const questionSlice = createSlice({
  name: 'question',
  initialState: {
    questionList: initialquestionList,
    totalPage: 0,
    detailId: null,
    questionDetail: initailQuestDetail,
    isGetQuestionLoading: false,
    isGetQuestionSucceed: false,
    isGetQuestionError: null,
    isGetQuestionDetailLoading: false,
    isGetQuestioDetailSucceed: false,
    isGetQuestionDetailError: null,
  },
  reducers: {
    getQuestionRequest: (state, action) => {
      state.isGetQuestionLoading = true;
      state.isGetQuestionSucceed = false;
      state.isGetQuestionDetailError = null;
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
    setDeatilId: (state, action) => {
      state.detailId = action.payload;
    },
    getQuestionDetailRequest: (state, action) => {
      state.isGetQuestionDetailLoading = true;
      state.isGetQuestioDetailSucceed = false;
      state.isGetQuestionDetailError = null;
    },
    getQuestionDetailRequestSuccess: (state, action) => {
      state.isGetQuestionDetailLoading = false;
      state.isGetQuestioDetailSucceed = true;
      state.questionDetail = action.payload.data;
      state.totalPage = action.payload.totalPage;
    },
    getQuestionDetailRequestError: (state, action) => {
      state.isGetQuestionDetailLoading = false;
      state.isGetQuestionDetailError = action.payload;
    },
  },
});

export const {
  getQuestionRequest,
  getQuestionRequestSuccess,
  getQuestionRequestError,
  setDeatilId,
  getQuestionDetailRequest,
  getQuestionDetailRequestSuccess,
  getQuestionDetailRequestError,
} = questionSlice.actions;

export default questionSlice.reducer;
