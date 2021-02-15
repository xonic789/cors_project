import { articleInterface } from "./PostList.interface";

export interface member {
  memberId: string,
  email: string,
  address: string,
  latitude: number,
  longitude: number,
  nickname: string,
  role: string,
  articles: articleInterface[],
  wishList: wishListInterface[]
}

export interface wishListInterface {
  wishId: string,
  memberId: string,
  articleId: string,
}
