export interface articleInterface {
  articleId: string, // 게시글 기본키
  memberId: number, // 작성자
  rprice: number, // 판매가격
  contens: string, // 내용
  writeDate: Date, // 작성일자
  progress: 'completed' | 'hide' | 'trading' | 'posting', // 게시글 상태
  title: string, // 책제목
  image: string,
  category: CategoryInterface, // 카테고리 키
  tprice: number, // 원래가격
  division: 'purchase' | 'sale', // 구매 판매 구분
}

export interface CategoryInterface {
  cid: number,
  oneDepth: string,
  twoDepth: string
  threeDepth: string,
}

export interface PostListInterface {
  data: articleInterface[]
}

export interface addPostInterface {
  rprice: number, // 판매가격
  contens: string, // 내용
  title: string, // 책제목
  Images: { // 이미지들
    sumnail: string,
    image: string[]
  },
  category: CategoryInterface, // 카테고리
  tprice: number, // 원래가격
}
