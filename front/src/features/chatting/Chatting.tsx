import React, { useState } from 'react';

import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

import styled from 'styled-components';
import { sendData } from '../../interfaces/Chatting.interface';

const Chatting = () => {
  const url = 'http://59.16.83.128:8080/';
  const sockJS = new SockJS(url);
  sockJS.onopen = function() {
    console.log('open');
  }
  const stompClient = Stomp.over(sockJS);

  const cunnectChat = (userName:string) => {
    stompClient.connect({},(frame) => {
      alert("connent!");
      stompClient.subscribe("/chat/send/" + userName,(response) => {
        const data = JSON.parse(response.body);
        // setData(data);
      })
    },() => {
      console.error("error");
    });
    stompClient.debug = function (){};
  }
  const sendMessage = ({chatRoomId,user_id,receiver}:sendData) => {
    // const sendMessageData = {'chatRoomId': chatRoomId, 'message': message, 'sender' :user_id, 'receiver': receiver};
    // 실제는 sample 이 아니라 sendMessageData 형식으로 들어가야 하지 않을까,,,.?
    // setData(data.concat(sampleMockMessage));
    // stompClient.send("/chat/send", {}, JSON.stringify(sendMessageData));
  }
  return (
    <div>
      
    </div>
  )
  
}

export default Chatting;