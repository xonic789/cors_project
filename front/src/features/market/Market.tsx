import React from 'react';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';
import { dummyMarket } from '../../interfaces/mockdata';

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
const MatrketList = styled.ul`
  margin-top: 80px;
`;
const MarketItem = styled.li`
  padding: 10px;
  display: flex;
  align-items: center;
`;
const MarketThumbnail = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-right: 20px;
`;
const MarketIntroduce = styled.div`
  & h1 {
    font-size: 20px;
    margin-bottom: 10px;
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
        <MatrketList>
          {dummyMarket.data.map((v) => (
            <MarketItem>
              <MarketThumbnail src={v.marketImage} alt={`${v.marketName}_thumbnail`} />
              <MarketIntroduce>
                <h1>{v.marketName}</h1>
                <p>{v.marketIntro}</p>
              </MarketIntroduce>
            </MarketItem>
          ))}
        </MatrketList>
      </MarketWrapper>
    </AppLayout>
  );
}

export default Market;
