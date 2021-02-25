import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';
import { maketDetailLoadRequest } from './marketSlice';

interface ParamsInterface {
  id: string;
}
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
  const dispatch = useDispatch();
  const { id } = useParams<ParamsInterface>();
  const { marketDetail } = useSelector((state: any) => state.market);

  useEffect(() => {
    dispatch(maketDetailLoadRequest(id));
  }, [dispatch, id]);

  return (
    <MarketDetailWrapper>
      {marketDetail !== null
      && (
      <>
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
      </>
      )}
    </MarketDetailWrapper>
  );
}

export default MarketDetail;
