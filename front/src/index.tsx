import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import logger from 'redux-logger';
import rootReducer from './rootReducers';
import rootSaga from './rootSaga';

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

const sagaMiddleware = createSagaMiddleware();

const middleware = [...getDefaultMiddleware(), logger, sagaMiddleware];
sagaMiddleware.run(rootSaga);

const store = configureStore({
  reducer: rootReducer,
  middleware,
});

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
  document.getElementById('root'),
);

reportWebVitals();
