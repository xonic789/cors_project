import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from 'react-router-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import { Provider } from 'react-redux';
import { persistReducer, persistStore } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import storage from 'redux-persist/lib/storage';
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

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['userSlice'],
};

const store = configureStore({
  reducer: persistReducer(persistConfig, rootReducer),
  middleware,
});
const persistor = persistStore(store);

sagaMiddleware.run(rootSaga);

ReactDOM.render(
  <Router history={customHistory}>
    <Provider store={store}>
      <React.StrictMode>
        <PersistGate loading={null} persistor={persistor} />
        <App />
      </React.StrictMode>
    </Provider>
  </Router>,
  document.getElementById('root'),
);

reportWebVitals();
