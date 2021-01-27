import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import LoginForm from './LoginForm';
import JoinForm from './JoinForm';
import SocialLogin from './SocialLogin';

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
  width: 10vw;
  height: 10vw;
`;

const Title = styled.h1`
  font-size: 10vw;
  color: #3162C7;
`;

const LinkBox = styled.div`
  width: 19em;
  font-size: 4vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2em;
`;

const StyledLink = styled(Link)`
  font-weight: 500;
  text-decoration: none;
  color: #707070;
`;

function Login():JSX.Element {
  const [inputState, setInputState] = useState<string>('login');

  const onClickInputsHanler = () => {
    switch (inputState) {
      case ('login'):
        setInputState('join');
        break;
      case ('join'):
        setInputState('login');
        break;
      default:
        break;
    }
  };

  return (
    <Wrapper>
      <Layout>
        <TitleBox>
          <Logo />
          <Title>코스마켓</Title>
        </TitleBox>
        {inputState === 'login' ? <LoginForm /> : <JoinForm />}
        <LinkBox>
          <StyledLink to="/" onClick={onClickInputsHanler}>{inputState === 'login' ? '회원가입' : '로그인'}</StyledLink>
          <StyledLink to="/home">비회원 둘러보기</StyledLink>
        </LinkBox>
        <SocialLogin />
      </Layout>
    </Wrapper>
  );
}

export default Login;
