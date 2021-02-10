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
  useEffect(() => {
    dispatch(loadBookPostRequest({ filtering: { division: 'sales', category: '' } }));
  }, [dispatch]);

  const { bookPost } = useSelector((state) => state.postSlice);
  console.log(bookPost);
  return (
    <AppLayout>
      <PostListWrapper>
        <Header />
        <AddPostButton />
        { bookPost.length !== 0 ? <InfiniteScrollList /> : <div>Loading...</div>}
      </PostListWrapper>
    </AppLayout>
  );
}

export default PostList;
