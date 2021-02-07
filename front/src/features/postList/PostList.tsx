import React, { useEffect } from 'react';
import { List } from 'react-virtualized';
import { useSelector, useDispatch } from 'react-redux';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';
import { articleInterface } from '../../interfaces/PostList.interface';
import Header from './Header';
import { loadBookPostRequest, loadScrollBookPostRequest } from './postSlice';
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
