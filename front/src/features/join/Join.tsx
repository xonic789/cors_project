import React, { useRef, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import DaumPostCode, { AddressData } from 'react-daum-postcode';
import { emailCertificationAsync, emailDuplicationAsync, joinRequestAsync, nicknameDuplicationAsync } from '../../api/joinApi';

const Positional = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  font-size: 4.5vw;
  @media screen and (min-width: 460px) {
    font-size: 20px;
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
  & h1 {
    font-size: 4.5vw;
    font-weight: 400;
  }
  @media screen and (min-width: 460px) {
    width: 460px;
    & h1 {
    font-size: 20px;
  }
  }
`;

const Main = styled.div`
  width: 100%;
  height: auto;
  padding: 1.5em 0;
  display: flex;
  justify-content: center;
  overflow: auto;
`;

const BackLink = styled(Link)`
  position: absolute;
  left: 0.5em;
`;

const BackLogo = styled.img`
  width: 1.8em;
  height: 1.8em;
`;

const JoinForm = styled.form`
  width: 90vw;
  display: flex;
  flex-direction: column;
  & > div:last-child {
    margin-bottom: 1.5em;
  }
  @media screen and (min-width: 460px) {
    width: 414px;
  }
  `;

const JoinInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
  & label {
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  @media screen and (min-width: 460px) {
    & label {
      font-size: 16.1px;
    }
  }
`;

const JoinInput = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  padding: 0.5em 0.5em;
  background: #f9f9f9;
  box-shadow: 3px 3px 10px #c4c4c4;
  border-radius: 5px;
  overflow: hidden;
`;

const Input = styled.input`
  width: 100%;
  font-size: 4vw;
  background: none;
  border: none;
  outline: none;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;

const CheckLogo = styled.img`
  width: 1.2em;
  height: 1.2em;
  flex-grow: 1;
  flex-shrink: 0;
`;

const CertificationRequest = styled.button`
  position: absolute;
  right: 0;
  font-size: 3vw;
  font-weight: bold;
  padding: 1em 1.5em;
  background: ;
  color: #fff;
  border: none;
  border-radius: 5px;
  outline: none;
  @media screen and (min-width: 460px) {
    font-size: 13.8px;
  }
`;

const CertificationBox = styled.div`
  display: none;
  flex-direction: column;
  margin-top: 0.5em;
`;

const CertificationInputBox = styled.div`
  display: flex;
  align-items: center;
`;

const CertificationInput = styled.input`
  width: 10em;
  font-size: 4vw;
  padding: 0.5em 1em;
  margin-right: 0.5em;
  background: #f9f9f9;
  box-shadow: 3px 3px 10px #c4c4c4;
  border-radius: 5px;
  border: none;
  outline: none;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;
const CertificationButton = styled.button`
  font-size: 4vw;
  font-weight: bold;
  padding: 0.5em 1em;
  background: #265290;
  color: #fff;
  border: none;
  border-radius: 5px;
  outline: none;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;

const InputMessage = styled.p`
  font-size: 2vw;
  margin-top: 1em;
  @media screen and (min-width: 460px) {
    font-size: 9.2px;
  }
`;

const SearchModal = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: #5c5c5c55;
`;

const CloseButton = styled.img`
  position: absolute;
  width: 1.5em;
  height: 1.5em;
  top: 0.4em;
  right: 0.4em;
  font-size: 6vw;
  @media screen and (min-width: 460px) {
    font-size: 27.6px;
  }
`;

const AddressFormBox = styled.div`
  margin-bottom: 2em;
  & label {
    display: block;
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  & ${JoinInput}:not(:last-child) {
    margin-bottom: 1em;
  }
  & ${JoinInput} {
    padding: 0.5em;
  }

  @media screen and (min-width: 460px) {
    & label {
      font-size: 16.1px;
    }
  }
`;

const AddressSearchBox = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 1em;
`;
const AddressSearchInput = styled.input`
  width: 10em;
  padding: 0.6em 0.5em;
  background: #f9f9f9;
  box-shadow: 3px 3px 10px #c4c4c4;
  border: none;
  outline: none;
  border-radius: 5px;
  margin-right: 0.5em;
  font-size: 4vw;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;
const AddressSearchButton = styled.button`
  padding: 0.7em;
  background: #265290;
  color: #fff;
  font-weight: bold;
  border: none;
  outline: none;
  border-radius: 5px;
  font-size: 3.5vw;
  @media screen and (min-width: 460px) {
    font-size: 16.1px;
  }
`;

const AgreementBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  margin-bottom: 2.5em;
  @media screen and (min-width: 460px) {
    width: 414px;
  }
`;
const AllAgreeBox = styled.div`
  display: flex;
  align-items: center;
  font-size: 4.5vw;
  font-weight: bold;
  margin-bottom: 1em;
  @media screen and (min-width: 460px) {
    font-size: 20px;
  }
`;
const AgreeCheckBox = styled.img`
  width: 1.5em;
  height: 1.5em;
  margin-right: 0.5em;
`;
const AgreeText = styled.div`
  & span {
    color: #265290;
  }
`;

const SubAgreeBox = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  font-size: 4vw;
  margin-bottom: 1em;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;

const AgreeDetail = styled.img`
  width: 1em;
  height: 1em;
  position: absolute;
  right: 0;
  transform: rotate(-90deg);
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
`;

const JoinButton = styled.button`
  width: 80vw;
  font-size: 4.5vw;
  padding: 0.8em 0;
  text-align: center;
  color: #fff;
  background: #265290;
  border: none;
  outline: none;
  margin-bottom: 1.5em;
  @media screen and (min-width: 460px) {
    width: 368px;
    font-size: 20px;
  }
`;

function Join():JSX.Element {
  const initialInputs = {
    email: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
      duplicationCheck: false,
      code: '',
      certificationCheck: false,
    },
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
    passwdCheck: {
      value: '',
      state: 'none',
      message: '',
      color: 'red',
    },
    address: {
      zipcode: '',
      baseAddress: '',
      detailAddress: '',
    },
    agreement: {
      all: false,
      agree1: false,
      agree2: false,
      agree3: false,
    },
  };

  const [showsModal, setShowsModal] = useState(false);
  const [inputs, setInputs] = useState(initialInputs);
  const emailCertificationBox = useRef<HTMLInputElement>(null);
  const history = useHistory();

  const {
    email,
    nickname,
    passwd,
    passwdCheck,
    address,
    agreement,
  } = inputs;

  const inputChange = (name: 'email' | 'nickname' | 'passwd' | 'passwdCheck', value: string, state: 'check' | 'fail' | 'none', message: string, color: 'red' | 'blue') => {
    setInputs({
      ...inputs,
      [name]: {
        ...inputs[name],
        value,
        state,
        message,
        color,
      },
    });
  };

  const onChangeText = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    switch (name) {
      case 'email':
        if (/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(value)) {
          inputChange(name, value, 'check', '', 'blue');
        } else {
          inputChange(name, value, 'fail', '이메일 형식이 아닙니다.', 'red');
        }
        break;
      case 'nickname':
        if (value.length < 4) {
          inputChange(name, value, 'fail', '닉네임을 4자 이상 입력해주세요.', 'red');
        } else {
          try {
            await nicknameDuplicationAsync(value);
            inputChange(name, value, 'check', '', 'blue');
          } catch {
            inputChange(name, value, 'fail', '이미 사용중인 닉네임입니다.', 'red');
          }
        }
        break;
      case 'passwd':
        if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/.test(value)) {
          inputChange(name, value, 'check', '', 'blue');
        } else {
          inputChange(name, value, 'fail', '8~20자의 영문 대소문자, 숫자, 특수문자 조합으로 설정해주세요.', 'red');
        }
        break;
      case 'passwdCheck':
        if (value === passwd.value) {
          inputChange(name, value, 'check', '', 'blue');
        } else {
          inputChange(name, value, 'fail', '비밀번호가 일치하지 않습니다.', 'red');
        }
        break;
      default:
        break;
    }
  };

  const onClickEmailDuplication = async () => {
    try {
      const result = await emailDuplicationAsync(email.value);

      if (result) {
        setInputs({
          ...inputs,
          email: {
            ...email,
            duplicationCheck: true,
          },
        });
        if (emailCertificationBox.current != null) {
          emailCertificationBox.current.style.display = 'flex';
        }
      } else {
        setInputs({
          ...inputs,
          email: {
            ...email,
            message: '이미 사용중인 이메일입니다.',
            color: 'red',
            duplicationCheck: false,
          },
        });
      }
    } catch (error) {
      alert('서버 통신중 오류가 발생하였습니다.');
    }
  };

  const onChangeCode = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputs({
      ...inputs,
      email: {
        ...email,
        code: e.target.value,
      },
    });
  };

  const onClickEmailCertification = async () => {
    try {
      const result = await emailCertificationAsync(email.value, email.code);

      if (result) {
        setInputs({
          ...inputs,
          email: {
            ...email,
            message: '인증이 완료되었습니다.',
            color: 'blue',
            certificationCheck: true,
          },
        });
        if (emailCertificationBox.current != null) {
          emailCertificationBox.current.style.display = 'none';
        }
      } else {
        setInputs({
          ...inputs,
          email: {
            ...email,
            message: '코드를 확인해주세요.',
            color: 'red',
            certificationCheck: false,
          },
        });
      }
    } catch {
      alert('서버 통신중 오류가 발생하였습니다.');
    }
  };

  const onCompleteAddressSelect = (data: AddressData) => {
    setInputs({
      ...inputs,
      address: {
        ...address,
        zipcode: data.zonecode,
        baseAddress: data.jibunAddress,
      },
    });
    setShowsModal(false);
  };

  const onChangeDetailAddress = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputs({
      ...inputs,
      address: {
        ...address,
        detailAddress: e.target.value,
      },
    });
  };

  const onClickAgree = (agree: 'all' | 'agree1' | 'agree2' | 'agree3') => {
    if (agree === 'all') {
      setInputs({
        ...inputs,
        agreement: {
          all: !agreement.all,
          agree1: !agreement.all,
          agree2: !agreement.all,
          agree3: !agreement.all,
        },
      });
    } else {
      const resultArr: boolean[] = [];

      Object.keys(agreement).forEach((key) => {
        if (key !== 'all' && (key === 'agree1' || key === 'agree2' || key === 'agree3')) {
          if (key === agree) {
            resultArr.push(!agreement[key]);
          } else {
            resultArr.push(agreement[key]);
          }
        }
      });

      if (agree === 'agree1' || agree === 'agree2' || agree === 'agree3') {
        setInputs({
          ...inputs,
          agreement: {
            ...agreement,
            all: resultArr.every((value) => value === true),
            [agree]: !agreement[agree],
          },
        });
      }
    }
  };

  const onsubmitJoin = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!email.certificationCheck) {
      alert('이메일 인증을 진행해주세요.');
    } else if (nickname.state !== 'check') {
      alert('닉네임이 중복되었거나 형식이 맞지 않습니다.');
    } else if (passwd.state !== 'check') {
      alert('비밀번호 형식이 맞지 않습니다.');
    } else if (passwdCheck.state !== 'check') {
      alert('비밀번호가 일치하지 않습니다.');
    } else if (address.zipcode === '') {
      alert('주소를 입력해주세요.');
    } else if (!agreement.agree1 || !agreement.agree2) {
      alert('필수 약관 항목에 동의해주세요.');
    } else {
      await joinRequestAsync(email.value, nickname.value, passwd.value, `${address.baseAddress} ${address.detailAddress}`);
      alert('회원가입이 완료되었습니다.');
      history.push('/');
    }
  };

  return (
    <Positional>
      <Header>
        <BackLink to="/">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>회원가입</h1>
      </Header>
      <Main>
        <JoinForm method="post" onSubmit={onsubmitJoin}>
          <JoinInputBox>
            <label htmlFor="email">이메일</label>
            <JoinInput>
              <Input required type="email" id="email" name="email" onChange={onChangeText} value={email.value} disabled={email.duplicationCheck} />
              <CheckLogo src={`/images/icons/${email.state}.png`} />
              <CertificationRequest style={{ display: email.state === 'check' && !email.certificationCheck ? 'block' : 'none' }} type="button" onClick={onClickEmailDuplication}>인증요청</CertificationRequest>
            </JoinInput>
            <CertificationBox ref={emailCertificationBox}>
              <CertificationInputBox>
                <CertificationInput name="certification" onChange={onChangeCode} value={email.code} />
                <CertificationButton type="button" onClick={onClickEmailCertification}>확인</CertificationButton>
              </CertificationInputBox>
            </CertificationBox>
            <InputMessage style={{ color: email.color }}>{email.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="nickname">닉네임</label>
            <JoinInput>
              <Input required type="text" maxLength={10} id="nickname" name="nickname" onChange={onChangeText} value={nickname.value} />
              <CheckLogo src={`/images/icons/${nickname.state}.png`} />
            </JoinInput>
            <InputMessage style={{ color: nickname.color }}>{nickname.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="passwd">비밀번호</label>
            <JoinInput>
              <Input maxLength={20} required type="password" id="passwd" name="passwd" onChange={onChangeText} value={passwd.value} />
              <CheckLogo src={`/images/icons/${passwd.state}.png`} />
            </JoinInput>
            <InputMessage style={{ color: passwd.color }}>{passwd.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="passwdCheck">비밀번호 확인</label>
            <JoinInput>
              <Input maxLength={20} required type="password" id="passwdCheck" name="passwdCheck" onChange={onChangeText} value={passwdCheck.value} />
              <CheckLogo src={`/images/icons/${passwdCheck.state}.png`} />
            </JoinInput>
            <InputMessage style={{ color: passwdCheck.color }}>{passwdCheck.message}</InputMessage>
          </JoinInputBox>
          <AddressFormBox>
            <label>주소</label>
            <AddressSearchBox>
              <AddressSearchInput disabled value={address.zipcode} />
              <AddressSearchButton type="button" onClick={() => setShowsModal(true)}>우편번호 검색</AddressSearchButton>
            </AddressSearchBox>
            <JoinInput>
              <Input type="text" disabled placeholder="상세 주소" value={address.baseAddress} />
            </JoinInput>
            <JoinInput>
              <Input type="text" placeholder="나머지 주소" onChange={onChangeDetailAddress} value={address.detailAddress} />
            </JoinInput>
          </AddressFormBox>
          <AgreementBox>
            <AllAgreeBox onClick={() => onClickAgree('all')}>
              <AgreeCheckBox src={`/images/icons/${agreement.all ? 'check' : 'check_default'}.png`} />
              <AgreeText>전체 약관에 동의합니다.</AgreeText>
            </AllAgreeBox>
            <SubAgreeBox onClick={() => onClickAgree('agree1')}>
              <AgreeCheckBox src={`/images/icons/${agreement.agree1 ? 'check' : 'check_default'}.png`} />
              <AgreeText>서비스 이용약관 <span>(필수)</span></AgreeText>
              <AgreeDetail src="/images/icons/back.png" />
            </SubAgreeBox>
            <SubAgreeBox onClick={() => onClickAgree('agree2')}>
              <AgreeCheckBox src={`/images/icons/${agreement.agree2 ? 'check' : 'check_default'}.png`} />
              <AgreeText>개인정보 처리방침 <span>(필수)</span></AgreeText>
              <AgreeDetail src="/images/icons/back.png" />
            </SubAgreeBox>
            <SubAgreeBox onClick={() => onClickAgree('agree3')}>
              <AgreeCheckBox src={`/images/icons/${agreement.agree3 ? 'check' : 'check_default'}.png`} />
              <AgreeText>이벤트 마케팅 수신 동의 <span>(선택)</span></AgreeText>
              <AgreeDetail src="/images/icons/back.png" />
            </SubAgreeBox>
          </AgreementBox>
          <ButtonWrapper>
            <JoinButton type="submit">회원가입</JoinButton>
          </ButtonWrapper>
        </JoinForm>
      </Main>
      <SearchModal style={{ display: showsModal ? 'flex' : 'none' }}>
        <CloseButton src="/images/icons/x.png" onClick={() => setShowsModal(false)} />
        <DaumPostCode onComplete={onCompleteAddressSelect} />
      </SearchModal>
    </Positional>
  );
}

export default Join;
