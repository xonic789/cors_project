const ImageFileReaderPromise = (file: Blob): Promise<string> => new Promise((resolve, reject) => {
  const reader = new FileReader();
  reader.onload = () => {
    if (reader.result !== null) {
      resolve(reader.result as string);
    }
  };
  reader.onerror = reject;
  reader.readAsDataURL(file);
});

export default ImageFileReaderPromise;
