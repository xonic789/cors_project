import React, { useCallback, useState } from 'react';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import CategoryJSON from './Category.json';
import { loadBookPostRequest } from './postSlice';

interface CategoryMenuPropsInterFace {
  onMenuClose: () => void,
}
interface CategoryTabInterface {
  tab: number;
  activeTab: number;
}
const BurgerMenuWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  width: 100%;
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
  margin-top: 80px;
  margin-bottom: 100px;
  padding: 10px;
`;
const CategoryBody = styled.div`
  overflow: scroll;
`;
const CategoryTop = styled.div`
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;
const CategoryTab = styled.div<CategoryTabInterface>`
  color: ${(props) => (props.tab === props.activeTab ? '#3960a6' : '#c4c4c4')};
`;
const ViewAll = styled.div`
  margin-right:0;
  font-size: 13px;
  &:after {
    content: "〉"; 
    margin: 0 3px 0 8px;
  }
`;
const CategoryList = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content:center;
  font-size: 13px;
`;
const CategoryItem = styled.div`
  width: 48%;
  padding: 10px 5px;
  margin: 2px;
  border: 1px solid #b6b6b6;
  border-radius: 10px;
`;
const CloseButtton = styled.button`
  background: white;
  border: 0;
  & img {
    width: 25px;
  }
`;
function CategoryMenu({ onMenuClose }:CategoryMenuPropsInterFace):JSX.Element {
  const dispatch = useDispatch();
  const [categoryTab, setCategoryTab] = useState<number>(0);
  const onHandleCategoryTab = useCallback((index: number) => {
    setCategoryTab(index);
  }, []);
  const onChangeCategotyDetailTab = useCallback((index1: number, index:number) => {
    const CategotyFilter = `${CategoryJSON.Category[index1].title}>${CategoryJSON.Category[index1].depth[index].title}`;
    dispatch(loadBookPostRequest({ division: 'sales', category: CategotyFilter }));
    onMenuClose();
  }, [dispatch, onMenuClose]);
  const onHandleViewAll = useCallback(() => {
    dispatch(loadBookPostRequest({ division: 'sales', category: `${CategoryJSON.Category[categoryTab].title}` }));
    onMenuClose();
  }, [categoryTab, dispatch, onMenuClose]);
  return (
    <BurgerMenuWrapper>
      <BurgerMenuHeader>
        <CloseButtton type="button" onClick={onMenuClose}>
          <img src="/images/icons/x.png" alt="x_button" />
        </CloseButtton>
      </BurgerMenuHeader>
      <BurgerMenuBody>
        <CategoryBody>
          <CategoryTop>
            {CategoryJSON.Category.map((c, i) => (
              <CategoryTab tab={i} activeTab={categoryTab} onClick={() => onHandleCategoryTab(i)}>{c.title}</CategoryTab>
            ))}
            <ViewAll onClick={onHandleViewAll}>전체보기</ViewAll>
          </CategoryTop>
          <CategoryList>
            {CategoryJSON.Category[categoryTab].depth.map((categoryDetail, index) => (
              <CategoryItem onClick={() => onChangeCategotyDetailTab(categoryTab, index)}>
                {categoryDetail.title}
              </CategoryItem>
            ))}
          </CategoryList>
        </CategoryBody>
      </BurgerMenuBody>
    </BurgerMenuWrapper>
  );
}

export default CategoryMenu;
