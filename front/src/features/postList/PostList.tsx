import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';
import { articleInterface } from '../../interfaces/PostList.interface';
import Header from './Header';
import { loadBookPostRequest, loadScrollBookPostRequest } from './postSlice';

const PostListWrapper = styled.div`
  display: flex;
  max-width: 100%;
  flex-wrap: wrap;
  padding-top: 200px;
  margin-bottom: 50px;
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
    overflow:hidden;
    margin-top: 10px;
  }
`;
function PostList(): JSX.Element {
  const { bookPost, filtering, hasMorePost, isLoadScrollBookPostLoading } = useSelector((state) => state.postSlice);
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(loadBookPostRequest({ division: 'sales', category: '' }));
  }, [dispatch]);
  useEffect(() => {
    function onScroll() {
      if (window.scrollY + document.documentElement.clientHeight >= document.documentElement.scrollHeight - 300) {
        if (hasMorePost && !isLoadScrollBookPostLoading) {
          dispatch(loadScrollBookPostRequest({ division: filtering.division, category: filtering.category }));
        }
      }
    }
    window.addEventListener('scroll', onScroll);
    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, [dispatch, filtering.category, filtering.division, hasMorePost, isLoadScrollBookPostLoading]);
  return (
    <AppLayout>
      <PostListWrapper>
        <Header />
        {
        bookPost.map((p:articleInterface) => (
          <PostListContent key={p.articleId}>
            <img src={p.image} alt="" />
            <h3>{p.title}</h3>
            <h3>{p.tprice}원</h3>
          </PostListContent>
        ))
      }
      </PostListWrapper>
    </AppLayout>
  );
}

export default PostList;
