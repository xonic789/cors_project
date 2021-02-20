import React from 'react';
import { HashRouter, Switch, Route, BrowserRouter } from 'react-router-dom';
import NotFound from './components/NotFound';
import AddPostPage from './pages/post/addPost/AddPostPage';
import DetailPostView from './pages/post/detailPostView/DetailPostView';
import Login from './pages/login/Login';
import PostList from './pages/post/postList/PostList';
import Join from './pages/join/Join';
import ModifyProfile from './pages/modifyProfile/ModifyProfile';
import GlobalStyle from './styles/GlobalStyles';
import MyPage from './pages/mypage/MyPage';
import MySaleArticle from './pages/mySlaeArticle/MySaleArticle';
import MyPurchaseArticle from './pages/myPurchaseArticle/MyPurchaseArticle';
import Notice from './pages/notice/Notice';
import Review from './pages/review/Review';
import WishList from './pages/wishList/WishList';
import ChattingList from './pages/chatting/ChattingList';
import Chatting from './pages/chatting/Chatting';
import Market from './pages/market/Market';
import MarketDetail from './pages/market/MarketDetail';

function App():JSX.Element {
  return (
    <div className="App">
      <GlobalStyle />
      <div>
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={Login} />
            <Route path="/home" exact component={PostList} />
            <Route path="/post/:id" exact component={DetailPostView} />
            <Route path="/addPost/:division" exact component={AddPostPage} />
            <Route path="/chatting" exact component={ChattingList} />
            <Route path="/chatting/:id" exact component={Chatting} />
            <Route path="/market" exact component={Market} />
            <Route path="/market/:id" exact component={MarketDetail} />
            <Route path="/join" exact component={Join} />
            <Route path="/mypage" exact component={MyPage} />
            <Route path="/mypage/modify" exact component={ModifyProfile} />
            <Route path="/mypage/sales" exact component={MySaleArticle} />
            <Route path="/mypage/purchase" exact component={MyPurchaseArticle} />
            <Route path="/mypage/wishs" exact component={WishList} />
            <Route path="/notice" exact component={Notice} />
            <Route path="/review" exact component={Review} />
            <Route component={NotFound} />
          </Switch>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
