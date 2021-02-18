import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import NotFound from './components/NotFound';
import AddPostPage from './features/addPost/AddPostPage';
import DetailPostView from './features/detailPostView/DetailPostView';
import Login from './pages/signIn/Login';
import PostList from './features/postList/PostList';
import Join from './pages/signUp/Join';
import ModifyProfile from './pages/myPage/modifyProfile/ModifyProfile';
import GlobalStyle from './styles/GlobalStyles';
import MyPage from './pages/myPage/MyPage';
import MySaleArticle from './pages/myPage/mySlaeArticle/MySaleArticle';
import MyPurchaseArticle from './pages/myPage/myPurchaseArticle/MyPurchaseArticle';
import Notice from './pages/myPage/notice/Notice';
import Review from './pages/myPage/review/Review';
import WishList from './pages/myPage/wishList/WishList';
import ChattingList from './features/chatting/ChattingList';
import Chatting from './features/chatting/Chatting';
import Market from './features/market/Market';
import Question from './features/question/question';

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
            <Route path="/join" exact component={Join} />
            <Route path="/mypage" exact component={MyPage} />
            <Route path="/mypage/modify" exact component={ModifyProfile} />
            <Route path="/mypage/sales" exact component={MySaleArticle} />
            <Route path="/mypage/purchase" exact component={MyPurchaseArticle} />
            <Route path="/mypage/wishs" exact component={WishList} />
            <Route path="/notice" exact component={Notice} />
            <Route path="/review" exact component={Review} />
            <Route path="/question" exact component={Question} />
            <Route component={NotFound} />
          </Switch>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
