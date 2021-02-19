import React from 'react';
import styled from 'styled-components';
import { Link, useHistory } from 'react-router-dom';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  flex-direction: column;
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

function MyMarket():JSX.Element {
  return (
    <Layout>
      <Header>
        <h1>마이마켓</h1>
      </Header>
      <Content>
        콘텐트
      </Content>
      <Footer>
        푸터
      </Footer>
    </Layout>
  );
}

export default MyMarket;
