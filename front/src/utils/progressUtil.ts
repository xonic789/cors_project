const ProgressUtil = (progress: string):string => {
  if (progress === 'POSTING') {
    return '판매중';
  }
  if (progress === 'COMPLETED') {
    return '완료';
  }
  if (progress === 'HIDE') {
    return '숨기기중';
  }
  if (progress === 'TRADING') {
    return '거래예약중';
  }
  return '';
};

export default ProgressUtil;
