import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from 'react-router-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import { Provider } from 'react-redux';
import ReduxThunk from 'redux-thunk';
import { createBrowserHistory } from 'history';
import createSagaMiddleware from 'redux-saga';
import reportWebVitals from './reportWebVitals';
import rootSaga from './rootSaga';
import rootReducer from './rootReducers';
import App from './App';
import './index.css';

const customHistory = createBrowserHistory();
const sagaMiddleware = createSagaMiddleware(
  {
    context: {
      history: customHistory,
    },
  },
);
const middleware = [ReduxThunk.withExtraArgument({ history: customHistory }), ...getDefaultMiddleware(), sagaMiddleware, logger];

const store = configureStore({
  reducer: rootReducer,
  middleware,
});

sagaMiddleware.run(rootSaga);

ReactDOM.render(
  <Router history={customHistory}>
    <Provider store={store}>
      <React.StrictMode>
        <App />
      </React.StrictMode>
    </Provider>
  </Router>,
  document.getElementById('root'),
);

reportWebVitals();
