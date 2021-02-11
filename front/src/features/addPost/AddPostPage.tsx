import React, { useCallback, useRef, useState } from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { dummyPost } from '../../interfaces/mockdata';
import { addBookPostRequest } from './addPostSlice';
import ImagePreView from './ImagePreView';

const AddPostWrapper = styled.div`
  width: 90%;
  margin: 0 auto;
`;
const AddPostHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 0px;
`;
const Logo = styled.img`
  width: 40px;
`;
const DeleteImg = styled.img`
  width: 20px;
  align-self: flex-start;
`;
const BookWrapper = styled.div`
  display: flex;
  padding: 20px;
  & img {
    width: 30%;
    max-width: 200px;
  }
  border: 1px solid #e8e8e8;
  margin: 10px 0px;
`;
const BookDetail = styled.div`
  margin-left: 20px;
`;
const Category = styled.div`
  font-size: 12px;
  margin-bottom: 20px;
`;
const BookTitle = styled.div`
  font-size: 15px;
  margin-bottom: 20px;
  font-weight: 800;
`;
const BookPrice = styled.div`
  font-size: 15px;
  margin-bottom: 20px;
`;
const FormWrapper = styled.form`
`;
const BookDetailInputWrapper = styled.div`
  & input {
    margin: 20px 0;
    width: 100%;
    padding: 10px;
    border: 1px solid #e8e8e8;
  }
  & textArea {
    width: 100%;
    padding: 10px;
    border: 1px solid #e8e8e8;
    height: 80px;
    resize: none;
    overflow: scroll;
  }
`;
const AddPostButton = styled.button`
  width: 100%;
  border: 0;
  background-color: #3960a6;
  padding: 10px;
  margin: 20px 0px;
  border-radius: 5px;
  color: white;
  font-weight: 700;
`;
const SearchInput = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
  background-color: #e9e9e9;
  width: 100%;
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
  const [price, setPrice] = useState<number>();
  const dispatch = useDispatch();

  const onChangePrice = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(Number(e.target.value));
  };
  const onSubmitPost = () => {
    // dispatch(addBookPostRequest(dummyPost));
  };
  return (
    <>
      <AddPostWrapper>
        <AddPostHeader>
          <Logo src="/images/icons/logo.jpeg" alt="logo" />
          <DeleteImg src="/images/icons/x.png" alt="x_button" />
        </AddPostHeader>
        <SearchInput>
          <input placeholder="책 이름을 검색해보세요!" />
          <img src="/images/icons/search.png" alt="search_icon" />
        </SearchInput>
        <BookWrapper>
          <img src="https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=http%3A%2F%2Fcfile27.uf.tistory.com%2Fimage%2F9914653B5C738BA62DDBCD" alt="thumbnail" />
          <BookDetail>
            <Category>{'국내도서>소설>한국소설>판타치소설'}</Category>
            <BookTitle>선녀와 나무꾼</BookTitle>
            <BookPrice>10,000 원</BookPrice>
          </BookDetail>
        </BookWrapper>
        <FormWrapper encType="multipart/form-data" onSubmit={onSubmitPost}>
          <ImagePreView />
          <BookDetailInputWrapper>
            <input type="text" pattern="[0-9]+" placeholder="₩ 가격입력" onChange={onChangePrice} />
            <textarea placeholder="상품설명을 입력하세요" />
          </BookDetailInputWrapper>
          <AddPostButton type="submit">등록하기</AddPostButton>
        </FormWrapper>
      </AddPostWrapper>
    </>
  );
}

export default AddPostPage;
