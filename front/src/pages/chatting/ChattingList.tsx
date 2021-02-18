import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';

const ChattingListWrapper = styled.div`
  width: 100%;
  max-width: 600px;
  margin: auto;
`;
const ChattingHeader = styled.div`
  height: 60px;
  background-color: #3960a6;
  color: white;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  position: fixed;
  width: 600px;
`;
const Chatting = styled.ul`
  margin-top: 60px;
`;
const ChattingItem = styled.li`
  padding: 20px;
  border-bottom: 1px solid #e8e8e8;
  display: flex;
  align-items: center;
`;
const ChattingUserIcon = styled.div`
  & img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 1px solid #e8e8e8;
  }
`;
const ChattingUserContent = styled.div`
  margin-left: 20px;
`;
const UserContentTop = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
`;
const NickName = styled.div`
  margin-right: 10px;
  font-weight: 900;
`;
const LastChattingTime = styled.div`
  color: #b3b3b3;
`;
function ChattingList():JSX.Element {
  const data = [{ id: 1 }, { id: 2 }, { id: 3 }, { id: 4 }, { id: 5 }, { id: 6 }, { id: 7 }, { id: 8 }, { id: 9 }];
  return (
    <AppLayout activeId={1}>
      <ChattingListWrapper>
        <ChattingHeader>
          <div>Cors Market Message</div>
        </ChattingHeader>
        <Chatting>
          {data.map((v) => (
            <NavLink to={`chatting/${v.id}`}>
              <ChattingItem>
                <ChattingUserIcon>
                  <img src="/images/icons/init_profile.png" alt="frofile_img" />
                </ChattingUserIcon>
                <ChattingUserContent>
                  <UserContentTop>
                    <NickName>닉네임</NickName>
                    <LastChattingTime>10분 전</LastChattingTime>
                  </UserContentTop>
                  <div>네 거래 가능하세요</div>
                </ChattingUserContent>
              </ChattingItem>
            </NavLink>
          ))}
        </Chatting>
      </ChattingListWrapper>
    </AppLayout>
  );
}

export default ChattingList;
