import React from 'react';
import styled from 'styled-components';

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
  const onClickLoginHandler = (social:string) => {
    switch (social) {
      case ('facebook'):
        break;
      case ('google'):
        break;
      case ('kakao'):
        break;
      default:
        break;
    }
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
