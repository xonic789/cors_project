import React from 'react';
import ReactDOM from 'react-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import { Provider } from 'react-redux';
import logger from 'redux-logger';
import rootReducer from './rootReducers';

import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const middleware = [...getDefaultMiddleware(), logger];

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
