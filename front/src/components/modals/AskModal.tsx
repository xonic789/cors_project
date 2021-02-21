import React, { useState } from 'react';
import styled from 'styled-components';

interface ButtonInterface {
  buttonColor: string,
}
interface AskModalInterface {
  visible: boolean,
  title: string,
  description: string,
  confirmText: string,
  cancelText: string,
  onConfirm: () => void,
  onCancel: () => void,
}
const Fullscreen = styled.div`
  position: fixed;
  z-index: 30;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.25);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const AskModalBlock = styled.div`
  width: 320px;
  background-color: white;
  padding:1.5rem;
  border-radius: 4px;
  box-shadow: 0px 0px 8px rgba(0,0,0,0.125);
  h2 {
    margin-top: 0;
    margin-bottom: 1rem;
  }
  p {
    margin-bottom: 3rem;
  }
  .buttons {
    display: flex;
    justify-content: flex-end;
    background-color: none;
  }
`;

const ButtonWrapper = styled.button`
`;
const StyledButton = styled.button<ButtonInterface>`
  height: 2rem;
  border: 0;
  padding: 5px;
  background-color: none;
  color: ${(props) => props.buttonColor};
  & + & {
    margin-left: 0.75rem;
  }
`;

function AskModal({
  visible,
  title,
  description,
  confirmText = '확인',
  cancelText = '취소',
  onConfirm,
  onCancel,
}: AskModalInterface):JSX.Element|null {
  if (!visible) return null;
  return (
    <Fullscreen>
      <AskModalBlock>
        <h2>{title}</h2>
        <p>{description}</p>
        <ButtonWrapper>
          <StyledButton buttonColor="#4b4b4b" onClick={onCancel}>{cancelText}</StyledButton>
          <StyledButton buttonColor="#ff4242" onClick={onConfirm}>{confirmText}</StyledButton>
        </ButtonWrapper>
      </AskModalBlock>
    </Fullscreen>
  );
}

export default AskModal;
