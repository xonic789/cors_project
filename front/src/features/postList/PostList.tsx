import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';
import Header from './Header';
import { loadBookPostRequest } from './postSlice';
import InfiniteScrollList from './InfiniteScrollList';

const PostListWrapper = styled.div`
`;
function PostList(): JSX.Element {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(loadBookPostRequest({ division: 'sales', category: '' }));
  }, [dispatch]);
  return (
    <AppLayout>
      <PostListWrapper>
        <Header />
        <InfiniteScrollList />
      </PostListWrapper>
    </AppLayout>
  );
}

export default PostList;
