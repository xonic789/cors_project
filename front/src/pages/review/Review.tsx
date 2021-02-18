import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { reviewInterface } from '../../interfaces/ReviewInterface';
import dummy from './mockdata';

const Positional = styled.div`
  height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  font-size: 4.5vw;
  @media screen and (min-width: 455px) {
    font-size: 20.484px;
  }
`;

const Header = styled.header`
  position: relative;
  display: flex;
  width: 100%;
  border-bottom: 1px solid #e5e5e5;
  & button {
    font-size: 4.5vw;
    font-weight: bold;
    color: #ababab;
    width: 50%;
    padding: 1em 0;
    background: none;
    border: none;
    outline: none;
  }
  & button.active {
    color: #265290;
    border-bottom: 3px solid #265290;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & button {
        font-size: 20.484px;
    }
  }
`;

const ReviewList = styled.div`
  width: 100%;
  height: 90%;
  overflow: auto;
  display: flex;
  flex-direction: column;
  padding: 1em;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const ReviewItem = styled.div`
  display: flex;
  margin-bottom: 1.5em;
  & img {
    width: 3.5em;
    height: 3.5em;
    border: 0.3em solid #acacac;
    border-radius: 50%;
    flex-shrink: 0;
    margin-right: 1em;
  }
`;

const ReviewTextBox = styled.div`
  width: 100%;
  padding: 1em;
  background: #cfe3ff;
  box-shadow: 5px 5px 10px #c5c5c5;
  border-radius: 10px;
  & h2 {
    font-size: 0.8em;
    margin-bottom: 0.3em;
  }
  & h3 {
    font-size: 0.6em;
    margin-bottom: 1em;
  }
  & p {
    font-size: 0.7em;
    line-height: 1.3em;
    word-break: keep-all;
  }
`;

const CloseButton = styled(Link)`
  position: absolute;
  right: 0.3em;
  top: 0.3em;
  & img {
      width: 0.7em;
      height: 0.7em;
  }
`;

function Review():JSX.Element {
  const [reviewList, setReiviewList] = useState<reviewInterface[]>([]);
  const [category, setCategory] = useState<string>('receive');
  useEffect(() => {
    setReiviewList(dummy);
  }, []);

  return (
    <Positional>
      <Header>
        <button onClick={() => setCategory('receive')} className={category === 'receive' ? 'active' : ''} type="button">받은 한줄평</button>
        <button onClick={() => setCategory('send')} className={category === 'send' ? 'active' : ''} type="button">보낸 한줄평</button>
        <CloseButton to="/mypage">
          <img src="/images/icons/x.png" alt="" />
        </CloseButton>
      </Header>
      <ReviewList>
        {
          reviewList.map((review) => (
            <ReviewItem key={review.reviewId}>
              <img src={review.profileImage} alt="" />
              <ReviewTextBox>
                <h2>{category === 'receive' ? 'To.' : 'From.'} {review.ninkname}</h2>
                <h3>{review.writeDate}</h3>
                <p>
                  {review.contents}
                </p>
              </ReviewTextBox>
            </ReviewItem>
          ))
        }
      </ReviewList>
    </Positional>
  );
}

export default Review;
