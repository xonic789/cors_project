import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { nicknameDuplicationAsync } from '../../api/joinApi';
import { postModifyProfileRequest } from '../login/userSlice';

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
  cursor: pointer;
  position: absolute;
  left: 1em;
  width: 2em;
  height: 2em;
`;

const ModifyButton = styled.button`
  cursor: pointer;
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
      margin-bottom: 1em;
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
  const { user } = useSelector((state) => state.userSlice);
  const { nickname, profileImg } = user;
  const dispatch = useDispatch();
  const history = useHistory();

  const [modifyInputs, setModifInputs] = useState({
    nickname: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
    },
    passwd: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
    },
    newPasswd: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
    },
    newPasswdCheck: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
    },
  });

  const onChangeInuts = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    let passwdChekInput: {
      value: string,
      state: string,
      message: string,
      color: string,
    } = {
      value: '',
      state: '',
      message: '',
      color: '',
    };

    switch (name) {
      case 'nickname':
        if (value === nickname) {
          setModifInputs({
            ...modifyInputs,
            nickname: {
              ...modifyInputs.nickname,
              value,
              state: 'none',
              message: '',
            },
          });
        } else if (value.length < 4) {
          setModifInputs({
            ...modifyInputs,
            nickname: {
              value,
              state: 'fail',
              message: '닉네임을 4자 이상 입력해주세요.',
              color: 'red',
            },
          });
        } else {
          setModifInputs({
            ...modifyInputs,
            nickname: {
              ...modifyInputs.nickname,
              value,
              state: 'check',
              message: '',
            },
          });
        }
        break;
      case 'passwd':
        setModifInputs({
          ...modifyInputs,
          passwd: {
            ...modifyInputs.passwd,
            value,
            state: 'check',
          },
        });
        break;
      case 'newPasswd':
        if (modifyInputs.newPasswdCheck.value !== '' && modifyInputs.newPasswdCheck.value !== value) {
          passwdChekInput = {
            ...modifyInputs.newPasswdCheck,
            state: 'fail',
            message: '비밀번호가 일치하지 않습니다.',
            color: 'red',
          };
        } else {
          passwdChekInput = {
            ...modifyInputs.newPasswdCheck,
            state: 'check',
            message: '',
          };
        }
        if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/.test(value)) {
          setModifInputs({
            ...modifyInputs,
            newPasswd: {
              ...modifyInputs.newPasswd,
              value,
              state: 'check',
              message: '',
            },
            newPasswdCheck: passwdChekInput,
          });
        } else {
          setModifInputs({
            ...modifyInputs,
            newPasswd: {
              value,
              state: 'fail',
              message: '8~20자의 영문 대소문자, 숫자, 특수문자 조합으로 설정해주세요.',
              color: 'red',
            },
            newPasswdCheck: passwdChekInput,
          });
        }
        break;
      case 'newPasswdCheck':
        if (modifyInputs.newPasswd.value === value) {
          setModifInputs({
            ...modifyInputs,
            newPasswdCheck: {
              ...modifyInputs.newPasswdCheck,
              value,
              state: 'check',
              message: '',
            },
          });
        } else {
          setModifInputs({
            ...modifyInputs,
            newPasswdCheck: {
              value,
              state: 'fail',
              message: '비밀번호가 일치하지 않습니다.',
              color: 'red',
            },
          });
        }
        break;
      default:
        break;
    }
  };

  const onSubmitModifyProfile = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (modifyInputs.nickname.state !== 'check') {
      alert('닉네임을 확인해주세요.');
    } else if (modifyInputs.passwd.state !== 'check') {
      alert('비밀번호를 확인해주세요.');
    } else if (modifyInputs.newPasswd.state !== 'check') {
      alert('새비밀번호를 확인해주세요.');
    } else if (modifyInputs.newPasswdCheck.state !== 'check') {
      alert('비밀번호가 일치하지 않습니다.');
    } else {
      try {
        const result = await nicknameDuplicationAsync(modifyInputs.nickname.value);
        console.log(result);
        if (result) {
          dispatch(postModifyProfileRequest({
            modifyProfile: { nickname: modifyInputs.nickname.value, passwd: modifyInputs.passwd.value, newPasswd: modifyInputs.newPasswd.value },
            modifyInputs,
            setModifInputs,
          }));
        }
      } catch {
        alert('서버통신중 에러발생');
      }
    }
  };

  return (
    <FormLayout method="post" onSubmit={onSubmitModifyProfile}>
      <Header>
        <BackImg src="/images/icons/back.png" onClick={() => history.push('/mypage')} />
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
            <input type="text" onChange={onChangeInuts} id="nickname" name="nickname" value={modifyInputs.nickname.value} />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p style={{ color: modifyInputs.nickname.color }}>{modifyInputs.nickname.message}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="passwd">현재 비밀번호</label>
          <Input>
            <input id="passwd" name="passwd" onChange={onChangeInuts} type="password" value={modifyInputs.passwd.value} />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p style={{ color: modifyInputs.passwd.color }}>{modifyInputs.passwd.message}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="newPasswd">새 비밀번호</label>
          <Input>
            <input id="newPasswd" name="newPasswd" onChange={onChangeInuts} type="password" value={modifyInputs.newPasswd.value} />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p style={{ color: modifyInputs.newPasswd.color }}>{modifyInputs.newPasswd.message}</p>
        </InputGroup>
        <InputGroup>
          <label htmlFor="newPasswdCheck">새 비밀번호 확인</label>
          <Input>
            <input id="newPasswdCheck" name="newPasswdCheck" onChange={onChangeInuts} type="password" value={modifyInputs.newPasswdCheck.value} />
            <img src="/images/icons/none.png" alt="" />
          </Input>
          <p style={{ color: modifyInputs.newPasswdCheck.color }}>{modifyInputs.newPasswdCheck.message}</p>
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
