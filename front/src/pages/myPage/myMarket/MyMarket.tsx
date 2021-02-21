import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { getMyMarketListRequest } from './myMarketSlice';
import numberArrayUtill from '../../../utils/numberArrayUtill';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  flex-direction: column;
  font-size: 4.5vw;
  @media screen and (min-width: 455px) {
    font-size: 20.484px;
  }
`;

const Header = styled.header`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
  border-bottom: 1px solid #e0e0e0;
  padding: 0.8em 0;
  & h1 {
    font-size: 4.5vw;
    font-weight: 400;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h1 {
      font-size: 20.484px;
    }
  }
`;

const BackLink = styled(Link)`
  position: absolute;
  left: 0.5em;
`;

const BackLogo = styled.img`
  width: 1.8em;
  height: 1.8em;
`;

const Content = styled.div`
  width: 100%;
  height: 100%;
  overflow: hidden;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;
const Footer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1em 0 ;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const PaginationBox = styled.div`
  display: flex;
  padding: 1.5em 0;
  & img {
    cursor: pointer;
    width: 1em;
    height: 1em;
  }
`;

const PrevPage = styled.img``;

const PageNumbers = styled.div`
  display: flex;
`;

const PageNumber = styled.div`
`;

const PageLink = styled(Link)`
  padding: 0 0.5em;
`;

const NextPage = styled.img`
  width: 1em;
  height: 1em;
  transform: rotate(180deg);
`;

function MyMarket():JSX.Element {
  const dispatch = useDispatch();
  const history = useHistory();
  const [page, setPage] = useState<number>(0);
  const { mySaleArticle, totalPage, isGetMyPurchaseArticlesLoading } = useSelector((state: any) => state.myPageSlice.mySaleArticleSlice);
  const progressForm = (progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING'): { text: string, background: string } => {
    const resultProgress = {
      COMPLETED: { text: '거래완료', background: '#1e1e1e' },
      HIDE: { text: '숨김', background: '#7e7e7e' },
      TRADING: { text: '예약중', background: '#62ff7d' },
      POSTING: { text: '거래중', background: '#265290' },
    };

    return resultProgress[progress];
  };

  const onClickPrevPage = (e: React.MouseEvent<HTMLImageElement, MouseEvent>) => {
    const prevPage = page - 1;

    if (prevPage < 0) {
      e.preventDefault();
    } else {
      setPage(0);
    }
  };
  const onClickNextPage = (e: React.MouseEvent<HTMLImageElement, MouseEvent>) => {
    const nextPage = page + 1;

    if (nextPage > totalPage - 1) {
      e.preventDefault();
    } else {
      setPage(totalPage - 1);
    }
  };

  useEffect(() => {
    dispatch(getMyMarketListRequest(page));
  }, [dispatch, page]);
  return (
    <Layout>
      <Header>
        <BackLink to="/mypage">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>마이마켓</h1>
      </Header>
      <Content>
        콘텐트
      </Content>
      <Footer>
        <PaginationBox>
          <PrevPage onClick={onClickPrevPage} src="/images/icons/back.png" />
          <PageNumbers>
            {
              !totalPage
                ? (
                  <PageNumber>
                    <PageLink to="/mypage/markets">1</PageLink>
                  </PageNumber>
                )
                : (
                  <>
                    {
                      numberArrayUtill(totalPage).map((i) => (
                        <PageNumber>
                          <PageLink onClick={() => setPage(i - 1)} to={`/mypage/markets?page=${i - 1}`}>{i}</PageLink>
                        </PageNumber>
                      ))
                    }
                  </>
                )
            }
          </PageNumbers>
          <NextPage onClick={onClickNextPage} src="/images/icons/back.png" />
        </PaginationBox>
      </Footer>
    </Layout>
  );
}

export default MyMarket;
