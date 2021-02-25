import React, { useCallback, useState } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';
import { ToastsContainer, ToastsStore, ToastsContainerPosition } from 'react-toasts';
import { addBookPostRequest } from '../postSlice';
import ImagePreView from './ImagePreView';
import ImageFileReaderPromise from '../../../utils/imageFileReader';
import { getAladinBook } from '../../../api/postBookApi';
import aladinIteminterface from '../../../interfaces/AladinInterface';
import SearchBook from './SearchBook';

interface ParamTypes {
  division: string
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
`;
const Logo = styled.img`
  width: 40px;
`;
const DeleteImg = styled.img`
  width: 20px;
  align-self: flex-start;
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
const SearchInput = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
  background-color: #e9e9e9;
  width: 100%;
  margin: auto;
  & input {
    border:0;
    width:80%;
    background-color: inherit;
  }
  & button {
    border: 0;
  }
  & img {
    width: 20px;
  }
`;

function AddPostPage():JSX.Element {
  const [searchTitle, setSearchTitle] = useState<string>('');
  const [searchResult, setSearchResult] = useState<aladinIteminterface[]>([]);
  const [title, setTitle] = useState<string>('');
  const [category, setCategory] = useState<string>('');
  const [cid, setCid] = useState<number>();
  const [thumbnail, setThumbnail] = useState<string>('');
  const [images, setImages] = useState<ImageURLInterface[]>([]);
  const [realPrice, setRealPrice] = useState<number>();
  const [price, setPrice] = useState<string>('');
  const [content, setContent] = useState<string>('');
  const [isOpenSearchBox, setIsOpenSearchBox] = useState<boolean>(false);
  const history = useHistory();

  const { isAddBookPostLoading } = useSelector((state: any) => state.postSlice);
  const dispatch = useDispatch();

  const { division } = useParams<ParamTypes>();
  const upperCaseDivision:string = division.toUpperCase();

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

  const loadSearchResultBook = async () => {
    getAladinBook(searchTitle).then(({ data }) => {
      const rgexData = data.replace(/\\/ig, '\\\\', /;/g, '');
      const deleteEndSemiconlonInData = rgexData.substr(0, rgexData.length - 1);
      const parseData = JSON.parse(deleteEndSemiconlonInData);
      console.log(parseData);
      setSearchResult(parseData.item);
      if (parseData.item.length === 0) {
        ToastsStore.error('검색결과가 없습니다');
      } else {
        setIsOpenSearchBox(true);
      }
    }).catch((error) => {
      console.log(error);
    });
  };

  const handleChangeSearch = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTitle(e.target.value);
  }, []);
  const handleClickSearch = () => {
    loadSearchResultBook();
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
  const handleClickItem = (item: aladinIteminterface) => {
    setCategory(item.categoryName);
    setTitle(item.title);
    setCid(item.categoryId);
    setThumbnail(item.cover);
    setRealPrice(item.priceStandard);
    setIsOpenSearchBox(false);
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
    formData.append('category', category); // category
    formData.append('cid', String(cid)); // 알라딘에서 받은 정보
    formData.append('title', title); // 알라딘에서 받은 정보
    formData.append('image', thumbnail); // 알라딘에서 받은 정보
    formData.append('rprice', String(realPrice)); // 알라딘에서 받은 정보
    formData.append('content', content); // 사용자가 입력한 정보
    formData.append('tprice', price); // 사용자가 입력한 정보
    formData.append('division', upperCaseDivision); // 사용자가 입력한 정보

    dispatch(addBookPostRequest({ data: formData }));
  }, [category, cid, content, dispatch, images, price, realPrice, thumbnail, title, upperCaseDivision]);
  return (
    <>
      <AddPostWrapper>
        {isOpenSearchBox && <SearchBook searchResult={searchResult} onClickItem={handleClickItem} />}
        <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
        <AddPostHeader>
          <Logo src="/images/icons/logo.jpeg" alt="logo" />
          <button type="button" onClick={handleXButtonClick}>
            <DeleteImg src="/images/icons/x.png" alt="x_button" />
          </button>
        </AddPostHeader>
        <SearchInput>
          <input placeholder="책 이름을 검색해보세요!" onChange={handleChangeSearch} />
          <button type="button" onClick={handleClickSearch}>
            <img src="/images/icons/search.png" alt="search_icon" />
          </button>
        </SearchInput>
        <BookWrapper>
          {
          title
            ? (
              <>
                <img src={thumbnail} alt="thumbnail" />
                <BookDetail>
                  <Category>{category}</Category>
                  <BookTitle>{title}</BookTitle>
                  <BookPrice>{realPrice} 원</BookPrice>
                </BookDetail>
              </>
            )
            : <div>책을 검색해서 등록하세요!</div>
        }
        </BookWrapper>
        <FormWrapper encType="multipart/form-data" onSubmit={handleSubmitPost}>
          <ImagePreView onChangeImage={handleChangeImages} images={images} onDelete={handleDeleteImage} />
          <BookDetailInputWrapper>
            <input type="text" pattern="[0-9]+" placeholder="₩ 가격입력" onChange={handleChangePrice} value={price} />
            <textarea onChange={handleChangeContent} value={content} placeholder="상품설명을 입력하세요" />
          </BookDetailInputWrapper>
          <AddPostButton type="submit" disabled={isAddBookPostLoading}>
            {isAddBookPostLoading ? <span>등록중입니다</span> : <span>등록하기</span>}
          </AddPostButton>
        </FormWrapper>
      </AddPostWrapper>
    </>
  );
}

export default AddPostPage;
