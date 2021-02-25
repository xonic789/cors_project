import React from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import ImagePreView from './ImagePreView';

interface ImageURLInterface {
  id: string;
  url: string;
  image: Blob;
}
interface EditorForm {
  onSubmit: (e: React.FormEvent<HTMLFormElement>) => void,
  onChangePrice: (e: React.ChangeEvent<HTMLInputElement>) => void,
  onChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>) => void,
  onChangeImage: (e: any) => void,
  onDeleteImage: (imageId: string) => void,
  price: string,
  content: string,
  images: ImageURLInterface[],
  isLoading: boolean,
}
const FormWrapper = styled.form`
`;
const BookDetailInputWrapper = styled.div`
  & input {
    margin: 20px 0;
    width: 100%;
    padding: 10px;
    border: 1px solid #e8e8e8;
  }
  & textArea {
    width: 100%;
    padding: 10px;
    border: 1px solid #e8e8e8;
    height: 80px;
    resize: none;
    overflow: scroll;
  }
`;
const AddPostButton = styled.button`
  width: 100%;
  border: 0;
  background-color: #3960a6;
  padding: 10px;
  margin: 20px 0px;
  border-radius: 5px;
  color: white;
  font-weight: 700;
`;

function Editor({ onSubmit, onChangePrice, onChangeContent, onChangeImage, onDeleteImage, price, content, images, isLoading }: EditorForm): JSX.Element {
  return (
    <FormWrapper encType="multipart/form-data" onSubmit={onSubmit}>
      <ImagePreView onChangeImage={onChangeImage} images={images} onDelete={onDeleteImage} />
      <BookDetailInputWrapper>
        <input type="text" pattern="[0-9]+" placeholder="₩ 가격입력" onChange={onChangePrice} value={price} />
        <textarea onChange={onChangeContent} value={content} placeholder="상품설명을 입력하세요" />
      </BookDetailInputWrapper>
      <AddPostButton type="submit" disabled={isLoading}>
        {isLoading ? <span>등록중입니다</span> : <span>등록하기</span>}
      </AddPostButton>
    </FormWrapper>
  );
}

export default Editor;
