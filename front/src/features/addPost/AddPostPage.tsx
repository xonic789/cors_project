import React from 'react';
import { useDispatch } from 'react-redux';
import { dummyPost } from '../../interfaces/mockdata';
import { addBookPostRequest } from './addPostSlice';

function AddPostPage():JSX.Element {
  const dispatch = useDispatch();
  const onSubmitPost = () => {
    dispatch(addBookPostRequest(dummyPost));
  };
  return (
    <button type="button" onClick={onSubmitPost}>게시물 등록하기</button>
  );
}

export default AddPostPage;
