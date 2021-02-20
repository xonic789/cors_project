import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { ToastsContainer, ToastsStore, ToastsContainerPosition } from 'react-toasts';
import AppLayout from '../../../components/AppLayout';
import Header from './Header';
import { loadBookPostRequest } from '../postSlice';
import InfiniteScrollList from './InfiniteScrollList';
import AddPostButton from '../addPost/AddPostButton';
import Loading from '../../../components/Loading';

const PostListWrapper = styled.div`
  margin-top: 150px;
  width: 100%;
  height: auto;
`;
const Category = styled.div`
  padding: 10px;
  color: #3960a6;
`;
function PostList(): JSX.Element {
  const dispatch = useDispatch();
  const { bookPost, filtering, isLoadBookPostLoading } = useSelector((state: any) => state.postSlice);
  useEffect(() => {
    dispatch(loadBookPostRequest({ filtering: { division: filtering.division, category: filtering.category } }));
  }, [dispatch, filtering.category, filtering.division]);
  console.log(bookPost);

  return (
    <AppLayout activeId={0}>
      <div>
        <Header />
        <PostListWrapper>
          <AddPostButton />
          <Category>카테고리: {filtering.category}</Category>
          {
            isLoadBookPostLoading
              ? <Loading />
              : <InfiniteScrollList />
          }
          <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} />
        </PostListWrapper>
      </div>
    </AppLayout>
  );
}

export default PostList;
