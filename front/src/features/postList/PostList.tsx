import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import AppLayout from '../../components/AppLayout';
import Header from './Header';
import { loadBookPostRequest } from './postSlice';
import InfiniteScrollList from './InfiniteScrollList';
import AddPostButton from './AddPostButton';

const PostListWrapper = styled.div`
`;
function PostList(): JSX.Element {
  const dispatch = useDispatch();
  const { bookPost, filtering } = useSelector((state) => state.postSlice);
  useEffect(() => {
    dispatch(loadBookPostRequest({ filtering: { division: filtering.division, category: filtering.category } }));
  }, [dispatch, filtering.category, filtering.division]);
  console.log(bookPost);
  return (
    <AppLayout activeId={0}>
      <PostListWrapper>
        <Header />
        <AddPostButton />
        { bookPost.length !== 0 ? <InfiniteScrollList /> : <div>Loading...</div>}
      </PostListWrapper>
    </AppLayout>
  );
}

export default PostList;
