import React, { useState } from 'react';
import styled from 'styled-components';
import { RootStateOrAny, useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { postLoginRequest } from './userSlice';

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

  const onLogin = (e:React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    dispatch(postLoginRequest({ user: { email: inputs.email, passwd: inputs.passwd }, history }));
  };

  return (
    <Form method="GET" onSubmit={onLogin}>
      <InputBox>
        <Input type="email" name="email" placeholder="아이디" value={inputs.email} onChange={handleChange} required />
        <Input type="password" name="passwd" placeholder="비밀번호" value={inputs.passwd} onChange={handleChange} required />
      </InputBox>
      <Button type="submit">로그인</Button>
    </Form>
  );
}

export default LoginForm;
