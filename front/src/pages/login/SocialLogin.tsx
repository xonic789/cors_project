import React from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { postSocialLoginRequest } from './userSlice';

const SocialLoginBox = styled.div`
  width: 11em;
  font-size: 4.5vw;
  display: flex;
  justify-content: space-between;
  @media screen and (min-width: 460px) {
    width: 222.7px;
    font-size: 20px;
  }
`;

const SocialItem = styled.img`
  width: 2.5em;
  height: 2.5em;
  border-radius: 10px;
  @media screen and (min-width: 460px) {
    width: 50px;
    height: 50px;
  }
`;

function SocialLogin():JSX.Element {
  const dispatch = useDispatch();

  const onClickLoginHandler = (social:string) => {
    dispatch(postSocialLoginRequest({ social }));
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
