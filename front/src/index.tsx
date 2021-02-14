import React from 'react';
import ReactDOM from 'react-dom';
import { configureStore, getDefaultMiddleware } from '@reduxjs/toolkit';
import logger from 'redux-logger';
import { Provider } from 'react-redux';
import createSagaMiddleware from 'redux-saga';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { PersistGate } from 'redux-persist/integration/react';
import reportWebVitals from './reportWebVitals';
import rootSaga from './rootSaga';
import rootReducer from './rootReducers';
import App from './App';
import './index.css';

const persistConfig = {
  key: 'root',
  storage,
};

const sagaMiddleware = createSagaMiddleware();

const middleware = [...getDefaultMiddleware(), logger, sagaMiddleware];

const store = configureStore({
  reducer: persistReducer(persistConfig, rootReducer),
  middleware,
});
sagaMiddleware.run(rootSaga);

const persistor = persistStore(store);

ReactDOM.render(
  <Provider store={store}>
    <React.StrictMode>
      <PersistGate loading={null} persistor={persistor}>
        <App />
      </PersistGate>
    </React.StrictMode>
  </Provider>,
  document.getElementById('root'),
);

reportWebVitals();
