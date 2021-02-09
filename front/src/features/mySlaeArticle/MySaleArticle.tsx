import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { getMySaleArticleRequest } from './mySaleArticleSlice';

function MySaleArticle():JSX.Element {
  const dispatch = useDispatch();

  useEffect(() => {
    console.log('asdasd');
    dispatch(getMySaleArticleRequest({ type: '' }));
  }, [dispatch]);

  return (
    <>
      <div>
        ㅁㄴㅇ
      </div>
    </>
  );
}

export default MySaleArticle;
