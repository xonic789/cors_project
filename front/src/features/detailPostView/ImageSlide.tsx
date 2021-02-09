import React, { useState } from 'react';
import styled from 'styled-components';
import Slider from 'react-slick';
import ImagesZoom from './ImagesZoom';

interface ImageZoomPropsInterface {
  images: string[],
}
const DetailPostViewWrapper = styled.div`
`;
const StyledSlider = styled(Slider)`
  & .slick-slide div{
    outline: none;
  }
  & .slick-dots {
    bottom: 0;
  }
`;
const SliderImageContainer = styled.div`
  width: 100vw;
  height: 50vh;
`;
const Image = styled.img`
  width: 100vw;
  max-width:100%;
  height: 50vh;
`;
function ImageSlide({ images }: ImageZoomPropsInterface): JSX.Element {
  const [imageZoomOpen, setImageZoomOpen] = useState(false);
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    arrows: false,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  const onHandleOpenImageZoom = () => {
    setImageZoomOpen(true);
  };
  const onHandleCloseImageZoom = () => {
    setImageZoomOpen(false);
  };
  return (
    <DetailPostViewWrapper>
      <StyledSlider {...settings}>
        {images.map((v) => (
          <SliderImageContainer key={v} onClick={onHandleOpenImageZoom}>
            <Image src={v} alt="BookImage" />
          </SliderImageContainer>
        ))}
      </StyledSlider>
      {imageZoomOpen && <ImagesZoom images={images} onClickCloseImageZoom={onHandleCloseImageZoom} />}
    </DetailPostViewWrapper>
  );
}

export default ImageSlide;
