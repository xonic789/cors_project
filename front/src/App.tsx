import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AppLayout from './components/AppLayout';
import Login from './features/login/Login';
import GlobalStyle from './styles/GlobalStyles';

function App():JSX.Element {
  return (
    <div className="App">
      <GlobalStyle />
      <div>
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={Login} />
            <Route path="/home" exact component={AppLayout} />
          </Switch>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
