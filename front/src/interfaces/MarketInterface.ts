export interface marketListInterface {
  data: marketInterface[],
}
export interface marketArticleInterface {
  id: number,
  title: string,
  rprice: number,
  tprice: number,
  image: string,
}
export interface marketInterface {
  marketId: number,
  marketName: string,
  marketIntro: string,
  marketImage: string,
}
export interface marketDetailInterface {
  marketId: number,
  marketName: string,
  marketIntro: string,
  marketImage: string,
  totalPage: number,
  articleList: marketArticleInterface[],
}
