import React, { useCallback, useEffect, useState } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';
import { ToastsContainer, ToastsStore, ToastsContainerPosition } from 'react-toasts';
import { modifyBookPostRequest } from '../postSlice';
import Editor from '../../../components/write/Editor';
import CategoryFormatUtil from '../../../utils/categoryFormatUtil';
import ImageFileReaderPromise from '../../../utils/imageFileReader';

interface ParamTypes {
  id: string
}
interface ImageURLInterface {
  id: string;
  url: string;
  image: Blob;
}
const AddPostWrapper = styled.div`
  width: 90%;
  margin: 0 auto;
  max-width: 600px;
`;
const AddPostHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 0px;
  & button {
    background-color: white;
    border: 0;
  }
`;
const Logo = styled.img`
  width: 40px;
`;
const BookWrapper = styled.div`
  display: flex;
  padding: 20px;
  & img {
    width: 30%;
    max-width: 200px;
  }
  border: 1px solid #e8e8e8;
  margin: 10px 0px;
`;
const DeleteImg = styled.img`
  width: 20px;
  align-self: flex-start;
`;
const BookDetail = styled.div`
  margin-left: 20px;
`;
const Category = styled.div`
  font-size: 12px;
  margin-bottom: 20px;
`;
const BookTitle = styled.div`
  font-size: 15px;
  margin-bottom: 20px;
  font-weight: 800;
`;
const BookPrice = styled.div`
  font-size: 15px;
  margin-bottom: 20px;
`;
function ModifyPost():JSX.Element {
  const { detailBookPost } = useSelector((state: any) => state.detailViewSlice);

  const [images, setImages] = useState<ImageURLInterface[]>([]);
  const [price, setPrice] = useState<string>('');
  const [content, setContent] = useState<string>('');
  // const { isModifyBookPostLoading } = useSelector((state: any) => state.posts);
  const dispatch = useDispatch();
  const history = useHistory();

  const ImageFileReader = async (file: Blob) => {
    try {
      const image: string = await ImageFileReaderPromise(file);
      if (images.length < 2) {
        setImages(images.concat({ id: uuidv4(), url: image, image: file }));
      } else {
        ToastsStore.error('최대 두장의 사진만 업로드 가능합니다.');
      }
    } catch (error) {
      console.error(error);
    }
  };
  const handleXButtonClick = () => {
    history.push('/home');
  };
  const handleDeleteImage = useCallback((imageId: string) => {
    const filteringImageFile = images.filter((image) => image.id !== imageId);
    setImages([...filteringImageFile]);
  }, [images]);
  const handleChangePrice = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setPrice(e.target.value);
  }, []);
  const handleChangeContent = useCallback((e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
  }, []);
  const handleChangeImages = (e: any) => {
    ImageFileReader(e.target.files[0]);
  };
  const handleSubmitPost = useCallback((e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData();
    if (images.length < 2) {
      ToastsStore.error('최소 두장의 사진이 업로드 되어야합니다');
      return;
    }
    for (let i = 0; i < images.length; i++) {
      formData.append('file', images[i].image); // 사용자가 등록한 이미지
    }
    formData.append('category', CategoryFormatUtil(detailBookPost.category)); // category
    formData.append('cid', String(detailBookPost.category.cid)); // 알라딘에서 받은 정보
    formData.append('title', detailBookPost.title); // 알라딘에서 받은 정보
    formData.append('image', detailBookPost.thumbnail); // 알라딘에서 받은 정보
    formData.append('rprice', detailBookPost.rprice); // 알라딘에서 받은 정보
    formData.append('content', content); // 사용자가 입력한 정보
    formData.append('tprice', price); // 사용자가 입력한 정보
    formData.append('division', detailBookPost.division); // 사용자가 입력한 정보
    dispatch(modifyBookPostRequest({ id: detailBookPost.articleId, data: formData }));
  }, [content, detailBookPost.articleId, detailBookPost.category, detailBookPost.division, detailBookPost.rprice, detailBookPost.thumbnail, detailBookPost.title, dispatch, images, price]);
  useEffect(() => {
    setImages(detailBookPost.image);
    setPrice(detailBookPost.tprice);
    setContent(detailBookPost.content);
  }, [detailBookPost.content, detailBookPost.image, detailBookPost.tprice]);
  return (
    <AddPostWrapper>
      <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      <AddPostHeader>
        <Logo src="/images/icons/logo.jpeg" alt="logo" />
        <button type="button" onClick={handleXButtonClick}>
          <DeleteImg src="/images/icons/x.png" alt="x_button" />
        </button>
      </AddPostHeader>
      <BookWrapper>
        <img src={detailBookPost.thumbnail} alt="thumbnail" />
        <BookDetail>
          <Category>{CategoryFormatUtil(detailBookPost.category)}</Category>
          <BookTitle>{detailBookPost.title}</BookTitle>
          <BookPrice>{detailBookPost.rprice} 원</BookPrice>
        </BookDetail>
      </BookWrapper>
      <Editor
        onSubmit={handleSubmitPost}
        onChangePrice={handleChangePrice}
        onChangeContent={handleChangeContent}
        onChangeImage={handleChangeImages}
        onDeleteImage={handleDeleteImage}
        isLoading={false} // 수정필요함
        content={content}
        price={price}
        images={images}
      />
    </AddPostWrapper>
  );
}
export default ModifyPost;
