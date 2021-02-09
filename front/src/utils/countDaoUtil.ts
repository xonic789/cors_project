interface countDaoInterface {
  chatCount: 10,
  countId: 2,
  views: 3,
  wishCount: 5,
}
const countDaoUtil = (count: countDaoInterface):string => `조회 ${count.views} 채팅 ${count.chatCount} 찜 ${count.wishCount}`;

export default countDaoUtil;
