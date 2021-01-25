import React, { useState } from 'react';
import styled from 'styled-components';

const Form = styled.form`
  width: 90%;
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
  border: 2px solid rgba(49, 98, 199, 0.5);
  border-radius: 8px;
  box-shadow: 5px 5px 10px rgba(49, 98, 199, 0.5);
  &:not(:last-child) {
    margin-bottom: 1em;
  }
`;

const Button = styled.button`
  width: 18em;
  text-align: center;
  padding: 1em 0;
  background: #6F96E9;
  font-weight: bold;
  color: #FFF;
  font-size: 4.5vw;
  border: none;
  border-radius: 200px;
  outline: none;
  margin-bottom: 1.2em;
`;

function LoginForm():JSX.Element {
  const [inputs, setInputs] = useState({
    user_id: '',
    user_password: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onLogin = (e:React.FormEvent<HTMLFormElement>) => {
    const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const spaceRegExp = /\s/g;
    const isEmail = (text: string) => text.match(emailRegExp);
    const isSpace = (text: string) => text.match(spaceRegExp);
    if (!isEmail(inputs.user_id)) {
      alert('이메일 에러');
      e.preventDefault();
    }
    if (isSpace(inputs.user_password)) {
      alert('공백 에러');
      e.preventDefault();
    }
  };

  return (
    <Form method="POST" onSubmit={onLogin}>
      <InputBox>
        <Input type="text" name="user_id" placeholder="아이디" value={inputs.user_id} onChange={handleChange} />
        <Input type="password" name="user_password" placeholder="비밀번호" value={inputs.user_password} onChange={handleChange} />
      </InputBox>
      <Button type="submit">로그인</Button>
    </Form>
  );
}

export default LoginForm;
