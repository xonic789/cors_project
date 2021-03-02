import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { ToastsContainer, ToastsContainerPosition, ToastsStore } from 'react-toasts';
import cookie from 'react-cookies';
import AppLayout from '../../components/AppLayout';
import { postLogoutRequest, setMyMarketList } from '../signIn/userSlice';
import { maketDetailLoadRequest, resetMyMarketList } from '../market/marketSlice';

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
  border: 1.5px solid #ccc;
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

const LogoutButton = styled.button`
  position: absolute;
  right: 0.5em;
  top: 0.5em;
`;

const AddMarketSelector = styled.div`
  z-index: -1;
  opacity: 0;
  transform: translateY(20px);
  transition: opacity 0.5s, transform 0.5s;
  width: 90%;
  padding: 2em 1em;
  position: absolute;
  display: flex;
  align-items: center;
  flex-direction: column;
  background: #fff;
  border: 1px solid#808080;
  border-radius: 10px;
  & h2 {
    font-size: 4vw;
    margin-bottom: 0.7em;
  }
  & p {
    font-size: 3.5vw;
    margin-bottom: 1em;
  }
  @media screen and (min-width: 455px) {
    width: 407.675px;
    & h2 {
      font-size: 18.208px;
    }
    & p {
      font-size: 15.932px;
    }
  }
`;

const ButtonBox = styled.div`
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 50%;
  & button {
    cursor: pointer;
    font-size: 3.5vw;
    padding: 0.5em;
    border: 1px solid #ccc;
    border-radius: 10px;
    outline: none;
  }
  & button.OkButton {
    background: #3162C7;
    color: #fff;
    font-weight: bold;
  }
  @media screen and (min-width: 455px) {
    & button {
      font-size: 15.932px;
    }
  }
`;

function MyPage():JSX.Element {
  const history = useHistory();
  const dispatch = useDispatch();
  const { user, isLoginSucceed } = useSelector((state: any) => state.userSlice);
  const { myMarketList: myMarket } = useSelector((state: any) => state.marketSlice);
  const { nickname, profileImg, myMarketList } = user;
  const marketAddBox = useRef<HTMLDivElement>(null);

  const onClickLogout = () => {
    dispatch(postLogoutRequest({}));
  };

  const onClickAddmarket = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    e.preventDefault();
    if (!isLoginSucceed) {
      ToastsStore.error('로그인이 필요한 서비스입니다.');
    } else if (marketAddBox.current !== null) {
      marketAddBox.current.style.opacity = '1';
      marketAddBox.current.style.transform = 'translateY(0)';
      marketAddBox.current.style.zIndex = '1';
    }
  };

  const closeModal = () => {
    if (marketAddBox.current !== null) {
      marketAddBox.current.style.opacity = '0';
      marketAddBox.current.style.transform = 'translateY(20px)';
      marketAddBox.current.style.zIndex = '-1';
    }
  };

  const onClickLoginCheck = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    if (!isLoginSucceed || cookie.load('REFRESH_TOKEN') === undefined) {
      e.preventDefault();
      ToastsStore.error('로그인이 필요한 서비스입니다.');
    }
  };

  const onClickPushMarket = () => {
    dispatch(maketDetailLoadRequest(myMarketList[0]));
  };

  useEffect(() => {
    if (myMarket.length !== 0) {
      dispatch(setMyMarketList(myMarket));
    }
  }, [dispatch, myMarket]);

  return (
    <AppLayout activeId={3}>
      <Wrapper>
        <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
        <LogoutButton type="button" onClick={onClickLogout}>로그아웃</LogoutButton>
        <Layout>
          <MyInfo>
            {
              profileImg !== '' && cookie.load('REFRESH_TOKEN') !== undefined
                ? <ProfileImg src={profileImg} />
                : <ProfileImg src="/images/icons/init_profile.png" />
            }
            <ProfileText>
              {
                nickname !== '' && cookie.load('REFRESH_TOKEN') !== undefined
                  ? <MyName>{nickname}</MyName>
                  : (
                    <>
                      <MyName style={{ cursor: 'pointer' }} onClick={() => history.push('/')}>로그인/회원가입하기<SpanImg src="/images/icons/back.png" /></MyName>
                      <MyDicription>로그인 후 더 많은 혜택을 받으세요.</MyDicription>
                    </>
                  )
              }
            </ProfileText>
          </MyInfo>
          <ProfileBtn onClick={onClickLoginCheck} to="/mypage/modify">프로필 수정</ProfileBtn>
          <MyMenu>
            <MyMenuItem>
              <MyMenuLink onClick={onClickLoginCheck} to="/mypage/sales">
                <MenuImgBox>
                  <MenuImg src="/images/icons/sell_active.png" />
                </MenuImgBox>
                <MenuText>판매내역</MenuText>
              </MyMenuLink>
            </MyMenuItem>
            <MyMenuItem>
              <MyMenuLink onClick={onClickLoginCheck} to="/mypage/purchase">
                <MenuImgBox>
                  <MenuImg src="/images/icons/pur_active.png" />
                </MenuImgBox>
                <MenuText>구매내역</MenuText>
              </MyMenuLink>
            </MyMenuItem>
            <MyMenuItem>
              <MyMenuLink onClick={onClickLoginCheck} to="/mypage/wishs">
                <MenuImgBox>
                  <MenuImg src="/images/icons/heart_blue.png" />
                </MenuImgBox>
                <MenuText>찜목록</MenuText>
              </MyMenuLink>
            </MyMenuItem>
          </MyMenu>
          <UtilMenuList>
            <UtilMenuItem><UtilLink onClick={onClickLoginCheck} to="/question">문의하기</UtilLink></UtilMenuItem>
            <UtilMenuItem><UtilLink onClick={onClickLoginCheck} to="/notice">공지사항</UtilLink></UtilMenuItem>
            <UtilMenuItem><UtilLink onClick={onClickLoginCheck} to="/review">한줄평</UtilLink></UtilMenuItem>
            {
              myMarketList.length === 0
                ? <UtilMenuItem><UtilLink onClick={onClickAddmarket} to="/addMarket">나의마켓</UtilLink></UtilMenuItem>
                : <UtilMenuItem><UtilLink onClick={onClickPushMarket} to={`/market/${myMarketList[0]}`}>나의마켓</UtilLink></UtilMenuItem>
            }
          </UtilMenuList>
        </Layout>
        <AddMarketSelector ref={marketAddBox}>
          <h2>등록한 마켓이 없습니다.</h2>
          <p>마켓을 등록하시겠습니까?</p>
          <ButtonBox>
            <button onClick={() => history.push('/addMarket')} className="OkButton" type="button">확인</button>
            <button onClick={closeModal} type="button">취소</button>
          </ButtonBox>
        </AddMarketSelector>
      </Wrapper>
    </AppLayout>
  );
}

export default MyPage;
