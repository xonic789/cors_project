import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

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

function MyMarket():JSX.Element {
  return (
    <Layout>
      <Header>
        <BackLink to="/mypage">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>마켓 등록하기</h1>
      </Header>
      <Content>
        콘텐트
      </Content>
    </Layout>
  );
}

export default MyMarket;
