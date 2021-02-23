import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const Layout = styled.form`
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

const BackgroundImgBox = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  width:100%;
  height: 50vw;
  background: #000;
  margin-bottom: 3em;
  @media screen and (min-width: 455px) {
    height: 227.6px;
  }
`;

const BackgroundImg = styled.img`
  width: 100%;
  height: 50vw;
  object-fit: cover;
  @media screen and (min-width: 455px) {
    height: 227.6px;
  }
`;

const MarketImg = styled.img`
  position: absolute;
  bottom: -2em;
  width: 6em;
  height: 6em;
  border-radius: 50%;
  object-fit: cover;
`;

const InputsBox = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1em;
`;
const InputItem = styled.div`
  display: flex;
  flex-direction: column;
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
        <BackgroundImgBox>
          <BackgroundImg src="/images/icons/profileBack.png" />
          <MarketImg src="/images/icons/profileBack.png" />
        </BackgroundImgBox>
        <InputsBox>
          <InputItem>
            <label>마켓 이미지</label>
            <input type="file" />
          </InputItem>
        </InputsBox>
      </Content>
    </Layout>
  );
}

export default MyMarket;
