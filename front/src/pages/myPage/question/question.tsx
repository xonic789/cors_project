import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { questionInterface } from '../../../interfaces/Question.inteface';
import numberArrayUtill from '../../../utils/numberArrayUtill';
import { getQuestionRequest, setDeatilId } from './questionSlice';

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

const QuestionList = styled.ul`
  width: 100%;
  height: 100%;
  overflow: auto;
  display: flex;
  flex-direction: column;
  padding: 0 1em;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const QuestionItem = styled.li`
  cursor: pointer;
  width: 100%;
  display: flex;
  flex-direction: column;
  &:not(:last-child) {
    margin-bottom: 1em;
  }
`;

const QuestionTitleBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 1em;
  border: 1px solid #cfcfcf;
`;

const QuestionText = styled.div`
  & h2 {
    font-size: 3.5vw;
    font-weight: bold;
    margin-bottom: 0.5em;
  }
  & p {
    font-size: 3vw;
  }
  @media screen and (min-width: 455px) {
    & h2 {
      font-size: 15.932px;
    }
    & p {
      font-size: 13.656px;
    }
  }
`;

const PaginationBox = styled.div`
  position: relative;
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  padding: 1.5em 0;
  & img {
    cursor: pointer;
    width: 1em;
    height: 1em;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const PrevPage = styled.img``;

const PageNumbers = styled.div`
  display: flex;
`;

const PageNumber = styled.div`
`;

const PageLink = styled(Link)`
  padding: 0 0.5em;
`;

const NextPage = styled.img`
  width: 1em;
  height: 1em;
  transform: rotate(180deg);
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
    margin-bottom: 0.3em;
  }
  & p {
    font-size: 4vw;
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
    & p {
      font-size: 18.208px;
    }
    & button {
      font-size: 18.208px;
    }
  }
`;

const ButtonWrap = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const AddButton = styled(Link)`
  padding: 0.5em;
  background: #265290;
  margin-right: 1em;
  border: 1px solid #eee;
  border-radius: 10px;
  color: #fff;
  font-size: 3.5vw;
  @media screen and (min-width: 455px) {
    font-size: 15.932px;
  }
`;

function Question():JSX.Element {
  const [page, setPage] = useState<number>(0);
  const dispatch = useDispatch();
  const history = useHistory();
  const { questionList, totalPage } = useSelector((state: any) => state.myPageSlice.questionSlice);

  useEffect(() => {
    dispatch(getQuestionRequest(page));
  }, [page, dispatch]);

  const onClickDetail = (id: string) => {
    dispatch(setDeatilId(id));
    history.push('/question/detail');
  };

  return (
    <Layout>
      <Header>
        <BackLink to="/mypage">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>문의사항</h1>
      </Header>
      {
        !totalPage
          ? (
            <>
              <EmptyArticle>
                <h2>등록된 문의사항이 없습니다.</h2>
                <p>문의 사항을 작성해보세요.</p>
                <button type="button" onClick={() => history.push('/question/save')}>문의사항 작성하기</button>
              </EmptyArticle>
            </>
          )
          : (
            <>
              <QuestionList>
                {
                  questionList.map((question: questionInterface) => (
                    <QuestionItem onClick={() => onClickDetail(question.questionId)} key={question.questionId}>
                      <QuestionTitleBox>
                        <QuestionText>
                          <h2>{question.title}</h2>
                          <p>등록일:{question.writeDate}</p>
                        </QuestionText>
                      </QuestionTitleBox>
                    </QuestionItem>
                  ))
                }
              </QuestionList>
              <ButtonWrap>
                <AddButton to="/question/save">작성하기</AddButton>
              </ButtonWrap>
            </>
          )
      }
      <PaginationBox>
        <PrevPage src="/images/icons/back.png" />
        <PageNumbers>
          {
            !totalPage
              ? (
                <PageNumber>
                  <PageLink to="/question">1</PageLink>
                </PageNumber>
              )
              : (
                <>
                  {
                    numberArrayUtill(totalPage).map((i) => (
                      <PageNumber>
                        <PageLink onClick={() => setPage(i - 1)} to={`/question?page=${i - 1}`}>{i}</PageLink>
                      </PageNumber>
                    ))
                  }
                </>
              )
          }
        </PageNumbers>
        <NextPage src="/images/icons/back.png" />
      </PaginationBox>
    </Layout>
  );
}

export default Question;
