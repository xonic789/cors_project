import React, { useState } from 'react';
import { Prompt } from 'react-router-dom';

function CustomPrompt():JSX.Element {
  const [isPromptOpen, setIsPromptOpen] = useState(false);
  const [nextLocation, setNextLocation] = useState(null);
  const [confirmNavigation, setConfirmNavigation] = useState(false);
  /* const handlePrompt = () => {

  } */
  return (
    <>
      {/* <Prompt when={isPromptOpen}/> */}
    </>
  );
}

export default CustomPrompt;
