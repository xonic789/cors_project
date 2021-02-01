import shortId from 'shortid';

export interface articleInterface {
  postId: string, // 게시글 기본키
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

export interface AddBookPostInterfce {
  memberId: number, // 작성자
  rprice: number, // 판매가격
  contens: string, // 내용
  progress: 'completed' | 'hide' | 'trading' | 'posting', // 게시글 상태
  title: string, // 책제목
  Images: { // 이미지들
    sumnail: string,
    image: string[]
  },
  category: CategoryInterface, // 카테고리 키
  tprice: number, // 원래가격
  division: 'purchase' | 'sale', // 구매 판매 구분
}
const PostList:PostListInterface = {
  data: [
    {
      postId: 'aaaa',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈 사냥개',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNEqDnzERi5Aa-o2Qk7r-GmibgwZWM0wV4utkTJZBRNULFk8KwNzUKtPXvgPE&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
    {
      postId: 'bbb',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '거북이와 두루미',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTadvwJ6CEZPiQKoZBst-6GNsGY_ukrkQH8J0F3UMuyh2P_1RVmyqUYMs4ZN4&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
    {
      postId: 'cccc',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈강아지',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTadvwJ6CEZPiQKoZBst-6GNsGY_ukrkQH8J0F3UMuyh2P_1RVmyqUYMs4ZN4&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
    {
      postId: 'dddd',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈고양이',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNEqDnzERi5Aa-o2Qk7r-GmibgwZWM0wV4utkTJZBRNULFk8KwNzUKtPXvgPE&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
    {
      postId: 'eeee',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈참새',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWijTAC5YYVzySJJQxc7vlox2z7jYgZg8gJOL7Jf5apLweM7gktnPcpH3LuMJMHgOPXcmjyJU&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
    {
      postId: 'ffff',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈고양이',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdovk9Bbp7EFyzq4b9wg1uJ53x8O9LbZP-Sequ_MYAih_ic5Y-ypQK0kw19mJJl_IIO7Ost6vP&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'sale',
    },
  ],
};

export const PurchasePostList:PostListInterface = {
  data: [
    {
      postId: 'aaaa',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈 코스마켓',
      Images: {
        sumnail: 'https://search.pstatic.net/sunny/?src=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F83%2F5a%2F76%2F835a7667d230bc3dda4e661da65bde90.jpg&type=a340',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
    {
      postId: 'bbb',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '거북이와 두루미',
      Images: {
        sumnail: 'https://search.pstatic.net/sunny/?src=https%3A%2F%2Fi.pinimg.com%2F736x%2F8d%2F21%2F99%2F8d2199527e96c2fd5d7e5263e31cb47c--books-a-million-william-morris.jpg&type=a340',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
    {
      postId: 'cccc',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈강아지',
      Images: {
        sumnail: 'https://search.pstatic.net/sunny/?src=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F65%2F07%2F4a%2F65074ade97541d64b9971ffe38be9409.png&type=a340',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
    {
      postId: 'dddd',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈고양이',
      Images: {
        sumnail: 'https://search.pstatic.net/sunny/?src=https%3A%2F%2Fi.pinimg.com%2F736x%2F8d%2F21%2F99%2F8d2199527e96c2fd5d7e5263e31cb47c--books-a-million-william-morris.jpg&type=a340',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
    {
      postId: 'eeee',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈참새',
      Images: {
        sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWijTAC5YYVzySJJQxc7vlox2z7jYgZg8gJOL7Jf5apLweM7gktnPcpH3LuMJMHgOPXcmjyJU&usqp=CAc',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
    {
      postId: 'ffff',
      memberId: 123,
      rprice: 20000,
      writeDate: new Date('2019-01-16'),
      progress: 'posting',
      title: '배고픈고양이',
      Images: {
        sumnail: 'https://search.pstatic.net/sunny/?src=https%3A%2F%2Fi.pinimg.com%2F736x%2F8d%2F21%2F99%2F8d2199527e96c2fd5d7e5263e31cb47c--books-a-million-william-morris.jpg&type=a340',
        image: [],
      },
      contens: '이것은 책입니다. 아주재밌는 책입니다.',
      category: {
        cid: 1,
        oneDepth: '국내도서',
        twoDepth: '소설/시/희극',
        threeDepth: 'aaa',
      },
      tprice: 30000,
      division: 'purchase',
    },
  ],
};

export const dummyBookPost = (data: AddBookPostInterfce) => ({
  postId: shortId.generate(),
  memberId: data.memberId, // 내가 넣어줄 값
  rprice: data.rprice, // 내가 넣어줄 값
  writeDate: new Date(),
  progress: data.progress, // 내가 넣어줄 값
  title: data.title, // 내가 넣어줄 값
  Images: {
    sumnail: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNEqDnzERi5Aa-o2Qk7r-GmibgwZWM0wV4utkTJZBRNULFk8KwNzUKtPXvgPE&usqp=CAc',
    image: [],
  }, // 내가 넣어줄 값
  contens: data.contens, // 내가 넣어줄 값
  category: data.category,
  tprice: data.tprice, // 내가 넣어줄 값
  division: data.division, // 내가 넣어줄 값
});

export default PostList;
