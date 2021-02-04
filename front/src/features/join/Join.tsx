import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import DaumPostCode, { AddressData } from 'react-daum-postcode';
import { RootStateOrAny, useDispatch, useSelector } from 'react-redux';
import {
  onChangeText,
  inputCheckOk,
  inputCheckFail,
  inputCheckNone,
  nicknameDuplicateCheck,
  emailCertificationRequest,
  emailCertificationCheck,
  setAddress,
  setAddressDetail,
  setAllAgreeClick,
  setAllAgree,
  setAgree,
  joinRequest,
} from './joinSlice';

const Positional = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  font-size: 4.5vw;
`;

const Header = styled.header`
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
  `;

const JoinInputBox = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
  & label {
    font-size: 3.5vw;
    margin-bottom: 0.5em;
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
  background: #6f96e9;
  color: #fff;
  border: none;
  border-radius: 5px;
  outline: none;
`;

const CertificationBox = styled.div`
  display: flex;
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
`;
const CertificationButton = styled.button`
  font-size: 4vw;
  font-weight: bold;
  padding: 0.5em 1em;
  background: #6f96e9;
  color: #fff;
  border: none;
  border-radius: 5px;
  outline: none;
`;

const InputMessage = styled.p`
  font-size: 2vw;
  margin-top: 1em;
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
`;
const AddressSearchButton = styled.button`
  padding: 0.7em;
  background: #6f96e9;
  color: #fff;
  font-weight: bold;
  border: none;
  outline: none;
  border-radius: 5px;
  font-size: 3.5vw;
`;

const AgreementBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 90vw;
  margin-bottom: 2.5em;
`;
const AllAgreeBox = styled.div`
  display: flex;
  align-items: center;
  font-size: 4.5vw;
  font-weight: bold;
  margin-bottom: 1em;
`;
const AgreeCheckBox = styled.img`
  width: 1.5em;
  height: 1.5em;
  margin-right: 0.5em;
`;
const AgreeText = styled.div`
  & span {
    color: #6f96e9;
  }
`;

const SubAgreeBox = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  font-size: 4vw;
  margin-bottom: 1em;
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
  background: #6f96e9;
  border: none;
  outline: none;
  margin-bottom: 1.5em;
`;

function Join():JSX.Element {
  const history = useHistory();
  const [showsModal, setShowsModal] = useState(false);
  const dispatch = useDispatch();
  const { email,
    certification,
    nickname,
    passwd,
    passwdCheck,
    emailDuplication,
    emailCertification,
    address,
    agreement,
  } = useSelector((state: RootStateOrAny) => state.joinSlice);

  const onClickCertification = () => {
    dispatch(emailCertificationRequest(email.value));
  };

  const onClickCertificationCheck = () => {
    dispatch(emailCertificationCheck({ email: email.value, code: certification.value }));
  };

  const onClickOpenModal = () => {
    setShowsModal(true);
  };

  const onClickCloseModal = () => {
    setShowsModal(false);
  };

  const OnCompleteSelectAddress = (data: AddressData) => {
    dispatch(setAddress({ zipCode: data.zonecode, address: data.jibunAddress }));
    setShowsModal(false);
  };

  const onChangeAddressDetail = (e: React.ChangeEvent<HTMLInputElement>) => {
    dispatch(setAddressDetail(e.target.value));
  };

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    switch (e.target.name) {
      case ('email'):
        if (/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i.test(e.target.value)) {
          dispatch(inputCheckOk({ name: 'email', message: '' }));
        } else if (e.target.value) {
          dispatch(inputCheckFail({ name: 'email', message: '이메일 형식이 아닙니다.' }));
        } else {
          dispatch(inputCheckNone({ name: 'email', message: '' }));
        }
        break;
      case ('nickname'):
        dispatch(nicknameDuplicateCheck({ nickname: e.target.value }));
        break;
      case ('passwd'):
        if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/.test(e.target.value)) {
          dispatch(inputCheckOk({ name: 'passwd', message: '' }));
        } else {
          dispatch(inputCheckFail({ name: 'passwd', message: '8~20자의 영문 대소문자, 숫자, 특수문자 조합으로 설정해주세요.' }));
        }
        if (passwdCheck.status === 'check') {
          dispatch(inputCheckFail({ name: 'passwdCheck', message: '비밀번호가 일치하지 않습니다.' }));
        }
        if (e.target.value === passwdCheck.value) {
          dispatch(inputCheckOk({ name: 'passwdCheck', message: '' }));
        }
        break;
      case ('passwdCheck'):
        if (e.target.value === passwd.value) {
          dispatch(inputCheckOk({ name: 'passwdCheck', message: '' }));
        } else {
          dispatch(inputCheckFail({ name: 'passwdCheck', message: '비밀번호가 일치하지 않습니다.' }));
        }
        break;
      default:
        break;
    }

    if (e.target.name === 'email'
      || e.target.name === 'certification'
      || e.target.name === 'nickname'
      || e.target.name === 'passwd'
      || e.target.name === 'passwdCheck'
    ) {
      dispatch(onChangeText({ name: e.target.name, value: e.target.value }));
    }
  };

  const onClickAllAgree = () => {
    if (agreement.all) {
      dispatch(setAllAgreeClick(false));
    } else {
      dispatch(setAllAgreeClick(true));
    }
  };

  const onClickAgree = (name: string) => {
    const resultArr: boolean[] = [];

    Object.keys(agreement).forEach((key) => {
      if (key !== 'all') {
        if (key === name) {
          resultArr.push(!agreement[key]);
        } else {
          resultArr.push(agreement[key]);
        }
      }
    });

    if (agreement.all) {
      dispatch(setAllAgree(false));
    } else if (resultArr.every((value) => value === true)) {
      dispatch(setAllAgree(true));
    }
    if (name === 'agree1' || name === 'agree2' || name === 'agree3') {
      dispatch(setAgree({ name, check: !agreement[name] }));
    }
  };

  const onSubmitJoin = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!emailCertification) {
      alert('이메일 인증을 진행해주세요.');
    } else if (nickname.status !== 'check') {
      alert('닉네임이 중복되었거나 형식이 맞지 않습니다.');
    } else if (passwd.status !== 'check') {
      alert('비밀번호 형식이 맞지 않습니다.');
    } else if (passwdCheck.status !== 'check') {
      alert('비밀번호가 일치하지 않습니다.');
    } else if (address.zipCode === '') {
      alert('주소를 입력해주세요.');
    } else if (!agreement.agree1 || !agreement.agree2) {
      alert('필수 약관 항목에 동의해주세요.');
    } else {
      dispatch(joinRequest({ email: email.value, passwd: passwd.value, nickname: nickname.value, address: `${address.addressBase} ${address.addressDetail}` }));
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
        <JoinForm method="post" onSubmit={onSubmitJoin}>
          <JoinInputBox>
            <label htmlFor="email">이메일</label>
            <JoinInput>
              <Input required type="email" id="email" name="email" onChange={onChangeInput} value={email.value} disabled={emailDuplication} />
              <CheckLogo src={`/images/icons/${email.status}.png`} />
              <CertificationRequest style={{ display: email.status === 'check' && !emailCertification ? 'block' : 'none' }} type="button" onClick={onClickCertification}>인증요청</CertificationRequest>
            </JoinInput>
            <CertificationBox style={{ display: emailDuplication && !emailCertification ? 'flex' : 'none' }}>
              <CertificationInputBox>
                <CertificationInput name="certification" onChange={onChangeInput} value={certification.value} />
                <CertificationButton type="button" onClick={onClickCertificationCheck}>확인</CertificationButton>
              </CertificationInputBox>
            </CertificationBox>
            <InputMessage style={{ color: email.color }}>{email.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="nickname">닉네임</label>
            <JoinInput>
              <Input required type="text" maxLength={10} id="nickname" name="nickname" onChange={onChangeInput} value={nickname.value} />
              <CheckLogo src={`/images/icons/${nickname.status}.png`} />
            </JoinInput>
            <InputMessage style={{ color: nickname.color }}>{nickname.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="passwd">비밀번호</label>
            <JoinInput>
              <Input maxLength={20} required type="password" id="passwd" name="passwd" onChange={onChangeInput} value={passwd.value} />
              <CheckLogo src={`/images/icons/${passwd.status}.png`} />
            </JoinInput>
            <InputMessage style={{ color: passwd.color }}>{passwd.message}</InputMessage>
          </JoinInputBox>
          <JoinInputBox>
            <label htmlFor="passwdCheck">비밀번호 확인</label>
            <JoinInput>
              <Input maxLength={20} required type="password" id="passwdCheck" name="passwdCheck" onChange={onChangeInput} value={passwdCheck.value} />
              <CheckLogo src={`/images/icons/${passwdCheck.status}.png`} />
            </JoinInput>
            <InputMessage style={{ color: passwdCheck.color }}>{passwdCheck.message}</InputMessage>
          </JoinInputBox>
          <AddressFormBox>
            <label>주소</label>
            <AddressSearchBox>
              <AddressSearchInput disabled onClick={onClickOpenModal} value={address.zipCode} />
              <AddressSearchButton type="button" onClick={onClickOpenModal}>우편번호 검색</AddressSearchButton>
            </AddressSearchBox>
            <JoinInput>
              <Input type="text" disabled placeholder="상세 주소" value={address.addressBase} />
            </JoinInput>
            <JoinInput>
              <Input onChange={onChangeAddressDetail} type="text" placeholder="나머지 주소" value={address.addressDetail} />
            </JoinInput>
          </AddressFormBox>
          <AgreementBox>
            <AllAgreeBox onClick={onClickAllAgree}>
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
        <CloseButton src="" onClick={onClickCloseModal} />
        <DaumPostCode onComplete={OnCompleteSelectAddress} />
      </SearchModal>
    </Positional>
  );
}

export default Join;
