# ModernSample

멀티 모듈 구조를 채택하여 **관심사의 분리(Separation of Concerns)**, **재사용성**, **빌드 속도 최적화**를 지향

## Module Hierarchy

```text
root
├── app                 # 최종 애플리케이션 모듈 (의존성 그래프의 최상위)
├── feature             # 사용자에게 보이는 화면 및 기능 단위 (독립적 실행 가능 지향)
│   ├── home            # 홈 화면 (UI + ViewModel + Tests)
│   ├── search          # 검색 화면
│   └── ...
└── core                # 앱 전반에서 공유되는 공통 로직 및 인프라
    ├── model           # 도메인 모델 (Pure Kotlin)
    ├── data            # 데이터 저장소 및 비즈니스 로직 처리 (Repository)
    ├── network         # 네트워크 API 통신 (Retrofit, DTO)
    ├── design          # 공통 UI 컴포넌트 및 테마 (Design System)
    ├── test            # Test Data 및 Test Repository
    └── domain          # 복잡한 유스케이스 (선택적 사용 예정)
```

## Feature Modules (:feature)

UI Layer: Jetpack Compose를 사용한 화면(Screen)과 상태 관리를 위한 ViewModel
androidTest: UI 테스트
test: ViewModel 및 비즈니스 로직 단위 테스트

## Core Modules (:core)

* core:model - 외부 의존성이 없는 순수 데이터 모델(Pure Data Class) 집합
* core:network - Retrofit/OkHttp 기반의 API 통신, DTO 및 DataSource 정의
* core:data	- Repository 패턴 구현 및 데이터 가공(Mapper)을 통한 데이터 처리 전략
* core:design - 앱 전반의 디자인 시스템(Color, Type, Theme) 및 공통 UI 컴포넌트
* core:test - Feature 테스트를 위한 Test 데이터 및 Test Repository 지원
* core:domain - [Optional] 복잡한 비즈니스 로직 및 Repository 조합 을 위한 UseCase 계층
  Tech Stack
  Language: Kotlin

## Stack

UI: Jetpack Compose (Material3)
DI: Hilt
Async: Coroutines & Flow
Network: Retrofit2 & OkHttp
Image Loader: Coil

**
local.properties에 
GITHUB_ACCESS_TOKEN=********
본인 GITHUB ACCESS TOKEN을 넣어서 실행하면 동작
**
