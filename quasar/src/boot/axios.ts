import axios, { type AxiosInstance } from 'axios';

const gatewayCAS: AxiosInstance = axios.create({ baseURL: 'https://115.120.246.30/cas' });
const gatewayCodeService: AxiosInstance = axios.create({ baseURL: 'https://115.120.246.30/code' });
const gatewayMessageService: AxiosInstance = axios.create({ baseURL: 'https://115.120.246.30/message' });
const gatewayArticleService: AxiosInstance = axios.create({ baseURL: 'https://115.120.246.30/article' });


export {
  axios,
  gatewayCAS,
  gatewayCodeService,
  gatewayMessageService,
  gatewayArticleService
};
