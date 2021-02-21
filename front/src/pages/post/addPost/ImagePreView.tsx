import React, { useCallback, useRef, useState } from 'react';
import styled from 'styled-components';

interface ImageURLInterface {
  id: string;
  url: string;
  image: Blob;
}

interface ImagePreViewInterface {
  onChangeImage: (e: any) => void,
  images: ImageURLInterface[],
  onDelete: (imageId: string) => void,
}

const ImageInputWrapper = styled.div`
  display: flex;
  align-items: center;
  height: 130px;
  border: 1px solid #e8e8e8;
  & button {
    margin-left: 10px;
    background-color: #f2f0f0;
    border: 0;
  }
  & img {
    width: 40px;
    height: 40px;
    margin: 10px;
  }
`;
const ImageInput = styled.input`
  display: none;
`;
const ImagePreviewWrapper = styled.div`
  display: flex;
  flex-direction: column;
  & img {
    width: 60px;
    height: 60px;
  }
  & button {
    width: 60px;
    background-color: white;
    border: 0;
  }
`;

function ImagePreView({ onChangeImage, images, onDelete }: ImagePreViewInterface):JSX.Element {
  const ImageRef = useRef<HTMLInputElement>(null);
  const onClickImageUpload = useCallback(() => {
    ImageRef.current?.click();
  }, []);

  return (
    <ImageInputWrapper>
      <ImageInput type="file" accept="image/jpg,image/png,image/jpeg" name="file" id="image" onChange={onChangeImage} ref={ImageRef} />
      <button type="button" onClick={onClickImageUpload}>
        <img src="/images/icons/camera.png" alt="camera" />
      </button>
      {images.map((v) => (
        <ImagePreviewWrapper key={v.id}>
          <button type="button" onClick={() => onDelete(v.id)}>X</button>
          <img src={v.url} alt="img" />
        </ImagePreviewWrapper>
      ))}
    </ImageInputWrapper>
  );
}

export default ImagePreView;
