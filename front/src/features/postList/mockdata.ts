import faker from 'faker';
import { AddBookPostInterface, PostListInterface } from '../../interfaces/PostList.interface';

export const dummyAddBookPost = (data: AddBookPostInterface) => ({
  articleId: faker.random.number(),
  memberId: data.memberId, // 내가 넣어줄 값
  rprice: data.rprice, // 내가 넣어줄 값
  writeDate: new Date(),
  progress: data.progress, // 내가 넣어줄 값
  title: data.title, // 내가 넣어줄 값
  thumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNEqDnzERi5Aa-o2Qk7r-GmibgwZWM0wV4utkTJZBRNULFk8KwNzUKtPXvgPE&usqp=CAc', // 내가 넣어줄 값
  images: [], // 내가 넣어줄 값
  contens: data.contens, // 내가 넣어줄 값
  category: data.category, // 내가 넣어줄 값
  tprice: data.tprice, // 내가 넣어줄 값
  division: data.division, // 내가 넣어줄 값
});

export const dummyBookPost: PostListInterface = {
  data: [],
};
export const generateDummyPost = (Postnumber: number) => Array(Postnumber).fill(0).map(() => (
  {
    articleId: faker.random.number(),
    countDAO: {
      chatCount: 2,
      countId: 2,
      views: 2,
      wishCount: 3,
    },
    title: faker.commerce.productName(),
    writeDate: new Date(),
    tprice: 20000,
    progress: 'POSTING',
    category: {
      cid: faker.random.number(), oneDepth: '국내도서', twoDepth: '소설/시/희곡', threeDepth: '', fourDepth: '', fiveDepth: '',
    },
    image: faker.random.image(),
    nickname: faker.name.findName(),
    market: null,
  }
));
