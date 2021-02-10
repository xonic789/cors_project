import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import AppLayout from '../../components/AppLayout';

const Wrapper = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1.3em 0;
`;

const MyInfo = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  font-size: 4.5vw;
  margin-bottom: 1em;
  @media screen and (min-width: 455px) {
    font-size: 20.484px;   
  }
`;

const ProfileImg = styled.img`
  width: 4em;
  height: 4em;
  margin-right: 0.4em;
  border: 1.5px solid #000;
  border-radius: 50%;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
`;

const MyName = styled.h2`
  display: flex;
  align-items: center;
  font-size: 5.5vw;
  font-weight: bold;
  margin-bottom: 0.5em;
  @media screen and (min-width: 455px) {
    font-size: 25.036px;
  }
`;

const SpanImg = styled.img`
 width: 1em;
 height: 1em;
 transform: rotate(180deg);
`;

const MyDicription = styled.p`
  font-size: 3vw;
  @media screen and (min-width: 455px) {
    font-size: 13.656px;
  }
`;

const ProfileBtn = styled(Link)`
  display: block;
  width: 23em;
  padding: 1em 0;
  font-size: 3.5vw;
  text-align: center;
  background: #fff;
  border: 1px solid rgba(158, 158, 158, 0.7);
  margin-bottom: 1em;
  text-decoration: none;
  color: #000;
  @media screen and (min-width: 455px) {
    font-size: 15.932px;
  }
`;

const MyMenu = styled.ul`
  display: flex;
  width: 23em;
  font-size: 3.5vw;
  border: 1px solid rgba(158, 158, 158, 0.7);
  border-radius: 5px;
  margin-bottom: 3em;
  @media screen and (min-width: 455px) {
    font-size: 15.932px;
  }
`;

const MenuImg = styled.img`
  width: 2.5em;
  height: 2.5em;
`;

const MyMenuItem = styled.li`
  width: 33.3333%;
  padding: 1.2em 0;
  &:not(:last-child):not(:first-child) {
    border-left: 1px solid rgba(158, 158, 158, 0.7);
    border-right: 1px solid rgba(158, 158, 158, 0.7);
  }
  &:first-child ${MenuImg} {
    width: 2.2em;
    height: 2.2em;
  }
`;

const MyMenuLink = styled(Link)`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-decoration: none;
`;

const MenuImgBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 2.5em;
  height: 2.5em;
  margin-bottom: 0.6em;
`;

const MenuText = styled.p`
  color: #3162C7;
  font-weight: bold;
`;

const UtilMenuList = styled.ul`
  display: flex;
  width: 100%;
  flex-direction: column;
`;

const UtilMenuItem = styled.li`
  display: block;
  &:not(:last-child) {
    margin-bottom: 1em;
  }
`;

const UtilLink = styled(Link)`
  display: block;
  text-align: center;
  padding: 1.2em 0;
  color: #fff;
  background: #6F96E9;
  border-radius: 5px;
  text-decoration: none;
  font-size: 4vw;
  font-weight: bold;
  @media screen and (min-width: 455px) {
    font-size: 18.2px;
  }
`;

function MyPage():JSX.Element {
  return (
    <AppLayout>
      <Wrapper>
        <Layout>
          <MyInfo>
            <ProfileImg src="/images/icons/init_profile.png" />
            <ProfileText>
              <MyName>로그인/회원가입하기<SpanImg src="/images/icons/back.png" /></MyName>
              <MyDicription>로그인 후 더 많은 혜택을 받으세요.</MyDicription>
            </ProfileText>
          </MyInfo>
          <ProfileBtn to="/mypage/modify">프로필 수정</ProfileBtn>
          <MyMenu>
            <MyMenuItem>
              <MyMenuLink to="/mypage/sales">
                <MenuImgBox>
                  <MenuImg src="/images/icons/sell_active.png" />
                </MenuImgBox>
                <MenuText>판매내역</MenuText>
              </MyMenuLink>
            </MyMenuItem>
            <MyMenuItem>
              <MyMenuLink to="/mypage/purchase">
                <MenuImgBox>
                  <MenuImg src="/images/icons/pur_active.png" />
                </MenuImgBox>
                <MenuText>구매내역</MenuText>
              </MyMenuLink>
            </MyMenuItem>
            <MyMenuItem>
              <MyMenuLink to="/mypage/wishs">
                <MenuImgBox>
                  <MenuImg src="/images/icons/heart_blue.png" />
                </MenuImgBox>
                <MenuText>찜목록</MenuText>
              </MyMenuLink>
            </MyMenuItem>
          </MyMenu>
          <UtilMenuList>
            <UtilMenuItem><UtilLink to="/question">문의하기</UtilLink></UtilMenuItem>
            <UtilMenuItem><UtilLink to="/notice">공지사항</UtilLink></UtilMenuItem>
            <UtilMenuItem><UtilLink to="/review">한줄평</UtilLink></UtilMenuItem>
            <UtilMenuItem><UtilLink to="/mymarket">나의마켓</UtilLink></UtilMenuItem>
          </UtilMenuList>
        </Layout>
      </Wrapper>
    </AppLayout>
  );
}

export default MyPage;
