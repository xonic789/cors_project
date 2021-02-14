import React, { ChangeEvent, ChangeEventHandler, useEffect, useState } from 'react';
import styled from 'styled-components';

import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

import { useParams } from 'react-router-dom';
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
    const roomIdApi = `http://local.corsmarket.ml/api/chat/room/`;
    console.log(id);
    axios.post(roomIdApi, {params: { articleId: id}}).then(({ data }) => {
      console.log(data);
      client.subscribe(`ws://local.corsmarket.ml/api/sub/chat/room/${data.joinId}`, (msg) => {
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
    <div>
      <form onSubmit={onSendMessage}>
        <input type="text" onChange={onHandleChangeMessage} />
        <button type="submit">전송하기</button>
      </form>
    </div>
  );
}
export default Chatting;
