interface articleInterface {
  postId: number, // 게시글 기본키
  memberId: number, // 작성자
  rprice: number, // 판매가격
  contens: string, // 내용
  writeDate: Date, // 작성일자
  progress: 'completed' | 'hide' | 'trading' | 'posting', // 게시글 상태
  title: string, // 책제목
  Images: { // 이미지들
    sumnail: string,
    image: string[]
  },
  cid: number, // 카테고리 키
  tprice: number, // 원래가격
  division: 'purchase' | 'sale', // 구매 판매 구분
}

interface PostListInterface {
  article: articleInterface[]
}

const PostList:PostListInterface = {
  article: [
    {
      postId: 1,
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '책제목',
      Images: {
        sumnail: '',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      cid: 9,
      tprice: 30000,
      division: 'sale',
    },
  ],
};

export default PostList;
