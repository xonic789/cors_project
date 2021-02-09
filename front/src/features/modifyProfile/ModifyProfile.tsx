import React from 'react';
import styled from 'styled-components';

const FormLayout = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 4vw;
  @media screen and (min-width: 430px) {
    font-size: 17.216px;
  }
`;

const Header = styled.header`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  & h1 {
      width: 100%;
      font-size: 4.5vw;
      padding: 1.5em;
  }
  @media screen and (min-width: 430px) {
    width: 430.4px;
    & h1 {
      font-size: 19.368px;
    }
  }
`;

const BackImg = styled.img`
  position: absolute;
  left: 1em;
  width: 2em;
  height: 2em;
`;

const ModifyButton = styled.button`
  position: absolute;
  font-size: 3vw;
  right: 1em;
  padding: 0.2em 0.7em;
  border: 2px solid #3162C7;
  outline: none;
  border-radius: 50px;
  color: #3162C7;
  background: none;
  @media screen and (min-width: 430px) {
    font-size: 12.912px;
  }
`;

const ImageBox = styled.div`
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 2.5em;
  @media screen and (min-width: 430px) {
    width: 430.4px;
  }
`;

const BackgroundImg = styled.div`
  width: 100%;
  height: 11em;
  background: #000;
  @media screen and (min-width: 430px) {
    width: 430.4px;
  }
`;

const ProfileImg = styled.div`
  position: absolute;
  bottom: -1.5em;
  width:5.5em;
  height: 5.5em;
  background: #464646;
  border-radius: 50%;
`;

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  margin-bottom: 2em;
  @media screen and (min-width: 430px) {
    width: 387.350px;
  }
`;

const InputGroup = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;

  & label {
      font-size: 3.5vw;
      margin-bottom: 1em;
  }

  & p {
      font-size: 3vw;
  }

  &:not(:last-child) {
      margin-bottom: 0.5em;
  }

  @media screen and (min-width: 430px) {
    & label {
      font-size: 15.064px;
    }
    & p {
      font-size: 12.912px;
    }
  }
`;

const Input = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  background:  #fffdfd;
  border-radius: 8px;
  box-shadow: 3px 3px 10px #cecece;
  padding: 0.5em 0;
  margin-bottom: 0.5em;

  & input {
      font-size: 4vw;
      width: 18em;
      padding: 0 1em;
      background: none;
      border: none;
      outline: none;
  }

  & img {
      width: 1.5em;
      height: 1.5em;
      margin-right: 1em;
  }

  @media screen and (min-width: 430px) {
    & input {
      font-size: 17.216px;
    }
  }
`;

const RemoveBox = styled.div`
    width: 90vw;
    display: flex;
    align-items: center;
    justify-content: space-between;

    & p {
      font-size: 3.5vw;
      color: gray;
    }

    & button {
        font-size: 3.8vw;
        font-weight: bold;
        background: none;
        border: none;
        outline: none;
        text-decoration: underline;
    }

    @media screen and (min-width: 430px) {
      width: 387.35px;
      & p {
        font-size: 15.064px;
      }
      & button {
        font-size: 16.35px;
      }
    }
`;

function ModifyProfile():JSX.Element {
  return (
    <FormLayout>
      <Header>
        <BackImg src="/images/icons/back.png" />
        <h1>프로필 편집</h1>
        <ModifyButton type="submit">완료</ModifyButton>
      </Header>
      <ImageBox>
        <BackgroundImg />
        <ProfileImg />
      </ImageBox>
      <InputBox>
        <InputGroup>
          <label htmlFor="nickname">닉네임</label>
          <Input>
            <input type="text" id="nickname" name="nickname" />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p>{null}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="passwd">현재 비밀번호</label>
          <Input>
            <input id="passwd" name="passwd" type="password" />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p>{null}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="newPasswd">새 비밀번호</label>
          <Input>
            <input id="newPasswd" name="newPasswd" type="password" />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p>{null}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="newPasswdCheck">새 비밀번호 확인</label>
          <Input>
            <input id="newPasswdCheck" name="newPasswdCheck" type="password" />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p>{null}</p>
        </InputGroup>
      </InputBox>
      <RemoveBox>
        <p>회원정보를 삭제하시겠어요?</p>
        <button type="button">회원 탈퇴</button>
      </RemoveBox>
    </FormLayout>
  );
}

export default ModifyProfile;
