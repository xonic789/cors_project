export interface chattingInterface {
  messageId: number;
  message: string;
  time: Date;
  userId: string;
  sender: string;
  chatRoomId: string;
}
export interface sendDataInterface {
  chatRoomId:string,
  userId:string,
  receiver:string,
}
