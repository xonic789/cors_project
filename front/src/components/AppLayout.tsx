import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
import PostList from '../features/postList/PostList';
import MyPage from '../features/mypage/MyPage';

interface AppLayoutInterface {
  children: JSX.Element;
}
interface MenuItemProps {
  active: boolean,
}

const AppLayoutWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding-bottom: 100px;
  max-width: 600px;
  margin: 0 auto;
`;
const MenuWrapper = styled.div`
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: white;
  max-width: 600px;
  margin: 0 auto;
`;
const MenuList = styled.ul`
  display: flex;
  justify-content: space-around;
  padding: 15px 0px;
  border-top: 1px solid #bababa;
  & a {
    color: inherit;
    text-decoration: none
  }
`;
const MenuItem = styled.li<MenuItemProps>`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: ${(props) => props.active && '#3960a6'};
  & div {
    font-size: 0.8rem;
    margin-top: 10px;
  }
  & img {
    display: block;
    width:30px;
  }
`;

function AppLayout({ children }: AppLayoutInterface):JSX.Element {
  const [active, setActive] = useState<number>(0);
  const tabTitle = [
    ['/images/icons/home.png', '/images/icons/home_active.png', '홈', 'home'],
    ['/../images/icons/chat.png', '/images/icons/chat_active.png', '채팅', 'chatting'],
    ['/../images/icons/market.png', '/images/icons/market_active.png', '마켓', 'market'],
    ['/../images/icons/my.png', '/images/icons/my_active.png', 'MY', 'mypage'],
  ];
  const onClickMenuHandler = (id:number) => {
    setActive(id);
  };
  return (
    <AppLayoutWrapper>
      {children}
      <MenuWrapper>
        <MenuList>
          {tabTitle.map((str, index) => (
            <NavLink to={str[3]} key={str[2]}>
              <MenuItem
                key={str[2]}
                onClick={() => onClickMenuHandler(index)}
                active={active === index}
              >
                <img src={active === index ? str[1] : str[0]} alt="icon" />
                <div>{str[2]}</div>
              </MenuItem>
            </NavLink>
          ))}
        </MenuList>
      </MenuWrapper>
    </AppLayoutWrapper>
  );
}

export default AppLayout;
