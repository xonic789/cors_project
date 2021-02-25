import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { marketArticleInterface } from '../../interfaces/MarketInterface';
import { maketDetailLoadRequest } from './marketSlice';

interface ParamsInterface {
  id: string;
}
const MarketDetailWrapper = styled.div`
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
`;
const MarketDetailHeader = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  width: 100%;
  height: 200px;
  span {
    position: absolute;
    height: 100px;
    bottom: -80%;
    padding: 10px;
    font-weight: 800;
    font-size: 20px;
  }
`;
const BackgroundImage = styled.img`
  width: 100%;
  height: 100%;
`;
const BackImage = styled.img`
  width: 20px;
  height: 20px;
  position: relative;
  padding: 10px;
`;
const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  bottom: -20%;
  position: absolute;
`;
const MarketDetailoContent = styled.div`
`;
const MarketPostList = styled.ul`
  display: flex;
  justify-content: space-between;
  margin-top: 120px;
`;
const MarketPostItem = styled.li`
  display: flex;
  flex-direction: column;
  padding: 10px;
  img {
    width: 40%;
  }
  div {
    margin-top: 10px;
    font-weight: 800;
  }
`;
const MarketBottom = styled.div`
  position: fixed;
  margin-bottom: 0;
  bottom: 0;
  width: 100%;
  & button {
    border: 0;
    width: 100%;
    padding: 10px;
    color: white;
    background-color: #3960a6;
  }
`;

function MarketDetail():JSX.Element {
  const dispatch = useDispatch();
  const history = useHistory();
  const { id } = useParams<ParamsInterface>();
  const { marketDetail } = useSelector((state: any) => state.marketSlice);
  console.log('aa', marketDetail);
  const { email } = useSelector((state: any) => state.userSlice.user);
  useEffect(() => {
    dispatch(maketDetailLoadRequest(id));
  }, [dispatch, id]);
  const onClickAddMarketPost = (marketId: number) => {
    history.push('/addPost/sales/market');
  };
  return (
    <MarketDetailWrapper>
      <BackImage src="images/icons/back.png" />
      {marketDetail !== null
      && (
        <>
          <MarketDetailHeader>
            <BackgroundImage src="/images/background.jpeg" alt="background" />
            <ProfileImage src={marketDetail.image} alt="profile" />
            <span>{marketDetail.name}</span>
          </MarketDetailHeader>
          <MarketDetailoContent>
            <MarketPostList>
              {
                marketDetail.articles.length === 0
                  ? <div>게시물이 존재하지 않습니다</div>
                  : marketDetail.articles.map((v:marketArticleInterface) => (
                    <MarketPostItem>
                      <img src={v.image} alt="book" />
                      <div>{v.title}</div>
                      <div>{v.tprice}</div>
                    </MarketPostItem>
                  ))
              }
            </MarketPostList>
          </MarketDetailoContent>
          <MarketBottom>
            {marketDetail.email === email
            && (
            <button type="button" onClick={() => onClickAddMarketPost(marketDetail.marketId)}>
              도서등록하기
            </button>
            )}
          </MarketBottom>
        </>
      )}
    </MarketDetailWrapper>
  );
}

export default MarketDetail;
