import React from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Login from './features/login/Login';

function App():JSX.Element {
  return (
    <div className="App">
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
