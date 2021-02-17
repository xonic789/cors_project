import React from 'react';
import ReactDOM from 'react-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import { Provider } from 'react-redux';
import { persistReducer, persistStore } from 'redux-persist';
import { PersistGate } from 'redux-persist/integration/react';
import storage from 'redux-persist/lib/storage';
import createSagaMiddleware from 'redux-saga';
import reportWebVitals from './reportWebVitals';
import rootSaga from './rootSaga';
import rootReducer from './rootReducers';

import App from './App';
import './index.css';

const sagaMiddleware = createSagaMiddleware();

const middleware = [...getDefaultMiddleware(), logger, sagaMiddleware];

const persistConfig = {
  key: 'root',
  storage,
};

const store = configureStore({
  reducer: persistReducer(persistConfig, rootReducer),
  middleware,
});
const persistor = persistStore(store);

sagaMiddleware.run(rootSaga);

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <PersistGate loading={null} persistor={persistor} />
      <App />
    </React.StrictMode>
  </Provider>,
  document.getElementById('root'),
);

reportWebVitals();
