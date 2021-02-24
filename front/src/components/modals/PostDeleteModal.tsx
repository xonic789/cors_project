import React from 'react';
import AskModal from './AskModal';

interface PostDeleteModalInterface {
  visible:boolean,
  onConfirm: () => void,
  onCancel: () => void,
}
function PostDeleteModal({ visible, onConfirm, onCancel }: PostDeleteModalInterface):JSX.Element {
  return (
    <AskModal
      visible={visible}
      title="게시글삭제"
      description="게시글을 정말로 삭제하시겠습니까?"
      confirmText="삭제"
      cancelText="취소"
      onConfirm={onConfirm}
      onCancel={onCancel}
    />
  );
}

export default PostDeleteModal;
