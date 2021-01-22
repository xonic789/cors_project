import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import LoginForm from './LoginForm';
import SocialLogin from './SocialLogin';

const Positioner = styled.div`
  width: 600px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const TitleBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 36px;
`;

const Logo = styled.img`
  width: 70px;
  height: 70px;
`;

const Title = styled.h1`
  font-size: 76px;
  color: #3162C7;
`;

const LinkBox = styled.div`
  width: 90%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 38px;
`;

const StyledLink = styled(Link)`
  font-size: 26px;
  font-weight: 500;
  color: #707070;
`;

function Login():JSX.Element {
  return (
    <Positioner>
      <TitleBox>
        <Logo />
        <Title>코스마켓</Title>
      </TitleBox>
      <LoginForm />
      <LinkBox>
        <StyledLink to="/join">회원가입</StyledLink>
        <StyledLink to="/main">비회원 둘러보기</StyledLink>
      </LinkBox>
      <SocialLogin />
    </Positioner>
  );
}

export default Login;
