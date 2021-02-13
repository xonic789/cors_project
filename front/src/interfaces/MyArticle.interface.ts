export interface myArticleInterface {
    articleId: string;
    image: string;
    category: {
        cid: string;
        oneDepth: string;
        twoDepth: string;
        threeDepth: string;
        fourDepth: string;
        fiveDepth: string;
    };
    title: string;
    tprice: number;
}
