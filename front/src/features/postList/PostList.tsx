import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import styled from 'styled-components';
import { articleInterface } from '../../interfaces/PostList.interface';
import Header from './Header';
import { loadBookPostRequest } from './postSlice';

const PostListWrapper = styled.div`
  display: flex;
  max-width: 100vw;
  flex-wrap: wrap;
  padding-top: 200px;
`;
const PostListContent = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 50%;
  flex-basis: 50%;
  padding:10px;
  & img {
    width: 100%;
    height: 200px;
  }
  & h3 {
    margin-top: 10px;
  }
`;
const PostList: React.FC = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(loadBookPostRequest('sale'));
  }, [dispatch]);
  const { bookPost } = useSelector((state) => state.postSlice);
  console.log(bookPost);
  return (
    <PostListWrapper>
      <Header />
      {
        bookPost.map((p:articleInterface) => (
          <PostListContent key={p.postId}>
            <img src={p.Images.sumnail} alt="" />
            <h3>{p.title}</h3>
            <h3>{p.rprice}원</h3>
          </PostListContent>
        ))
      }
    </PostListWrapper>
  );
};

export default PostList;
