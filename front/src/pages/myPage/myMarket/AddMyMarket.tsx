import React, { useState } from 'react';
import styled from 'styled-components';
import { Link, useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import DaumPostCode, { AddressData } from 'react-daum-postcode';
import { ToastsContainer, ToastsContainerPosition, ToastsStore } from 'react-toasts';
import { addMarketRequest } from '../../market/marketSlice';

const Layout = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
  flex-direction: column;
  font-size: 4.5vw;
  @media screen and (min-width: 455px) {
    font-size: 20.484px;
  }
`;

const Header = styled.header`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
  border-bottom: 1px solid #e0e0e0;
  padding: 0.8em 0;
  & h1 {
    font-size: 4.5vw;
    font-weight: 400;
  }
  @media screen and (min-width: 455px) {
    width: 455px;
    & h1 {
      font-size: 20.484px;
    }
  }
`;

const BackLink = styled(Link)`
  position: absolute;
  left: 0.5em;
`;

const BackLogo = styled.img`
  width: 1.8em;
  height: 1.8em;
`;

const Content = styled.div`
  width: 100%;
  height: 100%;
  overflow: auto;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const BackgroundImgBox = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  width:100%;
  height: 50vw;
  background: #000;
  margin-bottom: 3em;
  @media screen and (min-width: 455px) {
    height: 227.6px;
  }
`;

const BackgroundImg = styled.img`
  width: 100%;
  height: 50vw;
  object-fit: cover;
  @media screen and (min-width: 455px) {
    height: 227.6px;
  }
`;

const MarketImg = styled.img`
  position: absolute;
  bottom: -2em;
  width: 6em;
  height: 6em;
  border-radius: 50%;
  object-fit: cover;
`;

const InputsBox = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1em;
`;
const InputItem = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1em;
  & label {
    display: block;
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  & textarea {
    height: 7em;
    resize: none;
    padding: 0.5em 0.5em;
    background: #f9f9f9;
    box-shadow: 3px 3px 10px #c4c4c4;
    border: none;
    border-radius: 5px;
    outline: none;
    font-size: 4vw;
  }
  @media screen and (min-width: 460px) {
    & label {
      font-size: 16.1px;
    }
    & textarea {
      font-size: 18.4px;
    }
  }
`;

const Input = styled.input`
  width: 100%;
  font-size: 4vw;
  background: none;
  border: none;
  outline: none;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;

const InputBox = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  padding: 0.5em 0.5em;
  background: #f9f9f9;
  box-shadow: 3px 3px 10px #c4c4c4;
  border-radius: 5px;
  overflow: hidden;
`;

const AddressFormBox = styled.div`
  margin-bottom: 1em;
  & label {
    display: block;
    font-size: 3.5vw;
    margin-bottom: 0.5em;
  }
  & ${InputBox}:not(:last-child) {
    margin-bottom: 1em;
  }
  & ${InputBox} {
    padding: 0.5em;
  }

  @media screen and (min-width: 460px) {
    & label {
      font-size: 16.1px;
    }
  }
`;

const AddressSearchBox = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 1em;
`;
const AddressSearchInput = styled.input`
  width: 10em;
  padding: 0.6em 0.5em;
  background: #f9f9f9;
  box-shadow: 3px 3px 10px #c4c4c4;
  border: none;
  outline: none;
  border-radius: 5px;
  margin-right: 0.5em;
  font-size: 4vw;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;
const AddressSearchButton = styled.button`
  padding: 0.7em;
  background: #265290;
  color: #fff;
  font-weight: bold;
  border: none;
  outline: none;
  border-radius: 5px;
  font-size: 3.5vw;
  @media screen and (min-width: 460px) {
    font-size: 16.1px;
  }
`;

const SearchModal = styled.div`
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: #5c5c5c55;
`;

const CloseButton = styled.img`
  position: absolute;
  width: 1.5em;
  height: 1.5em;
  top: 0.4em;
  right: 0.4em;
  font-size: 6vw;
  @media screen and (min-width: 460px) {
    font-size: 27.6px;
  }
`;

const SubmitButtonBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  padding-bottom: 1em;
`;

const SubmitButton = styled.button`
  width: 90%;
  font-size: 4vw;
  padding: 1em 0;
  color: #fff;
  background: #265290;
  @media screen and (min-width: 460px) {
    font-size: 18.4px;
  }
`;

function MyMarket():JSX.Element {
  const dispatch = useDispatch();
  const history = useHistory();
  const [file, setFile] = useState<File | null>(null);
  const [imgView, setImgView] = useState<any>('/images/icons/init_market.png');
  const [marketInfo, setMarketInfo] = useState({
    name: '',
    intro: '',
  });
  const [addressInputs, setAddress] = useState({
    zipcode: '',
    baseAddress: '',
    detailAddress: '',
  });
  const [showsModal, setShowsModal] = useState(false);

  const onChangeMarketImage = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files !== null) {
      const reader = new FileReader();
      const imgFile = e.target.files[0];
      reader.onloadend = () => {
        setImgView(reader.result);
        setFile(imgFile);
      };
      reader.readAsDataURL(imgFile);
    }
  };

  const onCompleteAddressSelect = (data: AddressData) => {
    const { zonecode, address } = data;
    setAddress({
      ...addressInputs,
      zipcode: zonecode,
      baseAddress: address,
    });
    setShowsModal(false);
  };

  const onChangeMarketInfo = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setMarketInfo({
      ...marketInfo,
      [name]: value,
    });
  };

  const onChangeDetailAddress = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAddress({
      ...addressInputs,
      detailAddress: e.target.value,
    });
  };

  const onSubmitAddMarket = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const market = new FormData();

    if (file === null) {
      alert('마켓이름을 입력해주세요.');
    } else if (marketInfo.intro === '') {
      alert('마켓소개를 입력해주세요.');
    } else if (addressInputs.zipcode === '') {
      alert('주소를 입력해주세요.');
    } else {
      market.append('image', file);
      market.append('name', marketInfo.name);
      market.append('intro', marketInfo.intro);
      market.append('location', `${addressInputs.baseAddress}`);
      console.log('두번인것인가?');
      dispatch(addMarketRequest({ market, history, ToastsStore }));
    }
  };

  return (
    <Layout onSubmit={onSubmitAddMarket}>
      <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      <Header>
        <BackLink to="/mypage">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>마켓 등록하기</h1>
      </Header>
      <Content>
        <BackgroundImgBox>
          <BackgroundImg src="/images/icons/addMarket_back.jpg" />
          <MarketImg src={imgView} />
        </BackgroundImgBox>
        <InputsBox>
          <InputItem>
            <label>마켓 이미지</label>
            <input type="file" onChange={onChangeMarketImage} />
          </InputItem>
          <InputItem>
            <label>마켓명</label>
            <InputBox>
              <Input name="name" onChange={onChangeMarketInfo} value={marketInfo.name} type="text" />
            </InputBox>
          </InputItem>
          <InputItem>
            <label>마켓소개</label>
            <textarea name="intro" onChange={onChangeMarketInfo} value={marketInfo.intro} />
          </InputItem>
          <AddressFormBox>
            <label>마켓주소</label>
            <AddressSearchBox>
              <AddressSearchInput disabled value={addressInputs.zipcode} />
              <AddressSearchButton type="button" onClick={() => setShowsModal(true)}>우편번호 검색</AddressSearchButton>
            </AddressSearchBox>
            <InputBox>
              <Input type="text" disabled placeholder="상세 주소" value={addressInputs.baseAddress} />
            </InputBox>
            <InputBox>
              <Input type="text" placeholder="나머지 주소" onChange={onChangeDetailAddress} value={addressInputs.detailAddress} />
            </InputBox>
          </AddressFormBox>
        </InputsBox>
        <SubmitButtonBox>
          <SubmitButton type="submit">마켓 등록하기</SubmitButton>
        </SubmitButtonBox>
        <SearchModal style={{ display: showsModal ? 'flex' : 'none' }}>
          <CloseButton src="/images/icons/x.png" onClick={() => setShowsModal(false)} />
          <DaumPostCode onComplete={onCompleteAddressSelect} />
        </SearchModal>
      </Content>
    </Layout>
  );
}

export default MyMarket;
