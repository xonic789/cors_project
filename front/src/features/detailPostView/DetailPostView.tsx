import React from 'react';
import styled from 'styled-components';
import Slider from 'react-slick';

const DetailPostViewWrapper = styled.div``;
const DetailPostView:React.FC = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  return (
    <DetailPostViewWrapper>
      <Slider {...settings} />
    </DetailPostViewWrapper>
  );
};

export default DetailPostView;
