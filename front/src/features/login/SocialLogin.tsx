import React from 'react';
import styled from 'styled-components';

const SocialLoginBox = styled.div`
  width: 348px;
  display: flex;
  justify-content: space-between;
`;

const SocialItem = styled.img`
  width: 80px;
  height: 80px;
`;

function SocialLogin():JSX.Element {
  return (
    <SocialLoginBox>
      <SocialItem />
      <SocialItem />
      <SocialItem />
    </SocialLoginBox>
  );
}

export default SocialLogin;
