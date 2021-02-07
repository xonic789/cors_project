interface categoryInterface {
  cid: number, oneDepth: string, twoDepth: string, threeDepth: string, fourDepth: string, fiveDepth: string,
}
const CategoryFormat = (category: categoryInterface) => {
  const cate = [category.oneDepth, category.twoDepth, category.threeDepth, category.fourDepth, category.fiveDepth];
  const newCate = [];
  for (let i = 0; i < cate.length; i++) {
    if (cate[i] === '') {
      return newCate.join('>');
    }
    newCate.push(cate[i]);
  }
  console.log(newCate);
  return newCate.join('>');
};

export default CategoryFormat;
