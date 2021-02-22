import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { ToastsContainer, ToastsStore, ToastsContainerPosition } from 'react-toasts';
import styled from 'styled-components';
import { addQuestionRequestAsync } from '../../../api/questionApi';

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
  & label {
    font-size: 4vw;
    margin-bottom: 0.5em;
  }
  & input {
    font-size: 4.5vw;
    margin-bottom: 1.5em;
    padding: 0.5em;
    border: 1px solid #d4d4d4;
    border-radius: 10px;
  }
  & textarea {
    font-size: 4.5vw;
    padding: 1em;
    height: 15em;
    resize: none;
    border: 1px solid #d4d4d4;
    border-radius: 10px;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & label {
      font-size: 18.4px;
    }
    & input {
      font-size: 20.484px;
    }
    & textarea {
      font-size: 20.484px;
    }
  }
`;

const ButtonWrap = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
  padding: 3em 1em;
  & button {
      color: #fff;
      border: 1px solid #eee;
      border-radius: 10px;
      background: none;
      font-size: 3.5vw;
      padding: 0.5em 1em;
      margin-left: 1em;
  }
  & button.reset {
    background: #bbbbbb;
  }
  & button.save {
    background: #3960a6;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & button {
      font-size: 15.932px;
    }
  }
`;

function AddQuestion():JSX.Element {
  const history = useHistory();
  const [questionInputs, setQuestionInputs] = useState({
    title: '',
    content: '',
  });

  const onChangeInputs = (e: React.ChangeEvent<HTMLTextAreaElement> | React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setQuestionInputs({
      ...questionInputs,
      [name]: value,
    });
  };

  const onClickReset = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    setQuestionInputs({
      title: '',
      content: '',
    });
  };

  const onSubmitSaveInputs = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const result = await addQuestionRequestAsync(questionInputs);
    console.log(result);
    if (result.status === 200) {
      ToastsStore.success('문의사항 등록이 완료되었습니다.');
      history.push('/question');
    } else {
      ToastsStore.error('문의사항 등록중 오류가 발생하였습니다.');
    }
  };

  return (
    <Layout onSubmit={onSubmitSaveInputs}>
      <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      <Header>
        <BackLink to="/question">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>문의사항 작성</h1>
      </Header>
      <Content>
        <label htmlFor="title">제목</label>
        <input onChange={onChangeInputs} id="title" name="title" value={questionInputs.title} type="text" />
        <label htmlFor="content">내용</label>
        <textarea onChange={onChangeInputs} id="content" name="content" value={questionInputs.content} />
      </Content>
      <ButtonWrap>
        <button onClick={onClickReset} className="reset" type="button">reset</button>
        <button className="save" type="submit">save</button>
      </ButtonWrap>
    </Layout>
  );
}

export default AddQuestion;
