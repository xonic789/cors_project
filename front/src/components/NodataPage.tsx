import React from 'react';
import styled from 'styled-components';

const NodataWrapper = styled.div`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
`;
function NodataPage(): JSX.Element {
  return (
    <NodataWrapper>
      검색결과가 없습니다
    </NodataWrapper>
  );
}

export default NodataPage;
