import React from 'react';
import styled from 'styled-components';

const Wrapper = styled.div`
  width: 100%;
  max-width: 600px;
  height: 100%;
  background-color: #3960a6;
  & div {
    color: white;
    font-size: 20px;
    font-weight: 800;
  }
`;
function ChattingSub():JSX.Element {
  return (
    <Wrapper>
      <div>서비스를 준비중입니다</div>
    </Wrapper>
  );
}

export default ChattingSub;
