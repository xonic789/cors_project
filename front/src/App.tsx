import React from 'react';
import { HashRouter, Switch, Route } from 'react-router-dom';
import DetailPostView from './features/detailPostView/DetailPostView';
import Login from './features/login/Login';
import PostList from './features/postList/PostList';
import GlobalStyle from './styles/GlobalStyles';

function App():JSX.Element {
  return (
    <div className="App">
      <GlobalStyle />
      <div>
        <HashRouter>
          <Switch>
            <Route path="/" exact component={Login} />
            <Route path="/home" exact component={PostList} />
            <Route path="/post/:id" exact component={DetailPostView} />
          </Switch>
        </HashRouter>
      </div>
    </div>
  );
}

export default App;
