import React, { useEffect, useCallback, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { NavLink, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { ToastsContainer, ToastsStore, ToastsContainerPosition } from 'react-toasts';
import CategoryFormatUtil from '../../../utils/categoryFormatUtil';
import countUtil from '../../../utils/countDaoUtil';
import ProgressUtil from '../../../utils/progressUtil';
import { deleteBookPostRequest } from '../postSlice';
import { postAddWishListRequest, postRemoveWishListRequest } from '../../signIn/userSlice';
import ImageSlide from './ImageSlide';
import timeForToday from '../../../utils/timeForToday';
import PostActionButton from './PostActionButton';
import { loadDetailBookPostRequest } from './detailViewSlice';

interface DetailPostInterface {
  id: number;
}
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
const Thumbnail = styled.div`
  display: flex;
  align-items: center;
  padding: 10px;
  border: 1px solid #e8e8e8;
  color: #3960a6;
  font-weight: 700;
  border-radius: 5px;
  & img {
    width: 80px;
    margin-right: 20px;
  }
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

function DetailPostContent({ id } :DetailPostInterface):JSX.Element {
  const dispatch = useDispatch();
  const [heart, setHeart] = useState(false);
  const { wishList, email } = useSelector((state: any) => state.userSlice.user);
  const { detailBookPost } = useSelector((state: any) => state.detailViewSlice);
  const isMe = detailBookPost.member.email === email;
  const history = useHistory();
  const HandleHeartButton = () => {
    if (heart) {
      dispatch(postRemoveWishListRequest(detailBookPost.articleId));
    } else {
      dispatch(postAddWishListRequest(detailBookPost.articleId));
    }
  };

  const deletePost = useCallback(() => {
    dispatch(deleteBookPostRequest(id));
    setTimeout(() => {
      history.push('/home');
      ToastsStore.success('삭제가 완료되었습니다.');
    }, 1000);
  }, [dispatch, history, id]);
  const editPost = useCallback(() => {
    dispatch(loadDetailBookPostRequest(id));
    history.push(`/modifyPost/${id}`);
  }, [dispatch, history, id]);

  useEffect(() => {
    setHeart(wishList.includes(detailBookPost.articleId));
  }, [wishList, detailBookPost]);

  return (
    <>
      <ImageSlide images={detailBookPost.image} />
      <ContentWrapper>
        <ContentTop>
          <ProfileWrapper>
            <ProfileImg src="/images/icons/init_profile.png" alt="profile" />
            <NickName>{detailBookPost.member.nickname}</NickName>
          </ProfileWrapper>
          <State>{ProgressUtil(detailBookPost.progress)}</State>
        </ContentTop>
        <ContentMain>
          <ContentTitle>{detailBookPost.title}</ContentTitle>
          <Category>{CategoryFormatUtil(detailBookPost.category)}</Category>
          <UploadeTime>{timeForToday(detailBookPost.writeDate)}</UploadeTime>
          <Thumbnail>
            <img src={detailBookPost.thumbnail} alt="thumnail" />
            <div>원가: {detailBookPost.rprice} 원</div>
          </Thumbnail>
          <Content>
            {detailBookPost.content}
          </Content>
          <AdditionalContent>{countUtil(detailBookPost.count)}</AdditionalContent>
          <OtherBooksButton>판매자의 다른도서 보러가기</OtherBooksButton>
          {!isMe && <Report>신고하기⚡️</Report>}
          {isMe && <PostActionButton onEdit={editPost} onRemove={deletePost} />}
        </ContentMain>
        <ContentBottom>
          <HeartButton onClick={HandleHeartButton}>
            <img src={heart ? '/images/icons/heart_active.png' : '/images/icons/heart.png'} alt="heartButton" />
          </HeartButton>
          <Price>{detailBookPost.tprice} 원</Price>
          <NavLink to={`/chatting/${id}`}>
            {!isMe && <ChattingButton>채팅하기</ChattingButton>}
          </NavLink>
        </ContentBottom>
        <ToastsContainer store={ToastsStore} position={ToastsContainerPosition.TOP_CENTER} lightBackground />
      </ContentWrapper>
    </>
  );
}

export default DetailPostContent;
