import React from 'react';
import styled from 'styled-components';
import Slider from 'react-slick';

interface ImagesZoomProps {
  images: string[],
  onClickCloseImageZoom: () => void,
}
const Overlay = styled.div`
  position : fixed;
  z-index : 5000;
  top : 0;
  left : 0;
  right : 0;
  bottom: 0;
  background : #313131;
`;
const ImagesZoomContainer = styled.div`
  margin-top: 50px;
`;
const ImagesZoomHeader = styled.div`
  background-color: white;
  display: flex;
  justify-content: flex-end;
  align-items: center;
`;
const CloseButton = styled.button`
  border: 0;
  background: transparent;
  font-size: 30px;
  padding: 10px;
`;
const StyledSlider = styled(Slider)`
  & .slick-dots li button:before {
    color: white;
  }
  & .slick-dots {
    position: fixed;
    bottom: 0;
  }
`;
const ImagesContainer = styled.div`
  & img{
    width:80%;
    max-width: 400px;
    margin: 0 auto;
  }
`;

function ImagesZoom({ images, onClickCloseImageZoom }: ImagesZoomProps): JSX.Element {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
  };
  return (
    <Overlay>
      <ImagesZoomHeader>
        <CloseButton onClick={onClickCloseImageZoom}>ｘ</CloseButton>
      </ImagesZoomHeader>
      <ImagesZoomContainer>
        <StyledSlider {...settings}>
          {images.map((imageUrl) => <ImagesContainer><img src={imageUrl} alt="slideImage" /></ImagesContainer>)}
        </StyledSlider>
      </ImagesZoomContainer>
    </Overlay>
  );
}

export default ImagesZoom;
