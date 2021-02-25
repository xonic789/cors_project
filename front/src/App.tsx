import React from 'react';
import { HashRouter, Switch, Route, BrowserRouter } from 'react-router-dom';
import NotFound from './components/NotFound';
import AddPostPage from './pages/post/addPost/AddPostPage';
import DetailPostView from './pages/post/detailPostView/DetailPostView';
import PostList from './pages/post/postList/PostList';
import GlobalStyle from './styles/GlobalStyles';
import ChattingList from './pages/chatting/ChattingList';
import Chatting from './pages/chatting/Chatting';
import Market from './pages/market/Market';
import MarketDetail from './pages/market/MarketDetail';
import Login from './pages/signIn/Login';
import Join from './pages/signUp/Join';
import MyPage from './pages/myPage/MyPage';
import ModifyProfile from './pages/myPage/modifyProfile/ModifyProfile';
import MySaleArticle from './pages/myPage/mySlaeArticle/MySaleArticle';
import MyPurchaseArticle from './pages/myPage/myPurchaseArticle/MyPurchaseArticle';
import WishList from './pages/myPage/wishList/WishList';
import Notice from './pages/myPage/notice/Notice';
import Review from './pages/myPage/review/Review';
import Question from './pages/myPage/question/question';
import ModifyPost from './pages/post/modifyPost/ModifyPost';
import AddQuestion from './pages/myPage/question/AddQuestion';
import QuestionDetail from './pages/myPage/question/QuestionDetail';
import AddMarket from './pages/myPage/myMarket/AddMyMarket';

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
            <Route path="/addPost/:division/:type" exact component={AddPostPage} />
            <Route path="/modifyPost/:id" exact component={ModifyPost} />
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
            <Route path="/question" exact component={Question} />
            <Route path="/question/save" exact component={AddQuestion} />
            <Route path="/question/detail" exact component={QuestionDetail} />
            <Route path="/addMarket" exact component={AddMarket} />
            <Route component={NotFound} />
          </Switch>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
