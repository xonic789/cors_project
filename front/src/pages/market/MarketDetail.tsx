import React from 'react';
import styled from 'styled-components';

const MarketDetailWrapper = styled.div`
  width: 100%;
  max-width: 600px;
`;
const MarketDetailHeader = styled.div``;
const MarketDetailoContent = styled.div``;
const MarketPostList = styled.ul``;
const MarketPostItem = styled.li``;
const MarketBottom = styled.div``;

function MarketDetail():JSX.Element {
  return (
    <MarketDetailWrapper>
      <MarketDetailHeader>Header</MarketDetailHeader>
      <MarketDetailoContent>
        <MarketPostList>
          <MarketPostItem>Item</MarketPostItem>
          <MarketPostItem>Item</MarketPostItem>
          <MarketPostItem>Item</MarketPostItem>
        </MarketPostList>
      </MarketDetailoContent>
      <MarketBottom>
        <button type="button">
          도서등록하기
        </button>
      </MarketBottom>
    </MarketDetailWrapper>
  );
}

export default MarketDetail;
