import React, { useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { RootStateOrAny, useSelector } from 'react-redux';
import LoginForm from './LoginForm';
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
  const {
    loginSucceed,
  } = useSelector((state: RootStateOrAny) => state.LoginSlice);

  const history = useHistory();

  useEffect(() => {
    if (loginSucceed === true) {
      history.push('/home');
    }
  }, [loginSucceed, history]);

  return (
    <Wrapper>
      <Layout>
        <TitleBox>
          <Logo />
          <Title>코스마켓</Title>
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
