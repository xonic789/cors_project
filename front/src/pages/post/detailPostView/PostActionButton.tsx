import React, { useState } from 'react';
import styled from 'styled-components';
import PostDeleteModal from '../../../components/modals/PostDeleteModal';

interface PostActionButtonInterface {
  onEdit: () => void,
  onRemove: () => void,
}
interface ActionButtonColor {
  color: string,
}
const PostActionButtonBlock = styled.div`
  display: flex;
  align-items: center;
`;
const ActionButton = styled.button<ActionButtonColor>`
  border: 0;
  background-color: white;
  color: ${(props) => props.color};
  font-size: 15px;
  font-weight: 700;
  padding: 10px;
`;
function PostActionButton({ onEdit, onRemove }: PostActionButtonInterface):JSX.Element {
  const [modal, setModal] = useState(false);
  const handleRemoveClick = () => {
    setModal(true);
  };
  const onCancel = () => {
    setModal(false);
  };
  const onConfirm = () => {
    setModal(false);
    onRemove();
  };
  return (
    <>
      <PostActionButtonBlock>
        <ActionButton color="#0c5d8f" type="button" onClick={onEdit}>수정하기</ActionButton>
        <ActionButton color="#c22525" type="button" onClick={handleRemoveClick}>삭제하기</ActionButton>
      </PostActionButtonBlock>
      <PostDeleteModal
        visible={modal}
        onConfirm={onConfirm}
        onCancel={onCancel}
      />
    </>
  );
}
export default PostActionButton;
