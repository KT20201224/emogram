Emogram: 감정 기반 기억 저장소

감정은 기억의 색이다. Emogram은 하루의 감정을 시각적인 구슬에 담아 기록하고, 회상하며, 되돌아보는 경험을 제공합니다.

🧠 프로젝트 개요

Emogram은 사용자의 감정을 구슬 형태로 시각화하고 저장하는 감정 기반 일기장 서비스입니다. 하루 동안의 감정을 시간순으로 기록하고, 3D 병 속에 시각적으로 표현하여 감정의 흐름을 한눈에 파악할 수 있도록 도와줍니다. 감정은 타입, 색상, 메모와 함께 저장되며, 사용자는 자신의 감정 히스토리를 시간순/감정별로 탐색할 수 있습니다.

🌐 기술 스택

🔹 Frontend
	•	React (TypeScript)
	•	Tailwind CSS
	•	Vite
	•	Recoil (상태 관리)
	•	React Router DOM
	•	Three.js (감정구슬 애니메이션)

🔹 Backend
	•	Spring Boot 3.4.x
	•	PostgreSQL (감정 유형 및 사용자 정보 저장)
	•	MongoDB (Memory Orb 저장)
	•	Redis (EmotionType 캐싱)
	•	Spring Security + JWT (인증 및 인가)



⚙️ 주요 기능
	•	회원가입 및 로그인
	•	JWT 기반 인증 시스템
	•	사용자 정보 조회/수정 기능 포함
	•	감정 유형 관리
	•	관리자가 감정 유형(type, color) 추가 가능
	•	Redis에 캐시하여 조회 성능 향상
	•	Memory Orb 기록
	•	날짜, 감정 타입, 색상, 메모 저장
	•	하루에 여러 개의 감정을 시간순 저장 가능
	•	감정 병(Home View)
	•	오늘의 감정을 담은 병을 렌더링
	•	구슬은 병 안에서 떠다니는 형태로 표현
	•	감정 타임라인
	•	감정을 시간순 정렬하여 애니메이션과 함께 보여줌
	•	감정 상세 보기 및 수정
	•	클릭 시 구슬 안을 들여다보듯 상세 내용을 확인
	•	수정 및 삭제 가능

🗂 프로젝트 구조
```
Emogram/
├── backend/                  # Spring Boot 백엔드 서버
│   ├── src/main/java/com/emogram
│   ├── resources/
│   └── ...
├── frontend/                 # React 프론트엔드 애플리케이션
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── assets/
├── docker-compose.yml
├── README.md
└── ...
```

🧭 향후 계획
	•	AI 감정 분석 기능 (OpenAI API 기반)
	•	감정 공유 및 감정 지도 기능
	•	타임캡슐 모드: 미래에 열람할 수 있는 감정 저장 기능
	•	Flutter 기반 모바일 앱 개발
	•	다국어 지원

이 프로젝트는 감정을 기록하고, 이해하고, 되돌아보는 과정을 통해 스스로를 더 깊이 이해하고자 하는 사람들을 위한 정서적 공간입니다.
