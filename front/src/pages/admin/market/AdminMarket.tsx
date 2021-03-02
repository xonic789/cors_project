import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
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
  padding: 1.5em 0;
  & h1 {
    font-size: 6vw;
    font-weight: 400;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h1 {
    font-size: 27.312px;
  }
  }
`;

const Content = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  & ul {
    width: 90%;
  }
  & li {
    width: 100%;
  }
  & li:not(:last-child) {
    margin-bottom: 2em;
  }
  & a {
    display: block;
    text-align: center;
    width: 100%;
    padding: 1em 0;
    background: #3960a6;
    color: #fff;
    font-weight: bold;
    border-radius: 10px;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

function AdminHome():JSX.Element {
  return (
    <Layout>
      <Header>
        <h1>마켓 승인 관리</h1>
      </Header>
      <Content>
        마켓
      </Content>
    </Layout>
  );
}

export default AdminHome;
