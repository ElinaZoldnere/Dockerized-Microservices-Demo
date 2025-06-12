// Run with: dotenv -e .env.local k6 run insurance-calculator-app/loadtesting/smoke-v2.js

import { check } from 'k6';
import http from 'k6/http';
import encoding from 'k6/encoding';

export const options = {
  thresholds: {
    // http errors should be less than 1%, otherwise abort the test
    http_req_failed: [{ threshold: 'rate<0.01', abortOnFail: true }],
    // 99% of requests should be below 1s
    http_req_duration: ['p(99)<1000'],
  },
  scenarios: {
    smoke: {
      executor: 'constant-vus',
      vus: 5,
      duration: '30s',
    },
  },
};

const username = __ENV.INSUR_CALC_USERNAME;
const password = __ENV.INSUR_CALC_PASSWORD;

// build basic auth string
const credentials = `${username}:${password}`;
const encodedCredentials = encoding.b64encode(credentials); // k6 provides b64encode
const authHeader = `Basic ${encodedCredentials}`;

const payload = JSON.stringify({
  "agreementDateFrom": "2026-03-10",
  "agreementDateTo": "2026-04-25",
  "selectedRisks": ["TRAVEL_MEDICAL", "TRAVEL_CANCELLATION"],
  "country": "SPAIN",
  "persons": [
    {
     "personFirstName": "Andris",
     "personLastName": "Bērziņš",
     "personalCode": "123456-12345",
     "personBirthDate": "1990-01-01",
     "medicalRiskLimitLevel": "LEVEL_15000",
     "travelCost": "6000"
    },
    {
     "personFirstName": "Kārlis",
     "personLastName": "Auziņš",
     "personalCode": "123456-54321",
     "personBirthDate": "1989-01-01",
     "medicalRiskLimitLevel": "LEVEL_15000",
     "travelCost" : "6000"
    }
  ]
});

export default function () {
  const url = 'http://localhost:8080/insurance/travel/api/v2/';

  const params = {
    headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
    },
  };

  // send a post request and save response as a variable
  const res = http.post(url, payload, params);

  // check that response is 200
  check(res, {
    'response code was 200': (res) => res.status == 200,
  });
}
