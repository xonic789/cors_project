import React from 'react';
import { HashRouter, Switch, Route } from 'react-router-dom';
import AppLayout from './components/AppLayout';
import Login from './features/login/Login';
import Join from './features/join/Join';
import GlobalStyle from './styles/GlobalStyles';

function App():JSX.Element {
  return (
    <div className="App">
      <GlobalStyle />
      <div>
        <HashRouter>
          <Switch>
            <Route path="/" exact component={Login} />
            <Route path="/join" exact component={Join} />
            <Route path="/home" exact component={AppLayout} />
          </Switch>
        </HashRouter>
      </div>
    </div>
  );
}

export default App;
