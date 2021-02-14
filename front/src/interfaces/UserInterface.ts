export interface memberInterface {
  userId: string, // 고유번호
  nickname: string, // 닉네임
  profileImg: string,
  latitude: number, // 위도
  longitude: number, // 경도
  role: 'default' | 'admin' | '', // 권한
  articles: string[], // 내 글 고유번호 배열
  wishList: string[], // 내 찜 고유번호 배열
}

export interface modifyProfileInterface {
  nickname: string, // 닉네임
  passwd: string, // 이전 비밀번호
  newPasswd: string, // 새 비밀번호
}

export interface modifyProfileImageInterface {
  profileImage: File, // 변경 이미지 파일
}

export interface myWishListInterface {
  wishListId: string, // 찜 고유 번호
  articleId: string, // 글 고유 번호
  progress: 'completed' | 'hide' | 'trading' | 'posting', // 게시글 상태
  title: string, // 책제목
  sumnail: string, // 대표 이미지
}

export interface myArticleInterface {
  articleId: string, // 고유 번호
  progress: 'completed' | 'hide' | 'trading' | 'posting', // 게시글 상태
  title: string, // 책제목
  sumnail: string, // 대표 이미지
}
