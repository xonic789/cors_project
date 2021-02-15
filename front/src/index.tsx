import React from 'react';
import ReactDOM from 'react-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import { Provider } from 'react-redux';
import createSagaMiddleware from 'redux-saga';
import reportWebVitals from './reportWebVitals';
import rootSaga from './rootSaga';
import rootReducer from './rootReducers';
import App from './App';
import './index.css';

const sagaMiddleware = createSagaMiddleware();

const middleware = [...getDefaultMiddleware(), logger, sagaMiddleware];

const store = configureStore({
  reducer: rootReducer,
  middleware,
});
sagaMiddleware.run(rootSaga);

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </Provider>,
  document.getElementById('root'),
);

reportWebVitals();
