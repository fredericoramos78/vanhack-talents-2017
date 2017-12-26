import axios from 'axios';

axios.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
axios.defaults.headers.common['Content-Type'] = 'application/json';

const AjaxInterface = axios.create({
    baseURL: 'http://localhost:9000/',
    responseType: 'json'
});

export default AjaxInterface;