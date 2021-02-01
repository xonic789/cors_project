import React from 'react';
import styled from 'styled-components';
<<<<<<< HEAD
=======
import { useDispatch } from 'react-redux';
import { postSocialLogin } from './LoginSlice';
import kakaoLogo from './images/logo_kakao.png';
import googleLogo from './images/logo_google.png';
import facebookLogo from './images/logo_facebook.png';
>>>>>>> e81864fa6fa2a2b3e0e5463822257c93a9ed12f9

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
      <SocialItem src="/images/icons/p.png" onClick={() => onClickLoginHandler('facebook')} />
      <SocialItem src="/images/icons/g.png" onClick={() => onClickLoginHandler('google')} />
      <SocialItem src="/images/icons/k.png" onClick={() => onClickLoginHandler('kakao')} />
    </SocialLoginBox>
  );
}

export default SocialLogin;
