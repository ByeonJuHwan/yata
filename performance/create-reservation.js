import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate, Trend } from 'k6/metrics';

const BASE_URL = __ENV.BASE_URL || 'http://223.130.154.128:8081';

const searchErrorRate = new Rate('search_errors');
const reservationErrorRate = new Rate('reservation_errors');
const searchResponseTime = new Trend('search_response_time');
const reservationResponseTime = new Trend('reservation_response_time');

export let options = {
    scenarios: {
        constant_search_load: {
            executor: 'constant-vus',
            vus: 50,
            duration: '4m',
            exec: 'searchHotels',
        },

        // 예약 트래픽 (점진적 증가)
        reservation_spike: {
            executor: 'ramping-vus',
            startVUs: 5,
            stages: [
                { duration: '1m', target: 10 },
                { duration: '1m', target: 30 },
                { duration: '1m', target: 50 },
                { duration: '1m', target: 10 },
            ],
            exec: 'createReservation',
        },
    },

    thresholds: {
        search_response_time: ['p(95)<500', 'p(99)<1000'],
        search_errors: ['rate<0.01'],
        reservation_response_time: ['p(95)<3000', 'p(99)<5000'],
        reservation_errors: ['rate<0.05'],
    },
};

// 조회 시나리오
export function searchHotels() {
    const start = Date.now();

    let res = http.get(`${BASE_URL}/api/v1/hotels/my`, {
        headers: {
            'Content-Type': 'application/json',
            'X-USER-ID': 'test'
        },
        tags: { type: 'search' },
        timeout: '10s',
    });

    const duration = Date.now() - start;
    searchResponseTime.add(duration);
    searchErrorRate.add(res.status !== 200);

    const success = check(res, {
        'search status 200': (r) => r.status === 200,
        'search fast response': (r) => duration < 1000,
    });

    if (duration > 1000) {
        console.warn(`⚠️ 조회 지연 발생: ${duration}ms (기대: <500ms)`);
    }

    sleep(1);
}

// 예약 시나리오
export function createReservation() {
    let payload = JSON.stringify({
        "roomId": "01K87QXRSK4KZ8ER27Z5R62P16",  // 같은 방
        "hotelId": "01K87QWSGQ8DXD9H2XER2PW93F"
    });

    const start = Date.now();

    let res = http.post(`${BASE_URL}/api/v1/reservation`, payload, {
        headers: {
            'Content-Type': 'application/json',
            'X-USER-ID': `test`  // 다른 사용자로 처리
        },
        tags: { type: 'reservation' },
        timeout: '15s',
    });

    const duration = Date.now() - start;
    reservationResponseTime.add(duration);
    reservationErrorRate.add(res.status !== 201);

    check(res, {
        'reservation status 201': (r) => r.status === 201,
        'no timeout': (r) => r.status !== 0,
    });

    if (res.status === 0 || res.status === 503) {
        console.error(`🔥 리소스 고갈 의심: Status=${res.status}, Duration=${duration}ms`);
    }

    if (duration > 3000) {
        console.warn(`예약 지연: ${duration}ms (Lock 대기 의심)`);
    }

    sleep(2);
}

// 테스트 시작 전
export function setup() {
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log('🎯 MSA 분리 정당성 검증 테스트 (조정)');
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log('📌 목표: 예약 부하 증가 시 조회 성능 저하 증명');
    console.log('📊 부하: 조회 50명, 예약 5→50명');
    console.log('🔥 전략: 동일한 객실 1개에 집중 공격');
    console.log(`📍 대상: ${BASE_URL}`);
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n');

    return { startTime: new Date().toISOString() };
}

// 테스트 종료 후
export function teardown(data) {
    console.log('\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
    console.log('✅ 테스트 완료');
    console.log(`⏰ 시작: ${data.startTime}`);
    console.log(`⏰ 종료: ${new Date().toISOString()}`);
    console.log('━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━');
}