import React, { useState } from 'react';
import styled from 'styled-components';

const Form = styled.form`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 54.9px;
`;

const Input = styled.input`
  width: 100%;
  font-size: 30px;
  padding: 23px 35px;
  outline: none;
  border: 2px solid #3162C7;
  border-radius: 8px;
  &:not(:last-child) {
    margin-bottom: 38px;
  }
`;

const Button = styled.button`
  text-align: center;
  padding: 27px 0;
  background: #6F96E9;
  color: #FFF;
  font-size: 30px;
  border: none;
  border-radius: 200px;
  outline: none;
  margin-bottom: 33.1px;
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
