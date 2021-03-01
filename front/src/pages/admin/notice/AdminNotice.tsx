import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
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
  border-bottom: 1px solid #ccc;
  margin-bottom: 1em;
  padding: 1em 0;
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
  overflow: auto;
  & ul {
    width: 100%;
  }
  & li {
    width: 100%;
    padding: 1em;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid #ccc;
  }
  & li h2 {
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  & li p {
    font-size: 3vw;
  }
  & li:not(:last-child) {
    margin-bottom: 1em;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & li h2 {
      font-size: 15.932px;
    }
    & li p {
      font-size: 13.656px;
    }
  }
`;

const AddNoticeBox = styled.div`
  padding: 1.5em 0;
  width: 90%;
  & a {
    width: 100%;
    text-align: center;
    display: block;
    padding: 1em 0;
    border-radius: 10px;
    font-weight: bold;
    background: #3960a6;
    color: #fff;
  }
  @media screen and (min-width: 455px) {
    width: 409.5px;
  }
`;

function AdminNotice():JSX.Element {
  const history = useHistory();

  const onClickDetail = (noticeId: string) => {
    history.push(`/admin/notice/${noticeId}`);
  };
  return (
    <Layout>
      <Header>
        <h1>공지사항 관리</h1>
      </Header>
      <Content>
        <ul>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
          <li>
            <h2>공지사항 제목입니다.</h2>
            <p>2020.03.01</p>
          </li>
        </ul>
      </Content>
      <AddNoticeBox>
        <Link to="/admin/addNoitce">공지사항 등록</Link>
      </AddNoticeBox>
    </Layout>
  );
}

export default AdminNotice;
