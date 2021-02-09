import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { NavLink, RouteComponentProps, useParams } from 'react-router-dom';
import styled from 'styled-components';
import { loadDetailBookPostRequest } from './detailViewSlice';
import DetailPostContent from './DetailPostContent';

const DetailPostViewContainer = styled.div`
  max-width: 600px;
  margin: 0 auto;
  margin-bottom: 100px;
  position: relative;
`;
const Header = styled.div`
  position: absolute;
  padding: 10px;
  & img{
    width: 30px;
  }
  z-index: 5000;
`;

function DetailPostView({ history }: RouteComponentProps):JSX.Element {
  const dispatch = useDispatch();
  const { detailBookPost } = useSelector((state) => state.detailViewSlice);
  const id = useParams();

  const goback = () => {
    history.goBack();
  };

  useEffect(() => {
    dispatch(loadDetailBookPostRequest(id));
  }, [dispatch, id]);

  return (
    <DetailPostViewContainer>
      <NavLink to="home" onClick={goback}>
        <Header>
          <img src="/images/icons/back.png" alt="back_icon" />
        </Header>
      </NavLink>
      {detailBookPost !== null ? <DetailPostContent id={detailBookPost.articleId} /> : <div>Loading...</div>}
    </DetailPostViewContainer>
  );
}

export default DetailPostView;