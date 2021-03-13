import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import cookie from 'react-cookies';
import LoginForm from './LoginForm';
import { postLogoutRequest } from '../../signIn/userSlice';

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
  margin-bottom: 3em;
  & h1 {
    color: #3960a6;
    font-weight: bold;
    font-size: 8vw;
  }
  @media screen and (min-width: 460px) {
    & h1 {
      font-size: 36.8px;
    }
  }
`;

function AdminSignIn():JSX.Element {
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
          <h1>관리자 로그인</h1>
        </TitleBox>
        <LoginForm />
      </Layout>
    </Wrapper>
  );
}

export default AdminSignIn;
