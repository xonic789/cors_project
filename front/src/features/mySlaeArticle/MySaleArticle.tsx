import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { myArticleInterface } from '../../interfaces/MyArticle.interface';
import CategoryFormatUtil from '../../utils/categoryFormatUtil';
import numberArrayUtill from '../../utils/numberArrayUtill';
import { getMySaleArticleRequest } from './mySaleArticleSlice';

const Layout = styled.form`
  display: flex;
  height: 100vh;
  flex-direction: column;
  align-items: center;
  font-size: 4vw;
  @media screen and (min-width: 455px) {
    font-size: 18.208px;
  }
`;

const Header = styled.header`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
  border-bottom: 1px solid #e5e5e5;
  & h1 {
      width: 100%;
      font-size: 4.5vw;
      padding: 1.5em;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h1 {
      font-size: 20.484px;
    }
  }
`;

const BackLink = styled(Link)`
  position: absolute;
  left: 1em;
`;

const BackImg = styled.img`
  width: 2em;
  height: 2em;
  @media screen and (min-width: 455px) {
    font-size: 18.208px;
  }
`;

const MySaleItems = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const MySaleItem = styled.div`
  
`;

const ItemLink = styled(Link)`
  display: flex;
  align-items: center;
  padding: 1em;
`;

const ItemImgBox = styled.div`
  display: flex;
  flex-shrink: 0;
  align-items: center;
  justify-content: center;
  width: 9em;
  height: 10em;
  margin-right: 1.5em;
  border: 1px solid #eeeeee;
  border-radius: 10px;
  overflow: hidden;
  @media screen and (min-width: 455px) {
    font-size: 18.208px;
  }
`;

const ItemImage = styled.img`
  width: 10em;
  height: auto;
`;

const ItemInfo = styled.div`
  & p.my_state {
    display: inline-block;
    font-size: 2.5vw;
    font-weight: bold;
    padding: 0.5em 1em;
    border: 1px solid;
    border-radius: 10px;
    color: #fff;
    margin-bottom: 0.5em;
  }
  & p.my_category {
    font-size: 2.5vw;
    margin-bottom: 0.5em;
  }
  & h2.my_title {
    font-size: 4vw;
    margin-bottom: 0.8em;
  }
  & p.my_price {
    font-size: 3.5vw;
  }
  @media screen and (min-width: 455px) {
    & p.my_state {
      font-size: 11.38px;
    }
    & p.my_category {
      font-size: 11.38px;
    }
    & h2.my_title {
      font-size: 18.208px;
    }
    & p.my_price {
      font-size: 15.932px;
    }
  }
`;

const Pagenation = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  font-size: 4vw;
  padding: 1.5em 0;
  @media screen and (min-width: 455px) {
    font-size: 18.208px;
    width: 455px;
  }
`;

const PrevLink = styled(Link)`
`;

const Prev = styled.img`
  width: 1em;
  height: 1em;
`;

const PageItems = styled.ul`
  display: flex;
`;

const PageItem = styled.li`
  margin: 0 1em;
`;

const NextLink = styled(Link)`
`;

const Next = styled.img`
  width: 1em;
  height: 1em;
  transform: rotate(180deg);
`;

const EmptyArticle = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  align-items: center;
  justify-content: center;
  & h2 {
    font-size: 4.5vw;
    font-weight: bold;
    margin-bottom: 0.3em;
  }
  & p {
    font-size: 4vw;
    margin-bottom: 1em;
  }
  & button{
    font-size: 4vw;
    padding: 0.5em 1em;
    background: #265290;
    border: none;
    border-radius: 10px;
    outline: none;
    color: #fff;
    font-weight: bold;
  }
  @media screen and (min-width: 455px) {
    & h2 {
      font-size: 20.484px;
    }
    & p {
      font-size: 18.208px;
    }
    & button {
      font-size: 18.208px;
    }
  }
`;

function MySaleArticle():JSX.Element | null {
  const dispatch = useDispatch();
  const history = useHistory();
  const [page, setPage] = useState<number>(0);
  const { mySaleArticle, totalPage, isGetMyPurchaseArticlesLoading } = useSelector((state) => state.mySaleArticleSlice);
  const progressForm = (progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING'): { text: string, background: string } => {
    const resultProgress = {
      COMPLETED: { text: '거래완료', background: '#1e1e1e' },
      HIDE: { text: '숨김', background: '#7e7e7e' },
      TRADING: { text: '예약중', background: '#62ff7d' },
      POSTING: { text: '거래중', background: '#265290' },
    };

    return resultProgress[progress];
  };

  const onClickPrevPage = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    const prevPage = page - 1;

    if (prevPage < 0) {
      e.preventDefault();
    } else {
      setPage(0);
    }
  };
  const onClickNextPage = (e: React.MouseEvent<HTMLAnchorElement, MouseEvent>) => {
    const nextPage = page + 1;

    if (nextPage > totalPage - 1) {
      e.preventDefault();
    } else {
      setPage(totalPage - 1);
    }
  };

  useEffect(() => {
    dispatch(getMySaleArticleRequest(page));
  }, [dispatch, page]);

  return (
    <>
      <Layout>
        <Header>
          <BackLink to="/mypage">
            <BackImg src="/images/icons/back.png" />
          </BackLink>
          <h1>판매목록</h1>
        </Header>
        {
          (totalPage === 0
            ? (
              <>
                <EmptyArticle>
                  <h2>등록한 판매글이 없습니다.</h2>
                  <p>다 보신 책들을 나누어 보아요.</p>
                  <button type="button" onClick={() => history.push('/addPost/purchase')}>책 판매하러 가기</button>
                </EmptyArticle>
              </>
            )
            : (
              <>
                <MySaleItems>
                  {
                    mySaleArticle.map((item: myArticleInterface) => (
                      <MySaleItem key={item.articleId}>
                        <ItemLink to={`/post/${item.articleId}`}>
                          <ItemImgBox>
                            <ItemImage src={item.thumbnail} />
                          </ItemImgBox>
                          <ItemInfo>
                            <p style={{ background: progressForm(item.progress).background }} className="my_state">{progressForm(item.progress).text}</p>
                            <p className="my_category">{CategoryFormatUtil(item.category)}</p>
                            <h2 className="my_title">{item.title}</h2>
                            <p className="my_price">{item.tprice} 원</p>
                          </ItemInfo>
                        </ItemLink>
                      </MySaleItem>
                    ))
                  }
                </MySaleItems>
                <Pagenation>
                  <PrevLink onClick={onClickPrevPage} to={`/mypage/sales?page=${page - 1}`}>
                    <Prev src="/images/icons/back.png" />
                  </PrevLink>
                  <PageItems>
                    {
                      numberArrayUtill(totalPage).map((i) => (
                        <PageItem><Link onClick={() => setPage(i - 1)} to={`/mypage/sales?page=${i - 1}`}>{i}</Link></PageItem>
                      ))
                    }
                  </PageItems>
                  <NextLink onClick={onClickNextPage} to={`/mypage/sales?page=${page + 1}`}>
                    <Next src="/images/icons/back.png" />
                  </NextLink>
                </Pagenation>
              </>
            ))
        }
      </Layout>
    </>
  );
}

export default MySaleArticle;
