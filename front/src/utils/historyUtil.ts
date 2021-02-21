import { createBrowserHistory } from 'history';

const browserHistory = createBrowserHistory();

export function push(targetUrl: string):void {
  browserHistory.push(targetUrl);
}

export function redirect(targetUrl: string):void {
  browserHistory.replace(targetUrl);
}
