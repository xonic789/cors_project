import React from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { dummyPost } from '../../interfaces/mockdata';
import { addBookPostRequest } from './addPostSlice';

const AddPostWrapper = styled.div`
`;
const BookWrapper = styled.form`

`;
const FormWrapper = styled.form`

`;
const SearchInput = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
  background-color: #e9e9e9;
  width: 85%;
  margin: auto;
  & input {
    color: white;
    border:0;
    width:80%;
    background-color: inherit;
  }
  & img {
    width: 20px;
  }
`;
function AddPostPage():JSX.Element {
  const dispatch = useDispatch();
  const onSubmitPost = () => {
    dispatch(addBookPostRequest(dummyPost));
  };
  const onHandleUpload = (e: any) => {
    e.preventDefault();
    const img = e.target.files[0];
    const formData = new FormData();
    formData.append('file', img);
  };
  const onSubmit = (e: any) => {
    e.preventDefault();
  };
  return (
    <>
      <AddPostWrapper>
        <SearchInput>
          <input placeholder="책 이름을 검색해보세요!" />
          <img src="/images/icons/search.png" alt="search_icon" />
        </SearchInput>
        <BookWrapper>
          <div>{'국내도서>소설>한국소설>판타치소설'}</div>
          <img src="" alt="thumbnail" />
          <div>책제목</div>
          <div>원가: 10,000 원</div>
        </BookWrapper>
        <FormWrapper encType="multipart/form-data" onSubmit={onSubmit}>
          <input type="file" accept="image/jpg,impge/png,image/jpeg" name="file" onChange={onHandleUpload} />
          <input type="file" accept="image/jpg,impge/png,image/jpeg" name="file" onChange={onHandleUpload} />
          <input type="text" placeholder="가격입력" />
          <textarea placeholder="상품설명을 입력하세요" />
          <button type="button" onClick={onSubmitPost}>게시물 등록하기</button>
        </FormWrapper>
      </AddPostWrapper>
    </>
  );
}

export default AddPostPage;
