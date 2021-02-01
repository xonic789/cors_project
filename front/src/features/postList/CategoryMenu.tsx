import React, { useCallback, useState, useEffect } from 'react';
import styled from 'styled-components';
import CategoryJSON from './Category.json';

interface CategoryMenuPropsInterFace {
  onMenuClose: (e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => void,
  isOppend: boolean,
}
const BurgerMenuWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 100vw;
  min-height: 100vh;
  background-color: white;
  overflow: auto;
`;
const BurgerMenuHeader = styled.div`
  display: flex;
  justify-content: flex-end;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: white;
  border-bottom: 1px solid #e8e8e8;
  padding: 10px;
`;
const BurgerMenuBody = styled.div`
  display:flex;
`;
const BurgerMenuList = styled.ul`
  margin-top: 80px;
  margin-bottom: 100px;
  display: flex;
  flex-direction: column;
  width: 100vw;
  min-height: 100vh;
  position: sticky;
`;
const BurgerMenuListTwo = styled.ul`
  margin-top: 80px;
  margin-bottom: 100px;
  display: flex;
  flex-direction: column;
  width: 100vw;
  min-height: 100vh;
  overflow: scroll;
`;
const BurgerMenuItem = styled.li`
  padding: 10px 30px;
  font-size: 20px;
`;
const CloseButtton = styled.button`
  background: white;
  border: 0;
  & img {
    width: 25px;
  }
`;
const CategoryMenu = ({ onMenuClose, isOppend }:CategoryMenuPropsInterFace):JSX.Element => {
  const [contentIndex, setContentIndex] = useState<number>(0);
  const [contentDetailIndex, setContentDetailIndex] = useState<number>(0);
  const onChangeCategotyTab = useCallback((index: number) => {
    setContentIndex(index);
  }, []);
  const onChangeCategotyDetailTab = useCallback((index:number) => {
    setContentDetailIndex(index);
    console.log(contentIndex, contentDetailIndex);
  }, [contentIndex, contentDetailIndex]);
  const categoryFilter = `${CategoryJSON.Category[contentIndex].title}>${CategoryJSON.Category[contentIndex].depth[contentDetailIndex].title}`;
  useEffect(() => {
    console.log(categoryFilter, contentDetailIndex);
  }, [categoryFilter, contentDetailIndex]);
  return (
    <BurgerMenuWrapper>
      <BurgerMenuHeader>
        <CloseButtton type="button" onClick={onMenuClose}>
          <img src="/images/icons/x.png" alt="x_button" />
        </CloseButtton>
      </BurgerMenuHeader>
      <BurgerMenuBody>
        <BurgerMenuList>
          {CategoryJSON.Category.map(
            (c, i) => (
              <BurgerMenuItem onClick={() => onChangeCategotyTab(i)}>
                {c.title}
              </BurgerMenuItem>
            ),
          )}
        </BurgerMenuList>
        <BurgerMenuListTwo>
          <BurgerMenuItem>전체보기</BurgerMenuItem>
          {CategoryJSON.Category[contentIndex].depth.map((c, i) => (
            <BurgerMenuItem onClick={() => onChangeCategotyDetailTab(i)}>
              {c.title}
            </BurgerMenuItem>
          ))}
        </BurgerMenuListTwo>
      </BurgerMenuBody>
    </BurgerMenuWrapper>
  );
};

export default CategoryMenu;
