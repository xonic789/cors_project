import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import cookie from 'react-cookies';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import LoginForm from './LoginForm';
import SocialLogin from './SocialLogin';
import { postLogoutRequest } from './userSlice';

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
`;

const Layout = styled.div`
  width: 100%;
  max-width: 600px;
  min-width: 300px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const TitleBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 1.7em;
`;

const Logo = styled.img`
  width: auto;
  height: 18vw;
  @media screen and (min-width: 460px) {
    height: 82.8px;
  }
`;

const LinkBox = styled.div`
  width: 19em;
  font-size: 4vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2em;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
    margin-bottom: 36.8px;
  }
`;

const StyledLink = styled(Link)`
  font-weight: 500;
  text-decoration: none;
  color: #707070;
`;

function Login():JSX.Element {
  const dispatch = useDispatch();

  useEffect(() => {
    console.log(cookie.load('ACCESS_TOKEN') !== undefined);
    if (cookie.load('ACCESS_TOKEN') !== undefined || cookie.load('REFRESH_TOKEN') !== undefined) {
      dispatch(postLogoutRequest({}));
    }
  }, [dispatch]);

  return (
    <Wrapper>
      <Layout>
        <TitleBox>
          <Logo src="/images/icons/title_logo.jpg" />
        </TitleBox>
        <LoginForm />
        <LinkBox>
          <StyledLink to="/join">회원가입</StyledLink>
          <StyledLink to="/home">비회원 둘러보기</StyledLink>
        </LinkBox>
        <SocialLogin />
      </Layout>
    </Wrapper>
  );
}

export default Login;
