import React from 'react';
import styled from 'styled-components';
import { NavLink, RouteComponentProps } from 'react-router-dom';
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
  const goback = () => {
    history.goBack();
  };
  return (
    <DetailPostViewContainer>
      <NavLink to="home" onClick={goback}><Header><img src="/images/icons/back.png" alt="back_icon" /></Header></NavLink>
      <DetailPostContent />
    </DetailPostViewContainer>
  );
}

export default DetailPostView;
