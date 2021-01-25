import React from 'react';
import styled from 'styled-components';
import facebookLogo from './images/logo_facebook.png';
import googleLogo from './images/logo_google.png';
import kakaoLogo from './images/logo_kakao.png';

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
  return (
    <SocialLoginBox>
      <SocialItem src={facebookLogo} />
      <SocialItem src={googleLogo} />
      <SocialItem src={kakaoLogo} />
    </SocialLoginBox>
  );
}

export default SocialLogin;
