import React from 'react';
import styled from 'styled-components';

const SocialLoginBox = styled.div`
  width: 11em;
  font-size: 4.5vw;
  display: flex;
  justify-content: space-between;
  & a {
    width: 2.5em;
    height: 2.5em;
    border-radius: 10px;
    overflow: hidden;
  }
  @media screen and (min-width: 460px) {
    width: 222.7px;
    font-size: 20px;
    & a {
      width: 50px;
      height: 50px;
    }
  }
`;

const SocialItem = styled.img`
  width: 2.5em;
  height: 2.5em;
  @media screen and (min-width: 460px) {
    width: 50px;
    height: 50px;
  }
`;

function SocialLogin():JSX.Element {
  return (
    <SocialLoginBox>
      <a href="/api/oauth2/authorization/naver">
        <SocialItem src="/images/icons/n.png" />
      </a>
      <a href="/api/oauth2/authorization/kakao">
        <SocialItem src="/images/icons/k.png" />
      </a>
      <a href="/api/oauth2/authorization/google">
        <SocialItem src="/images/icons/g.png" />
      </a>
    </SocialLoginBox>
  );
}

export default SocialLogin;
