import { createGlobalStyle } from 'styled-components';
import { reset } from 'styled-reset';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const GlobalStyle = createGlobalStyle`
  ${reset}
  * {
    box-sizing: border-box;
  }
  a {
    color: inherit;
    text-decoration: none
  }
`;

export default GlobalStyle;
