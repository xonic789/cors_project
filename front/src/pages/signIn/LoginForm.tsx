import React, { useState } from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { ToastsContainer, ToastsContainerPosition, ToastsStore } from 'react-toasts';
import { postLoginRequest, postLoginRequestError, postLoginRequestSuccess } from './userSlice';
import { LOGIN_ERROR, postLoginAsync, SERVER_ERROR } from '../../api/userApi';

interface inputFormInterface {
  email: string,
  passwd: string
}

const Form = styled.form`
  width: 90vw;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 1.8em;
`;

const Input = styled.input`
  width: 18em;
  font-size: 4.5vw;
  padding: 0.8em 1.3em;
  outline: none;
  border: 2px solid #3960a688;
  border-radius: 8px;
  box-shadow: 5px 5px 10px #3960a655;
  &:not(:last-child) {
    margin-bottom: 1em;
  }
  @media screen and (min-width: 460px) {
    font-size: 20px;
    padding: 16px 26px;
    margin-bottom: 20px;
  }
`;

const Button = styled.button`
  width: 18em;
  text-align: center;
  padding: 1em 0;
  background: #3960a6;
  font-weight: bold;
  color: #FFF;
  font-size: 4.5vw;
  border: none;
  border-radius: 200px;
  outline: none;
  margin-bottom: 1.2em;
  @media screen and (min-width: 460px) {
    font-size: 20px;
    padding: 20px 0;
    margin-bottom: 24px;
  }
`;

function LoginForm(): JSX.Element {
  const [inputs, setInputs] = useState<inputFormInterface>({
    email: '',
    passwd: '',
  });

  const dispatch = useDispatch();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const history = useHistory();

  const onLogin = async (e:React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const result = await postLoginAsync({ email: inputs.email, passwd: inputs.passwd });
      console.log(result, 'asdasdsad');
      dispatch(postLoginRequestSuccess(result));
      history.push('/home');
    } catch (error) {
      dispatch(postLoginRequestError(error.message));
      if (error.message === LOGIN_ERROR) {
        ToastsStore.error('로그인 정보를 확인해주세요.');
      } else if (error.message === SERVER_ERROR) {
        ToastsStore.error('서버 통신중 에러 발생');
      }
    }
  };

  return (
    <Form method="GET" onSubmit={onLogin}>
      <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      <InputBox>
        <Input type="email" name="email" placeholder="아이디" value={inputs.email} onChange={handleChange} required />
        <Input type="password" name="passwd" placeholder="비밀번호" value={inputs.passwd} onChange={handleChange} required />
      </InputBox>
      <Button type="submit">로그인</Button>
    </Form>
  );
}

export default LoginForm;
