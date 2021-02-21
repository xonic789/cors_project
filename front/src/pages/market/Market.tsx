import React from 'react';
import { Link } from 'react-router-dom';
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
const MarketFilterList = styled.ul`
  margin-top: 70px;
  display: flex;
  width: 200px;
  justify-content: space-around;
  align-items: center;
  padding: 10px;
`;
const MarketFilterItem = styled.li`
  border: 2px solid #e8e8e8;
  padding: 5px;
  border-radius: 10px;
`;
const MatrketList = styled.ul`
`;
const MarketItem = styled.li`
  padding: 10px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e8e8e8;
`;
const MarketThumbnail = styled.img`
  width: 80px;
  height: 80px;
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
        <MarketFilterList>
          <MarketFilterItem>가까운순</MarketFilterItem>
          <MarketFilterItem>찜많은순</MarketFilterItem>
        </MarketFilterList>
        <MatrketList>
          {dummyMarket.data.map((v) => (
            <Link to="/market/sd">
              <MarketItem>
                <MarketThumbnail src={v.marketImage} alt={`${v.marketName}_thumbnail`} />
                <MarketIntroduce>
                  <h1>{v.marketName}</h1>
                  <p>{v.marketIntro}</p>
                </MarketIntroduce>
              </MarketItem>
            </Link>
          ))}
        </MatrketList>
      </MarketWrapper>
    </AppLayout>
  );
}

export default Market;
