import React, { useEffect, useState } from 'react';
import styled from 'styled-components';

import { Client } from '@stomp/stompjs';

import { NavLink, useParams } from 'react-router-dom';
import axios from 'axios';
import chattingConnection from '../../api/chattingApi';

interface ChattingUserInterface {
  userNickname: string,
}
interface useParamsID {
  id: string
}
interface sendDataInterface {
  messageType: string,
  joinId: string,
  nickname: string,
  content: string
}
interface activeInterface {
  active: boolean,
}
const ChattingWrapper = styled.div`
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
`;
const ChattingMessageHeader = styled.div`
  width: 100%;
  max-width: 600px;
  position: fixed;
  top: 0;
  
`;
const HeaderTop = styled.div`
  background-color: #3960a6;
  color: white;
  justify-content: space-between;
  font-size: 20px;
  display: flex;
  align-items: center;
  padding: 20px;
`;
const HeaderBottom = styled.div`
  background-color: white;
  border-bottom: 1px solid #e8e8e8;
  padding: 10px;
  display: flex;
  align-items: center;
  & img {
    width: 60px;
    height: 60px;
    margin-right: 20px;
  }
`;
const HeaderBottomContent = styled.div`
  & div {
    padding: 10px;
  }
`;
const ChattingBottom = styled.div`
  width: 100%;
  max-width: 600px;
  height: 50px;
  position: fixed;
  display: flex;
  align-items: center;
  bottom: 0;
  background-color: #e8e8e8;
  & img {
    width: 40px;
    margin-left: 5px;
  }
  & form {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-around;
  }
  & input {
    border: 0;
    width: 70%;
    border-radius: 20px;
    font-size: 15px;
    padding: 5px;
  }
`;
const OpponentChatting = styled.div`
  display: flex;
  align-items: center;
  align-self: flex-start;
`;
const MyChatting = styled.div`
  margin: 10px;
  align-self: flex-end;
  display: flex;
  & div {
    background-color: #3960a6;
    padding: 10px 15px;
    font-size: 15px;
    max-width: 200px;
    color: white;
    border-radius: 10px 10px 0px 10px;
  }
`;
const SubmitButton = styled.button<activeInterface>`
  font-size: 15px;
  background-color: ${(props) => (props.active ? '#3960a6' : '#aeaeae')};
  border-radius: 5px;
  color:  ${(props) => (props.active ? 'white' : '#e8e8e8')};
  border: 0;
  padding: 5px 20px;
`;

const SendTime = styled.span`
  color: #797979;
  font-size: 10px;
  margin: 0 5px;
  align-self: flex-end;
`;

const ChattingMessageWrapper = styled.div`
  padding: 10px;
  margin-top: 160px;
`;
const ChattingMessageProfile = styled.div`
  & img {
    width: 30px;
    height: 30px;
    border: 1px solid #e8e8e8;
    border-radius: 50%;
    margin-right: 10px;
  }
`;
const ChattingMessageList = styled.div`
  display: flex;
  flex-direction: column;
`;
const ChattingMessage = styled.div`
  background-color: #e8e8e8;
  padding: 10px 15px;
  font-size: 15px;
  max-width: 200px;
  border-radius: 10px 10px 10px 0px;
`;

function Chatting({ userNickname }: ChattingUserInterface):JSX.Element {
  const [message, setMessage] = useState('');
  const [chatting, setChatting] = useState<string[]>([]);
  const { id } = useParams<useParamsID>();
  console.log(id);
  const client = new Client({
    brokerURL: chattingConnection,
    debug(str: string) {
      console.log(str);
    },
    reconnectDelay: 1000000,
    heartbeatIncoming: 40000000,
    heartbeatOutgoing: 40000000,
  });

  client.onConnect = function (frame) {
    const roomIdApi = `http://local.corsmarket.ml/api/chat/room${id}`;
    console.log(id);
    axios.post(roomIdApi).then(({ data }) => {
      console.log(data);
      client.subscribe(`ws://local.corsmarket.ml/api/sub/chat/room/${data.roomId}`, (msg) => {
        if (msg.body) {
          setChatting([...chatting, msg.body]);
          console.log(msg.body);
        }
      });
    });
  };

  client.onStompError = function (frame):void {
    console.log(`Broker reported error: ${frame.headers.message}`);
    console.log(`Additional details: ${frame.body}`);
  };

  client.activate();
  const onSendMessage = () => {
    const sendData: sendDataInterface = { messageType: 'TALK', joinId: id, nickname: userNickname, content: message };
    client.publish({
      destination: 'ws://local.corsmarket.ml/api/pub/chat/message',
      body: JSON.stringify(sendData),
      headers: {},
    });
  };
  const onHandleChangeMessage = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(e.target.value);
  };
  return (
    <ChattingWrapper>
      <ChattingMessageHeader>
        <HeaderTop>
          <NavLink to="/chatting"><div>{'<'}</div></NavLink>
          <div>Josh</div>
        </HeaderTop>
        <HeaderBottom>
          <img src="http://image.yes24.com/momo/TopCate2917/MidCate006/291263568.jpg" alt="book_img" />
          <HeaderBottomContent>
            <div>영어책 읽기의 힘</div>
            <div>13000원</div>
          </HeaderBottomContent>
        </HeaderBottom>
      </ChattingMessageHeader>
      <ChattingMessageWrapper>
        <ChattingMessageList>
          <OpponentChatting>
            <ChattingMessageProfile>
              <img src="/images/icons/init_profile.png" alt="frofile" />
            </ChattingMessageProfile>
            <ChattingMessage>안녕반가워!</ChattingMessage>
            <SendTime>16:43</SendTime>
          </OpponentChatting>
          <MyChatting>
            <SendTime>16:43</SendTime>
            <div>안녕반가워!</div>
          </MyChatting>
          <MyChatting>
            <SendTime>16:43</SendTime>
            <div>거래하러왔니????</div>
          </MyChatting>
          <OpponentChatting>
            <ChattingMessageProfile>
              <img src="/images/icons/init_profile.png" alt="frofile" />
            </ChattingMessageProfile>
            <ChattingMessage>싸게 해줘</ChattingMessage>
            <SendTime>16:43</SendTime>
          </OpponentChatting>
        </ChattingMessageList>
      </ChattingMessageWrapper>
      <ChattingBottom>
        <form onSubmit={onSendMessage}>
          <input type="text" onChange={onHandleChangeMessage} placeholder="메세지를 입력하세요" />
          <SubmitButton active={message.length !== 0} disabled={message.length === 0} type="submit">전송</SubmitButton>
        </form>
      </ChattingBottom>
    </ChattingWrapper>
  );
}
export default Chatting;
