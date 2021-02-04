export interface articleInterface {
  articleId: number,
  countDAO: {
    chatCount: number,
    countId: number,
    views: number,
    wishCount: number,
  },
  title: string,
  writeDate: Date,
  tprice: number,
  progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING',
  category: {
    cid: number, oneDepth: string, twoDepth: string, threeDepth: string, fourDepth: string, fiveDepth: string,
  },
  image: string,
  nickname: string,
  market: null | number,
}
export interface articleDetailInterface {
  articleId: number,
  memberId: number,
  countDAO: {
    chatCount: number,
    countId: number,
    views: number,
    wishCount: number,
  },
  title: string,
  writeDate: Date,
  rprice: number,
  tprice: number,
  progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING',
  category: {
    cid: number, oneDepth: string, twoDepth: string, threeDepth: string, fourDepth: string, fiveDepth: string,
  },
  division: 'PURCHASE' | 'SALE',
  thumbnail: string,
  image: string[],
}

export interface PostListInterface {
  data: articleInterface[]
}

export interface AddBookPostInterface {
  memberId: number, // 작성자
  rprice: number, // 판매가격
  tprice: number, // 원래가격
  contens: string, // 내용
  progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING', // 게시글 상태
  title: string, // 책제목
  thumnail: string,
  images: string[]
  category: string, // 카테고리 키
  division: 'PURCHASE' | 'SALE', // 구매 판매 구분
}
