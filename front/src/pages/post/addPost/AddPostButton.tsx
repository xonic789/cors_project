import React, { useCallback, useState } from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

interface BoxInterface {
  show: boolean;
}
const ButtonWrapper = styled.div`
  position:fixed;
  width: 100%;
  max-width: 600px;
  z-index: 30;
  bottom: 100px;
  padding-right: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;
const Button = styled.button`
  border: 0;
  border-radius: 50%;
  background-color: #3960a6;
  z-index: 100;
  width: 50px;
  height: 50px;
  font-size: 20px;
  color: white;
  & img {
    width: 30px;
    height: 30px;
  }
`;
const Box = styled.button<BoxInterface>`
  border: 0;
  border-radius: 20px;
  background-color: #4571c4;
  height: 30px;
  font-size: 13px;
  color: white;
  margin-bottom: 10px;
  padding: 0px 15px;
  display: ${(props) => (props.show ? 'block' : 'none')};
`;
const AddPostButton = ():JSX.Element => {
  const [showBox, setShowBox] = useState(false);
  const onClickShowBox = useCallback(() => {
    setShowBox(!showBox);
  }, [showBox]);

  return (
    <ButtonWrapper>
      <NavLink to="/addPost/sales/user"><Box show={showBox}>판매글 작성하러가기</Box></NavLink>
      <NavLink to="/addPost/purchase/user"><Box show={showBox}>구매글 작성하러가기</Box></NavLink>
      <Button onClick={onClickShowBox}>{showBox ? 'X' : <img src="/images/icons/write.png" alt="+_img" />}</Button>
    </ButtonWrapper>
  );
};

export default AddPostButton;
