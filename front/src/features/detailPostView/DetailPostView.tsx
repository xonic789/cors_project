import React from 'react';
import ImageZoom from './ImageZoom';

function DetailPostView():JSX.Element {
  const images = [
    'https://i.pinimg.com/originals/14/42/80/144280730d980a74790187079c376f0c.jpg',
    'https://i.pinimg.com/originals/80/37/6e/80376e91064ee65b96f075438d40f104.jpg',
    'https://i.pinimg.com/736x/a5/e6/af/a5e6af76263a0f972772afe98898c3b8.jpg',
  ];
  return (
    <ImageZoom images={images} />
  );
}

export default DetailPostView;
