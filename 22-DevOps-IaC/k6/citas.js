import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 20,
  duration: '30s',
  thresholds: {
    // TODO: define los umbrales y defiéndelos.
    // http_req_duration: ['p(95)<500'],
    // http_req_failed: ['rate<0.01'],
  },
};

export default function () {
  // TODO: reservar una cita contra la URL de tu entorno (variable de entorno BASE_URL)
  const res = http.get(`${__ENV.BASE_URL}/`);
  check(res, { 'responde 200': (r) => r.status === 200 });
  sleep(1);
}
