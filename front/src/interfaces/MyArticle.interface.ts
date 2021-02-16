interface categoryInterface {
    cid: number, oneDepth: string, twoDepth: string, threeDepth: string, fourDepth: string, fiveDepth: string,
}

export interface myArticleInterface {
    articleId: string;
    thumbnail: string;
    category: categoryInterface;
    progress: 'COMPLETED' | 'HIDE' | 'TRADING' | 'POSTING';
    title: string;
    tprice: number;
}
