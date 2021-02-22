import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { getQuestionDetailRequest } from './questionSlice';

const Layout = styled.form`
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
  border-bottom: 1px solid #e0e0e0;
  padding: 0.8em 0;
  margin-bottom: 1.5em;
  & h1 {
    font-size: 4.5vw;
    font-weight: 400;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h1 {
      font-size: 20.484px;
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
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  justify-content: center;
  padding: 0 1em;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const TitleArea = styled.div`
  width: 100%;
  & h2 {
    text-align: left;
  }
`;

const EmptyArticle = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  align-items: center;
  justify-content: center;
  & h2 {
    font-size: 4.5vw;
    font-weight: bold;
    margin-bottom: 1em;
  }
  & button{
    font-size: 4vw;
    padding: 0.5em 1em;
    background: #265290;
    border: none;
    border-radius: 10px;
    outline: none;
    color: #fff;
    font-weight: bold;
  }
  @media screen and (min-width: 455px) {
    & h2 {
      font-size: 20.484px;
    }
    & button {
      font-size: 18.208px;
    }
  }
`;

function QuestionDetail():JSX.Element {
  const { detailId, questionDetail, isGetQuestionDetailError } = useSelector((state: any) => state.myPageSlice.questionSlice);
  const dispatch = useDispatch();
  const history = useHistory();

  useEffect(() => {
    dispatch(getQuestionDetailRequest(detailId));
  }, [dispatch, detailId]);

  return (
    <Layout>
      <Header>
        <BackLink to="/question">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>문의사항 상세</h1>
      </Header>
      {
        isGetQuestionDetailError !== null
          ? (
            <>
              <EmptyArticle>
                <h2>접근 권한이 없거나 잘못된 접근입니다.</h2>
                <button type="button" onClick={() => history.push('/mypage')}>마이페이지로 가기</button>
              </EmptyArticle>
            </>
          )
          : (
            <Content>
              <TitleArea>
                <h2>{questionDetail.title}</h2>
                <p>{questionDetail.writeDate}</p>
              </TitleArea>
              <pre>{questionDetail.content}</pre>
            </Content>
          )
      }
    </Layout>
  );
}

export default QuestionDetail;
