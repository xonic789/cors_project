import React, { useCallback, useRef, useState } from 'react';
import styled from 'styled-components';
import { v4 as uuidv4 } from 'uuid';

interface ImageViewInterface {
  id: string;
  url: string;
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
    font-weight: 600;
  }
`;

const ImagePreView = () => {
  const [imageFile, setImageFile] = useState<ImageViewInterface[]>([]);
  const ImageRef = useRef<HTMLInputElement>(null);
  const onDeleteImage = (imageId: string) => {
    const filteringImageFile = imageFile.filter((image) => image.id !== imageId);
    setImageFile([...filteringImageFile]);
  };
  const onClickImageUpload = useCallback(() => {
    ImageRef.current?.click();
  }, []);
  const ImageFileReaderPromise = (file: Blob) => new Promise<string>((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = () => {
      if (reader.result !== null) {
        resolve(reader.result as string);
      }
    };
    reader.onerror = reject;
    reader.readAsDataURL(file);
  });
  const ImageFileReader = async (file: Blob) => {
    try {
      const image: string = await ImageFileReaderPromise(file);
      if (imageFile.length >= 2) {
        alert('사진은 최대 두장만 선택하실 수 있습니다.');
      } else {
        setImageFile(imageFile.concat({ id: uuidv4(), url: image }));
      }
    } catch (error) {
      console.error(error);
    }
  };
  const onHandleUpload = (e: any) => {
    e.preventDefault();
    console.log(e.target.files);
    const imageFormData = new FormData();
    const images = e.target.files;
    ImageFileReader(e.target.files[0]);
    [].forEach.call(images, (f) => {
      imageFormData.append('file', f);
    });
  };

  return (
    <ImageInputWrapper>
      <ImageInput type="file" accept="image/jpg,impge/png,image/jpeg" name="file" id="image" onChange={onHandleUpload} ref={ImageRef} multiple />
      <button type="button" onClick={onClickImageUpload}>
        <img src="/images/icons/camera.png" alt="camera" />
      </button>
      {imageFile.map((v) => (
        <ImagePreviewWrapper key={v.id}>
          <button type="button" onClick={() => onDeleteImage(v.id)}>X</button>
          <img src={v.url} alt="img" />
        </ImagePreviewWrapper>
      ))}
    </ImageInputWrapper>
  );
};

export default ImagePreView;
