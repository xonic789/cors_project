import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import createSagaMiddleWare from 'redux-saga';
import App from './App';
import reportWebVitals from './reportWebVitals';
import LoginSlice from './features/login/LoginSlice';
import rootSaga from './features/login/LoginSaga';

const sagaMiddleWare = createSagaMiddleWare();

const store = createStore(LoginSlice, applyMiddleware(sagaMiddleWare));

sagaMiddleWare.run(rootSaga);

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
  document.getElementById('root'),
);

reportWebVitals();
