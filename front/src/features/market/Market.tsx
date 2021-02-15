import React from 'react';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';

const MarketWrapper = styled.div`
  width: 100%;
  max-width: 600px;
`;
const MarketHeader = styled.div`
  height: 60px;
  width: 100%;
  max-width: 600px;
  background-color: white;
  position: fixed;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid #e8e8e8;
  & span {
    margin-right: 20px;
    font-size: 20px;
  }
  & img {
    width: 30px;
  }
`;
function Market():JSX.Element {
  return (
    <AppLayout activeId={2}>
      <MarketWrapper>
        <MarketHeader>
          <span>마켓</span>
          <img src="/images/icons/market.png" alt="market_logo" />
        </MarketHeader>
      </MarketWrapper>
    </AppLayout>
  );
}

export default Market;
