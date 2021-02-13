import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { getMySaleArticleRequest } from './mySaleArticleSlice';

const Layout = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 4vw;
  @media screen and (min-width: 430px) {
    font-size: 17.216px;
  }
`;

const Header = styled.header`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  & h1 {
      width: 100%;
      font-size: 4.5vw;
      padding: 1.5em;
  }
  @media screen and (min-width: 430px) {
    width: 430.4px;
    & h1 {
      font-size: 19.368px;
    }
  }
`;

const BackLink = styled(Link)`
  position: absolute;
  left: 1em;
`;

const BackImg = styled.img`
  width: 2em;
  height: 2em;
`;

function MySaleArticle():JSX.Element {
  const dispatch = useDispatch();
  const { mySaleArticle } = useSelector((state) => state.mySaleArticleSlice);

  useEffect(() => {
    dispatch(getMySaleArticleRequest({ type: '' }));
  }, [dispatch]);

  return (
    <>
      <Layout>
        <Header>
          <BackLink to="/mypage">
            <BackImg src="/images/icons/back.png" />
          </BackLink>
          <h1>판매목록</h1>
        </Header>
      </Layout>
    </>
  );
}

export default MySaleArticle;
