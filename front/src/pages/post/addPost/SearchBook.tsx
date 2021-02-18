import React from 'react';
import styled from 'styled-components';
import aladinIteminterface from '../../../interfaces/AladinInterface';

interface searchBookInterface {
  searchResult: aladinIteminterface[],
  onClickItem: (item: aladinIteminterface) => void,
}
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
function SearchBook({ searchResult, onClickItem }: searchBookInterface):JSX.Element {
  return (
    <SearchBookWrapper>
      {searchResult.map((v) => (
        <SearchBookItem key={v.itemId} onClick={() => onClickItem(v)}>
          <img src={v.cover} alt="bookcover" />
          <SearchBookDetail>
            <h5>{v.categoryName}</h5>
            <h5>{v.title}</h5>
            <h5>{v.author}</h5>
            <h5>{v.priceStandard}원</h5>
          </SearchBookDetail>
        </SearchBookItem>
      ))}
    </SearchBookWrapper>
  );
}

export default SearchBook;
