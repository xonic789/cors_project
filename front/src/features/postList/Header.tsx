import React, { useCallback, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import CategoryMenu from './CategoryMenu';
import { loadBookPostRequest } from './postSlice';

interface PostTabItemInterface {
  tab: number,
  active: number,
}

const HeaderWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  background-color: white;
  max-width: 600px;
  margin: 0 auto;
  z-index: 50;
`;
const TopWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 10px;
  padding: 10px 30px;
`;
const BuggerMenu = styled.button`
  align-self: center;
  background-color:white;
  border: none;
  & img{
    width: 30px;
  }
`;
const LogoWrapper = styled.div`
  display: flex;  
  align-items: center;
  justify-content: center;
  color: #3960a6;
  flex-basis: 2;
  font-size: 25px;
  & img{
    width: 50px;
  }
`;
const SearchInput = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
  background-color: #e9e9e9;
  width: 85%;
  margin: auto;
  & input {
    color: white;
    border:0;
    width:80%;
    background-color: inherit;
  }
  & img {
    width: 20px;
  }
`;
const PostTab = styled.ul`
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 5px;
  border: 1px solid #e9e9e9;
`;
const PostTabItem = styled.li<PostTabItemInterface>`
  padding: 10px 0;
  width: 100%;
  text-align: center;
  border-bottom: ${(props) => props.tab === props.active && '3px solid #3960a6'};
`;

function Header(): JSX.Element {
  const dispatch = useDispatch();
  const [openMenu, setOpenMenu] = useState(false);
  const [tabIndex, setTabIndex] = useState(0);
  const { filtering } = useSelector((state) => state.postSlice);
  const onOpenMenuHandler = useCallback((e : React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    setOpenMenu(true);
  }, []);
  const onCloseMenuHandler = useCallback(() => {
    setTabIndex(0);
    setOpenMenu(false);
  }, []);
  const onClickSalePostTab = useCallback(() => {
    setTabIndex(0);
    dispatch(loadBookPostRequest({ division: 'sales', category: filtering.category }));
  }, [dispatch, filtering.category]);
  const onClickPurchasPostTab = useCallback(() => {
    setTabIndex(1);
    dispatch(loadBookPostRequest({ division: 'purchase', category: filtering.category }));
  }, [dispatch, filtering.category]);
  return (
    <HeaderWrapper>
      {openMenu && <CategoryMenu onMenuClose={onCloseMenuHandler} />}
      <TopWrapper>
        <BuggerMenu onClick={onOpenMenuHandler}>
          <img src="/images/icons/category.png" alt="menu_icon" />
        </BuggerMenu>
        <LogoWrapper>
          <img src="/images/icons/logo.jpeg" alt="logo" />
        </LogoWrapper>
      </TopWrapper>
      <SearchInput>
        <input placeholder="책 이름을 검색해보세요!" />
        <img src="/images/icons/search.png" alt="search_icon" />
      </SearchInput>
      <PostTab>
        <PostTabItem onClick={() => onClickSalePostTab()} tab={0} active={tabIndex}>판매글</PostTabItem>
        <PostTabItem onClick={() => onClickPurchasPostTab()} tab={1} active={tabIndex}>구매글</PostTabItem>
      </PostTab>
    </HeaderWrapper>
  );
}

export default Header;
