import React from 'react';
import { List, AutoSizer } from 'react-virtualized';
import { useSelector, useDispatch } from 'react-redux';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
import CategoryFormatUtil from '../../utils/categoryFormatUtil';
import { loadScrollBookPostRequest } from './postSlice';

interface IndexInterface {
  index: number;
  style?: any;
}
interface OnScrollParams {
  clientHeight: number;
  clientWidth: number;
  scrollHeight: number;
  scrollLeft: number;
  scrollTop: number;
  scrollWidth: number;
}
const ListWrapper = styled.div`
  display: flex;
  max-width: 100%;
  flex-wrap: wrap;
  margin-top: 150px;
  overflow: hidden;
`;
const Content = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 200px;
  flex-basis: 50%;
  padding:10px;
  border-top: 1px solid #e8e8e8;
  & img {
    width: 150px;
    height: 150px;
    border-radius: 10px;
  }
`;
const ContentExplanation = styled.div`
  padding: 0px 20px;
  & h3 {
    overflow:hidden;
    padding: 10px 0px;
  }
`;
const Categoty = styled.div`
  font-size: 9px;
`;
function InfiniteScrollList(): JSX.Element {
  const dispatch = useDispatch();
  const { bookPost, hasMorePost, isLoadScrollBookPostLoading } = useSelector((state) => state.postSlice);
  const scrollListener = (params:OnScrollParams) => {
    if (params.scrollTop + params.clientHeight >= params.scrollHeight - 300) {
      if (hasMorePost && !isLoadScrollBookPostLoading) {
        const LastId = bookPost[bookPost.length - 1].articleId;
        dispatch(loadScrollBookPostRequest({ lastId: LastId }));
      }
    }
  };

  const rowRanderer = ({ index, style }: IndexInterface) => {
    const post = bookPost[index];
    return (
      <div style={style}>
        <NavLink to={`post/${post.articleId}`}>
          <Content key={post.articleId}>
            <img src={post.image} alt="" />
            <ContentExplanation>
              <Categoty>{CategoryFormatUtil(post.category)}</Categoty>
              <h3>{post.title}</h3>
              <h3>{post.tprice}원</h3>
            </ContentExplanation>
          </Content>
        </NavLink>
      </div>
    );
  };

  return (
    <ListWrapper>
      <AutoSizer disableHeight>
        {({ width }) => (
          <List
            rowCount={bookPost.length} // 항목의 개수
            height={800} // 실제 렌더링 되는 높이범위
            rowHeight={200} // 항목의높이
            width={width} // 항목의 너비
            rowRenderer={rowRanderer} // 항목렌더링할때쓰는 함수
            onScroll={scrollListener} // scroll 함수
            overscanRowCount={2} // 다음에 로드해올 항목 미리 컨텐츠 높이 잡기
          />
        )}
      </AutoSizer>
    </ListWrapper>
  );
}

export default InfiniteScrollList;
