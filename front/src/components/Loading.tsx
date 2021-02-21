import React from 'react';
import styled from 'styled-components';
import Spinner from 'react-spinner-material';

const CustomSpinner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  & div {
    margin-bottom: 20px;
  }
`;

function Loading():JSX.Element {
  return (
    <CustomSpinner>
      <div>데이터를 불러오고 있습니다.<br /> 잠시만 기다려주세요.</div>
      <Spinner color="#004c9d" size={60} visible stroke={3} radius={50} />
    </CustomSpinner>
  );
}

export default Loading;
