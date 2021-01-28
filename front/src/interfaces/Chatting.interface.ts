export interface chatting {
  message_id: number;
  message: string;
  time: Date;
  user_id: string;
  sender: string;
  chatRoomId: string;
}
export interface sendData {
  chatRoomId:string,
  user_id:string,
  receiver:string,
}