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
  border: 2px solid #3162C7;
  border-radius: 8px;
  &:not(:last-child) {
    margin-bottom: 1em;
  }
`;

const Button = styled.button`
  width: 18em;
  text-align: center;
  padding: 1em 0;
  background: #6F96E9;
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

  return (
    <Form>
      <InputBox>
        <Input name="user_id" placeholder="아이디" value={inputs.user_id} onChange={handleChange} />
        <Input name="user_password" placeholder="비밀번호" value={inputs.user_password} onChange={handleChange} />
      </InputBox>
      <Button>로그인</Button>
    </Form>
  );
}

export default LoginForm;
