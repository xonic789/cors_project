import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory, useParams } from 'react-router-dom';
import { ToastsContainer, ToastsContainerPosition, ToastsStore } from 'react-toasts';
import styled from 'styled-components';
import { addNoticeRequestAsync } from '../../../api/noticeApi';

const Layout = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
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
  display: flex;
  flex-direction: column;
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

const TitleArea = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 0.5em 1em;
  border-bottom: 1px solid #ccc;
  & h2, & input {
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  & p {
    font-size: 3vw;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h2, & input {
      font-size: 15.932px;
    }
    & p {
      font-size: 13.656px;
    }
  }
`;

const TextArea = styled.div`
  padding: 1em;
  height: 100%;
  & pre, & textarea {
      width: 100%;
      height: 100%;
      padding: 1em;
      border: 1px solid #ccc;
      overflow: auto;
      word-break: keep-all;
      white-space: pre-wrap;
      font-size: 3.5vw;
  }
  & textarea {
    resize: none;
  }
  @media screen and (min-width: 455px) {
    & pre, & textarea {
      font-size: 15.932px;
    }
  }
`;

const ButtonBox = styled.div`
  width: 100%;
  padding: 1em;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  & button {
    cursor: pointer;
    padding: 0.5em 1em;
    font-size: 3.5vw;
    color: #fff;
    border: 1px solid #a3c3ff;
    border-radius: 5px;
    &.delete {
      background: #395fa655;
    }
    &.modify {
      background: #fff;
      color: #3960a6;
      margin-right: 1em;
    }
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & button {
      font-size: 13.656px;
    }
  }
`;

const DeleteModal = styled.div`
  position: absolute;
  width: 80%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1.5em 0;
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 10px;
  & p {
    font-size: 4.5vw;
    padding: 0.5em 0;
  }
  & button {
    cursor: pointer;
    padding: 0.5em 1em;
    font-size: 3.5vw;
    color: #fff;
    border: 1px solid #a3c3ff;
    border-radius: 5px;
    &.delete {
      background: #395fa655;
    }
    &.modify {
      background: #fff;
      color: #3960a6;
      margin-right: 1em;
    }
  }
  @media screen and (min-width: 455px) {
    & p {
      font-size: 20.484px;
    }
    & button {
      font-size: 13.656px;
    }
  }
`;

function AdminNoticeAdd():JSX.Element {
  const history = useHistory();
  const dispatch = useDispatch();
  const [inputs, setInputs] = useState({
    title: '',
    content: '',
  });

  const onchangeInputs = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onClickAdd = async () => {
    const formData = new FormData();
    formData.append('title', inputs.title);
    formData.append('content', inputs.content);
    try {
      const result = await addNoticeRequestAsync(formData);
      if (result.status === 200) {
        ToastsStore.success('공지사항 등록이 완료되었습니다.');
        history.push('/admin/notice');
      }
    } catch (error) {
      ToastsStore.error('서버에러 또는 권한 불충분');
    }
  };

  return (
    <Layout>
      <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      <Header>
        <BackLink to="/admin/notice">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>공지사항 관리</h1>
      </Header>
      <Content>
        <TitleArea>
          <input type="text" onChange={onchangeInputs} name="title" />
        </TitleArea>
        <TextArea>
          <textarea onChange={onchangeInputs} name="content" />
        </TextArea>
        <ButtonBox>
          <button onClick={onClickAdd} className="modify" type="button">등록</button>
        </ButtonBox>
      </Content>
    </Layout>
  );
}

export default AdminNoticeAdd;
