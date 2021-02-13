import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { getMySaleArticleRequest } from './mySaleArticleSlice';

const Layout = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 4vw;
  @media screen and (min-width: 455px) {
    font-size: 18.208
  }
`;

const Header = styled.header`
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: center;
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
    font-size: 3.5vw;
    padding: 0.5em;
    border: 1px solid;
  }
  & p.my_category {
    font-size: 3vw;
    margin-bottom: 0.5em;
  }
  & h2.my_title {
    font-size: 4.5vw;
    margin-bottom: 1em;
  }
  & p.my_price {
    font-size: 4.5vw;
  }
  @media screen and (min-width: 455px) {
    & p.my_state {
      font-size: 15.932px;
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

function MySaleArticle():JSX.Element {
  const dispatch = useDispatch();
  const { mySaleArticle } = useSelector((state) => state.mySaleArticleSlice);

  useEffect(() => {
    dispatch(getMySaleArticleRequest({ type: '' }));
  }, [dispatch]);

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
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p className="my_state">판매중</p>
              <p className="my_category">국내도서&gt;소설/시/희곡</p>
              <h2 className="my_title">국내도서입니당.</h2>
              <p className="my_price">20000원</p>
            </ItemInfo>
          </MySaleItem>
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p className="my_category">국내도서&gt;소설/시/희곡</p>
              <h2 className="my_title">국내도서입니당.</h2>
              <p className="my_price">20000원</p>
            </ItemInfo>
          </MySaleItem>
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p className="my_category">국내도서&gt;소설/시/희곡</p>
              <h2 className="my_title">국내도서입니당.</h2>
              <p className="my_price">20000원</p>
            </ItemInfo>
          </MySaleItem>
          <MySaleItem>
            <ItemImage />
            <ItemInfo>
              <p className="my_category">국내도서&gt;소설/시/희곡</p>
              <h2 className="my_title">국내도서입니당.</h2>
              <p className="my_price">20000원</p>
            </ItemInfo>
          </MySaleItem>
        </MySaleItems>
      </Layout>
    </>
  );
}

export default MySaleArticle;
