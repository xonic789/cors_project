import React from 'react';
import styled from 'styled-components';

const SearchBookWrapper = styled.div`
  height: 400px;
  width: 90%;
  max-width: 600px;
  position: fixed;
  background-color: #ededed;
  margin-top: 120px;
  overflow: scroll;
`;
const SearchBookItem = styled.div`
  display: flex;
  padding: 10px;
  border-bottom: 1px solid #e8e8e8;
  & img {
    margin-right: 10px;
    max-height: 120px;
  }
`;
const SearchBookDetail = styled.div`
  & h5 {
    padding: 5px;
  }
`;
function SearchBook():JSX.Element {
  const a = 1;
  return (
    <SearchBookWrapper>
      <SearchBookItem>
        <img src="https://image.aladin.co.kr/product/26274/61/coversum/8983949082_1.jpg" alt="bookcover" />
        <SearchBookDetail>
          <h5>{'국내도서>소설/시/희곡>역사소설>한국 역사소설'}</h5>
          <h5>흥부와 놀부</h5>
          <h5>최사규 지음</h5>
          <h5>30,000원</h5>
        </SearchBookDetail>
      </SearchBookItem>
      <SearchBookItem>
        <img src="https://image.aladin.co.kr/product/26274/61/coversum/8983949082_1.jpg" alt="bookcover" />
        <SearchBookDetail>
          <h5>{'국내도서>소설/시/희곡>역사소설>한국 역사소설'}</h5>
          <h5>흥부와 놀부</h5>
          <h5>최사규 지음</h5>
          <h5>30,000원</h5>
        </SearchBookDetail>
      </SearchBookItem>
      <SearchBookItem>
        <img src="https://image.aladin.co.kr/product/26274/61/coversum/8983949082_1.jpg" alt="bookcover" />
        <SearchBookDetail>
          <h5>{'국내도서>소설/시/희곡>역사소설>한국 역사소설'}</h5>
          <h5>흥부와 놀부</h5>
          <h5>최사규 지음</h5>
          <h5>30,000원</h5>
        </SearchBookDetail>
      </SearchBookItem>
      <SearchBookItem>
        <img src="https://image.aladin.co.kr/product/26274/61/coversum/8983949082_1.jpg" alt="bookcover" />
        <SearchBookDetail>
          <h5>{'국내도서>소설/시/희곡>역사소설>한국 역사소설'}</h5>
          <h5>흥부와 놀부</h5>
          <h5>최사규 지음</h5>
          <h5>30,000원</h5>
        </SearchBookDetail>
      </SearchBookItem>
    </SearchBookWrapper>
  );
}

export default SearchBook;
