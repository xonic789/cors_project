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

interface inputForm {
  email:string,
  passwd:string
  passwdCheck:string,
  nickname:string,
}

const JoinForm:React.FC = () => {
  const [inputs, setInputs] = useState<inputForm>({
    email: '',
    passwd: '',
    passwdCheck: '',
    nickname: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onJoin = (e:React.FormEvent<HTMLFormElement>) => {
    const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const spaceRegExp = /\s/g;

    const isEmail = (text: string) => text.match(emailRegExp);
    const isSpace = (text: string) => text.match(spaceRegExp);
    const passwdCompare = inputs.passwd === inputs.passwdCheck;

    if (!isEmail(inputs.email)) {
      alert('이메일 에러');
      e.preventDefault();
    } else if (isSpace(inputs.passwd)) {
      alert('공백 에러');
      e.preventDefault();
    } else if (!passwdCompare) {
      alert('비밀번호 불일치');
      e.preventDefault();
    } else {
      alert('회원가입 요청');
    }
  };

  return (
    <Form method="POST" onSubmit={onJoin}>
      <InputBox>
        <Input type="text" name="email" placeholder="아이디" value={inputs.email} onChange={handleChange} />
        <Input type="password" name="passwd" placeholder="비밀번호" value={inputs.passwd} onChange={handleChange} />
        <Input type="password" name="passwdCheck" placeholder="비밀번호 확인" value={inputs.passwdCheck} onChange={handleChange} />
        <Input type="text" name="nickname" placeholder="닉네임" value={inputs.nickname} onChange={handleChange} />
      </InputBox>
      <Button type="submit">회원가입</Button>
    </Form>
  );
};

export default JoinForm;
