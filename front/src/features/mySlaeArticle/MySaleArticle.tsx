import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { myArticleInterface } from '../../interfaces/MyArticle.interface';
import CategoryFormatUtil from '../../utils/categoryFormatUtil';
import numberArrayUtill from '../../utils/numberArrayUtill';
import { getMySaleArticleRequest } from './mySaleArticleSlice';

const Layout = styled.form`
  display: flex;
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
  display: flex;
  align-items: center;
  padding: 1em;
`;

const ItemImage = styled.img`
  width: 10em;
  height: 10em;
  margin-right: 1em;
  border-radius: 10px;
  @media screen and (min-width: 455px) {
    font-size: 18.208px;
  }
`;

const ItemInfo = styled.div`
  & p.my_state {
    display: inline-block;
    font-size: 3vw;
    font-weight: bold;
    padding: 0.5em;
    border: 1px solid;
    border-radius: 10px;
    color: #fff;
    margin-bottom: 0.5em;
  }
  & p.my_category {
    font-size: 3vw;
    margin-bottom: 0.5em;
  }
  & h2.my_title {
    font-size: 4.5vw;
    margin-bottom: 0.8em;
  }
  & p.my_price {
    font-size: 4.5vw;
  }
  @media screen and (min-width: 455px) {
    & p.my_state {
      font-size: 13.656px;
    }
    & p.my_category {
      font-size: 13.656px;
    }
    & h2.my_title {
      font-size: 20.484px;
    }
    & p.my_price {
      font-size: 20.484px;
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

function MySaleArticle():JSX.Element {
  const dispatch = useDispatch();
  const [page, setPage] = useState<number>(0);
  const { mySaleArticle, totalPage } = useSelector((state) => state.mySaleArticleSlice);
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
        <MySaleItems>
          {
            mySaleArticle.map((item: myArticleInterface) => (
              <MySaleItem key={item.articleId}>
                <ItemImage src={item.thumbnail} />
                <ItemInfo>
                  <p style={{ background: progressForm(item.progress).background }} className="my_state">{progressForm(item.progress).text}</p>
                  <p className="my_category">{CategoryFormatUtil(item.category)}</p>
                  <h2 className="my_title">{item.title}</h2>
                  <p className="my_price">{item.tprice}</p>
                </ItemInfo>
              </MySaleItem>
            ))
          }
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p style={{ background: progressForm('COMPLETED').background }} className="my_state">{progressForm('COMPLETED').text}</p>
              <p className="my_category">테스트중</p>
              <h2 className="my_title">테스트중</h2>
              <p className="my_price">200000</p>
            </ItemInfo>
          </MySaleItem>
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p style={{ background: progressForm('HIDE').background }} className="my_state">{progressForm('HIDE').text}</p>
              <p className="my_category">테스트중</p>
              <h2 className="my_title">테스트중</h2>
              <p className="my_price">200000</p>
            </ItemInfo>
          </MySaleItem>
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p style={{ background: progressForm('TRADING').background }} className="my_state">{progressForm('TRADING').text}</p>
              <p className="my_category">테스트중</p>
              <h2 className="my_title">테스트중</h2>
              <p className="my_price">200000</p>
            </ItemInfo>
          </MySaleItem>
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
      </Layout>
    </>
  );
}

export default MySaleArticle;
