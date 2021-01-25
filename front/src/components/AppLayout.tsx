import React, { useState } from 'react';
import styled from 'styled-components';
import PostList from '../features/postList/PostList';

interface TabInterface {
  [key: number]: JSX.Element,
}
interface MenuItemProps {
  active: boolean,
}

const AppLayoutWrapper = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;
const MenuWrapper = styled.div`
  margin-top: auto;
`;
const MenuList = styled.ul`
  display: flex;
  justify-content: space-around;
  padding: 15px 0px;
  border-top: 1px solid #bababa;
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

const AppLayout: React.FC = () => {
  const [active, setActive] = useState<number>(0);
  const tabTitle = [
    ['/images/icons/home2.png', '/images/icons/home.png', '홈'],
    ['/../images/icons/chat2.png', '/images/icons/chat.png', '채팅'],
    ['/../images/icons/market2.png', '/images/icons/market.png', '마켓'],
    ['/../images/icons/my2.png', '/images/icons/my.png', 'MY'],
  ];
  const tab:TabInterface = {
    0: <PostList />,
  };
  const onClickMenuHandler = (id:number) => {
    setActive(id);
  };

  return (
    <AppLayoutWrapper>
      {tab[active]}
      <MenuWrapper>
        <MenuList>
          {tabTitle.map((str, index) => (
            <MenuItem
              key={str[2]}
              onClick={() => onClickMenuHandler(index)}
              active={active === index}
            >
              <img src={active === index ? str[1] : str[0]} alt="icon" />
              <div>{str[2]}</div>
            </MenuItem>
          ))}
        </MenuList>
      </MenuWrapper>
    </AppLayoutWrapper>
  );
};

export default AppLayout;
