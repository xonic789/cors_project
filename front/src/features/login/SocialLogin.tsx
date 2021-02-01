import React from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { postSocialLogin } from './LoginSlice';
import kakaoLogo from './images/logo_kakao.png';
import googleLogo from './images/logo_google.png';
import facebookLogo from './images/logo_facebook.png';

const SocialLoginBox = styled.div`
  width: 11em;
  font-size: 4.5vw;
  display: flex;
  justify-content: space-between;
`;

const SocialItem = styled.img`
  width: 2.5em;
  height: 2.5em;
`;

function SocialLogin():JSX.Element {
  const dispatch = useDispatch();

  const onClickLoginHandler = (social:string) => {
    dispatch(postSocialLogin({ social }));
  };
  return (
    <SocialLoginBox>
      <SocialItem src={facebookLogo} onClick={() => onClickLoginHandler('facebook')} />
      <SocialItem src={googleLogo} onClick={() => onClickLoginHandler('google')} />
      <SocialItem src={kakaoLogo} onClick={() => onClickLoginHandler('kakao')} />
    </SocialLoginBox>
  );
}

export default SocialLogin;
