import React, { useState } from 'react';
import styled from 'styled-components';
import ImageSlide from './ImageSlide';

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
const ContentWrapper = styled.div`
`;
const ContentTop = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border-bottom: 1px solid #e8e8e8;
`;
const ContentMain = styled.div`
  display: flex;
  flex-direction: column;
  width: 90%;
  margin: 0 auto;
`;
const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  font-size: 20px;
`;
const ProfileImg = styled.img`
  width: 50px;
  height: 50px;
  border: 1px solid #e8e8e8;
  border-radius: 50%;
  padding: 2px;
`;
const NickName = styled.div`
  margin-left: 10px;
`;
const State = styled.div`
  color: #3960a6;
  border: 1px solid #3960a6;
  padding: 5px 10px;
  border-radius: 20px;
`;
const ContentTitle = styled.div`
  padding: 10px 0;
  font-size: 20px;
  font-weight: 600;
`;
const Category = styled.div`
  font-size: 13px;
  color: #919191;
`;
const Content = styled.div`
  padding: 20px 0;
`;
const AdditionalContent = styled.div`
  padding: 20px 0;
  font-size: 13px;
`;
const OtherBooksButton = styled.button`
  color: white;
  background: #3960a6;
  width: 100%;
  border: 0;
  padding: 10px;
  font-size: 15px;
  border-radius: 5px;
  margin: 0 auto;
`;
const ContentBottom = styled.div`
  max-width: 600px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  font-size: 20px;
  border-top: 1px solid #e8e8e8;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: white;
`;
const ChattingButton = styled.button`
  color: white;
  background: #3960a6;
  width: 40%;
  max-width: 200px;
  border: 0;
  padding: 10px;
  font-size: 15px;
  border-radius: 5px;
`;
const HeartButton = styled.button`
  background: transparent;
  border: 0;
  & img {
    width: 35px;
  }
`;
const Price = styled.div`
  font-weight: 800;
`;
const Report = styled.div`
  font-size: 15px;
  padding: 20px 0;
`;
const UploadeTime = styled.div`
  font-size: 13px;
  color: #919191;
  padding: 10px 0;
`;
function DetailPostView():JSX.Element {
  const [heart, setHeart] = useState(false);
  const images = [
    'https://i.pinimg.com/originals/14/42/80/144280730d980a74790187079c376f0c.jpg',
    'https://i.pinimg.com/originals/80/37/6e/80376e91064ee65b96f075438d40f104.jpg',
    'https://pgnqdrjultom1827145.cdn.ntruss.com/img/ef/a2/efa2dd37b944994045f508ea57846fa32d034b1dc5b90a5b84961e9a8af74fda_v1.jpg',
  ];
  const HandleHeartButton = () => {
    setHeart(!heart);
  };
  return (
    <DetailPostViewContainer>
      <Header><img src="/images/icons/back.png" alt="back_icon" /></Header>
      <ImageSlide images={images} />
      <ContentWrapper>
        <ContentTop>
          <ProfileWrapper>
            <ProfileImg src="/images/icons/init_profile.png" alt="profile" />
            <NickName>닉네임</NickName>
          </ProfileWrapper>
          <State>판매중</State>
        </ContentTop>
        <ContentMain>
          <ContentTitle>스토리텔링 바이블</ContentTitle>
          <Category>{'국내도서>소설>한국소설>판타지소설'}</Category>
          <UploadeTime>9초전</UploadeTime>
          <Content>
            <div>책정보: 흥부와 놀부이야기</div>
            <div>이책 한번밖에 안봤습니다.</div>
          </Content>
          <AdditionalContent>채팅 1 조회 10 찜 2</AdditionalContent>
          <OtherBooksButton>판매자의 다른도서 보러가기</OtherBooksButton>
          <Report>신고하기⚡️</Report>
        </ContentMain>
        <ContentBottom>
          <HeartButton onClick={HandleHeartButton}>
            <img src={heart ? '/images/icons/heart_active.png' : '/images/icons/heart.png'} alt="heartButton" />
          </HeartButton>
          <Price>12,000원</Price>
          <ChattingButton>채팅하기</ChattingButton>
        </ContentBottom>
      </ContentWrapper>
    </DetailPostViewContainer>
  );
}

export default DetailPostView;
