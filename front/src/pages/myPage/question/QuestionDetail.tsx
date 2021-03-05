import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { commentInterface } from '../../../interfaces/Question.inteface';
import { getQuestionDetailRequest } from './questionSlice';

const Layout = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  font-size: 4.5vw;
  line-height: 1.3em;
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
  justify-content: center;
  padding: 0 1em;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const TitleArea = styled.div`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1.5em 1em;
  border: 1px solid #ccc;
  margin-bottom: 0.5em;
  & h2 {
    text-align: left;
  }
  & p {
    color: #666666;
    font-size: 3.5vw;
    flex-shrink: 0;
    margin-left: 1em;
  }
  & p.commentOk {
    color: #3162C7;
  }

  @media screen and (min-width: 455px) {
    & p {
      font-size: 15.932px;
    }
  }
`;

const ContentArea = styled.div`
  width: 100%;
  height: 60vh;
  min-height: 300px;
  overflow: auto;
  padding: 1em;
  border: 1px solid #ccc;
  font-size: 3.8vw;
  & p.content_title {
    color: #3162C7;
    margin-bottom: 0.5em;
  }
  & pre {
    margin-bottom: 1em;
  }
  & li {
    display: flex;
    flex-direction: column;
    padding: 0.7em 0;
  }
  & li:not(:last-child) {
    border-bottom: 1px solid #ccc;
  }
  @media screen and (min-width: 455px) {
    font-size: 17.2976px;
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

const CommentWriter = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: space-between;

  & * {
    font-size: 3vw;
  }
  @media screen and (min-width: 455px) {
    & * {
      font-size: 13.656px;
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
                {
                  questionDetail.comments.length === 0
                    ? (
                      <p>[답변대기중]</p>
                    )
                    : (
                      <p className="commentOk">[답변완료]</p>
                    )
                }
              </TitleArea>
              <ContentArea>
                <p className="content_title">[문의내용]</p>
                <pre>{questionDetail.content}</pre>
                <p className="content_title">[답변내용]</p>
                {
                  questionDetail.comments.length === 0
                    ? (
                      <p>답변 대기중입니다. 신속히 답변 드리겠습니다.</p>
                    )
                    : (
                      <ul>
                        {
                          questionDetail.comments.map((item: commentInterface) => (
                            <li>
                              <CommentWriter>
                                <h2>작성자 : {item.nickname}</h2>
                                <p>작성일 : {item.writeDate}</p>
                              </CommentWriter>
                              <p>{item.content}</p>
                            </li>
                          ))
                        }
                      </ul>
                    )
                }
              </ContentArea>
            </Content>
          )
      }
    </Layout>
  );
}

export default QuestionDetail;
