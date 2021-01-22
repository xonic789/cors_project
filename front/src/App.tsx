import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Login from './features/login/Login';
import GlobalStyle from './styles/GlobalStyle';

function App():JSX.Element {
  return (
    <div className="App">
      <GlobalStyle />
      <div>
        <BrowserRouter>
          <Switch>
            <Route path="/" exact component={Login} />
          </Switch>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
