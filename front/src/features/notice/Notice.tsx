import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { noticeInterface } from '../../interfaces/NoticeInterface';
import numberArrayUtill from '../../utils/numberArrayUtill';
import { getNoticeRequest, toggleActiveNotice } from './noticeSlice';

const Layout = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
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
  margin-bottom: 1.5em;
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

const NoticeList = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;
  @media screen and (min-width: 455px) {
    width: 455px;
  }
`;

const NoticeItem = styled.li`
  width: 100%;
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #e0e0e0;
`;

const NoticeTitleBox = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  padding: 0.5em 1.5em;
`;

const NoticeText = styled.div`
  & h2 {
    font-size: 3.5vw;
    font-weight: bold;
    margin-bottom: 0.5em;
  }
  & p {
    font-size: 3vw;
  }
  @media screen and (min-width: 455px) {
    & h2 {
      font-size: 15.932px;
    }
    & p {
      font-size: 13.656px;
    }
  }
`;

const DetailButton = styled.button`
  cursor: pointer;
  font-size: 3vw;
  background: none;
  border: none;
  outline: none;
  & img {
      width: 1em;
      height: 1em;
  }
  @media screen and (min-width: 455px) {
    font-size: 13.656px;
  }
`;

const NoticeDetail = styled.div`
  padding: 0 1.6em;
  overflow: hidden;
  transition: 0.5s;
  font-size: 0.8em;
`;

const PaginationBox = styled.div`
  display: flex;
  padding: 1.5em 0;
  & img {
    cursor: pointer;
    width: 1em;
    height: 1em;
  }
`;

const PrevPage = styled.img``;

const PageNumbers = styled.div`
  display: flex;
`;

const PageNumber = styled.div`
`;

const PageLink = styled(Link)`
  padding: 0 0.5em;
`;

const NextPage = styled.img`
  width: 1em;
  height: 1em;
  transform: rotate(180deg);
`;

function Notice():JSX.Element {
  const [page, setPage] = useState<number>(1);
  const dispatch = useDispatch();
  const { noticeList, totalPage } = useSelector((state) => state.noticeSlice);

  useEffect(() => {
    dispatch(getNoticeRequest(page));
  }, [page, dispatch]);

  return (
    <Layout>
      <Header>
        <BackLink to="/mypage">
          <BackLogo src="/images/icons/back.png" />
        </BackLink>
        <h1>공지사항</h1>
      </Header>
      <NoticeList>
        {
          noticeList.map((notice: noticeInterface) => (
            <NoticeItem key={notice.noticeId}>
              <NoticeTitleBox>
                <NoticeText>
                  <h2>{notice.title}</h2>
                  <p>{notice.writeDate}</p>
                </NoticeText>
                <DetailButton onClick={() => dispatch(toggleActiveNotice(notice.noticeId))}>
                  <img src="/images/icons/back.png" alt="" style={{ transform: notice.active ? 'rotate(90deg)' : 'rotate(-90deg)' }} />
                </DetailButton>
              </NoticeTitleBox>
              <NoticeDetail style={{ height: notice.active ? 'auto' : '0', padding: notice.active ? '1em 1.5em' : '0 1.5em' }}>
                <p>
                  {notice.content}
                </p>
              </NoticeDetail>
            </NoticeItem>
          ))
        }
      </NoticeList>
      <PaginationBox>
        <PrevPage src="/images/icons/back.png" />
        <PageNumbers>
          {
            numberArrayUtill(totalPage).map((i) => (
              <PageNumber>
                <PageLink onClick={() => setPage(i - 1)} to={`/notice?page=${i - 1}`}>{i}</PageLink>
              </PageNumber>
            ))
          }
        </PageNumbers>
        <NextPage src="/images/icons/back.png" />
      </PaginationBox>
    </Layout>
  );
}

export default Notice;
