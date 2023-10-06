---


# dub_club_wanted_BE

## dub
동아리 홍보와, 동아리 지원을 할수있는 플랫폼


---

# 핵심기능

#### 1. 회원 가입

```일반 회원과 동아리 회원으로 나누어 가입이 가능합니다.```

#### 2. 동아리 등록

```동아리 회원은 동아리의 프로필 이미지, 태그, 소개글 등을 등록할 수 있습니다.```

#### 3. 동아리 공고 및 지원서 양식 등록

```동아리 회원은 동아리 내에서 공고를 올릴 수 있으며, 지원자들에게 지원서 양식을 제공할 수 있습니다.```

#### 4. 태그별 동아리 조회

```사용자는 태그별로 동아리를 검색하고 조회할 수 있습니다.```

#### 5. 동아리 지원서 다운로드 및 제출

```일반 회원은 동아리의 지원서 양식을 다운로드하여 작성한 후, 해당 동아리에 제출할 수 있습니다.```

#### 6. 동아리 회원별 지원자 조회

```동아리 회원은 자신의 동아리에 지원한 회원들을 조회할 수 있습니다.```

#### 7. 합격/불합격 통보 보내기

```동아리 회원은 지원자들에게 합격 또는 불합격 통보를 보낼 수 있습니다.```

---

# API 엔드포인트 목록 및 사용법

https://woozy-cuticle-bfb.notion.site/dub_-wanted-5f89e6bcf87142eca927893ff04703f6?pvs=4


---

# CI/CD Flow
![CIcd](https://github.com/s2hoon/dub_club_wanted_BE/assets/82464990/120399f7-7d09-4996-a8ac-631c4024a4fe)

1. main branch 에 Push 또는 Merge
2. Github 에 작성해둔 workflow file 로 Github Actions 수행
3. build, docker image build, docker image push 수행
4. EC2 인스턴스에서 docker image pull 후, run

---

# ERD
![기존](https://github.com/s2hoon/dub_club_wanted_BE/assets/82464990/ced95902-d874-4bc8-8d39-5b5a0da96a9e)


---

## Project Structure

```java
├─baseResponse
├─configuration
├─controller
├─domain
├─dto
│  ├─Club
│  ├─Comment
│  ├─Member
│  ├─OAuth
│  └─Post
├─repository
└─service
```

> +---baseResponse
|       BaseException.java
|       BaseResponse.java
|       BaseResponseStatus.java
> 
1. `BaseException.java`:
    - `BaseException`은 `RuntimeException` 클래스를 상속받고 있습니다. 이는 실행 중 발생할 수 있는 예외를 나타내는 클래스입니다.
    - `BaseException` 클래스는 `BaseResponseStatus`를 필드 값으로 가지고 있습니다. 즉, 예외가 발생했을 때 해당 예외의 원인을 `BaseResponseStatus`로 표시할 수 있습니다.
2. `BaseResponse.java`:
    - 모든 응답(Response) 객체는 이 클래스를 통해 생성됩니다.
    - 오버로딩(Overloading)을 통해 생성자가 구현되어 있으며, `result`를 매개변수로 받으면 요청이 성공한 경우를 나타내고, `result`를 매개변수로 받지 않으면 요청이 실패한 경우를 나타냅니다.
3. `BaseResponseStatus.java`:
    - 이 클래스는 열거형(Enum) 파일로 정의되어 있으며, 코드값과 메시지를 필드로 가지고 있습니다.
      `BaseResponse`나 `BaseException`에서 사용되는 상태 코드와 메시지를 정의하는 데 사용됩니다. 이러한 정의를 통해 각각의 상태를 나타내고 관리할 수 있습니다.

> +---configuration
|       ClientConfig.java
|       EncoderConfig.java
|       JwtTokenFilter.java
|       JwtTokenUtil.java
|       S3config.java
|       SecurityConfig.java
> 
1. `ClientConfig.java`:
    - 이 파일은 `RestTemplate`을 사용하기 위해 Spring 빈으로 등록합니다.
    - 모든 Rest API 요청은 이 빈을 통해 진행됩니다. `RestTemplate`은 HTTP 요청을 쉽게 수행하고 응답을 처리하는 데 사용됩니다.
2. `EncoderConfig.java`:
    - 이 파일은 `BCryptPasswordEncoder`를 Spring 빈으로 등록합니다.
    - 모든 비밀번호는 이 빈을 통해 암호화됩니다. `BCryptPasswordEncoder`는 보안성이 높은 비밀번호 해싱을 제공하는 데 사용됩니다.
3. `JwtTokenFilter.java`:
    - 이 파일은 JWT(Jason Web Token) 토큰을 검증하고 권한을 부여하는 로직을 포함합니다.
    - Spring Security 과 함께 사용됩니다.
4. `JwtTokenUtil.java`:
    - 이 파일은 JWT 토큰을 생성하는 로직을 포함합니다.
    - JWT는 인증된 사용자를 식별하고 정보를 보호하기 위한 토큰 방식입니다.
5. `S3config.java`:
    - 이 파일은 Amazon S3를 사용하기 위한 설정 파일입니다.
    - Amazon S3는 클라우드 기반의 파일 저장소이며, 파일 업로드 및 다운로드와 같은 작업을 수행하기 위한 설정을 제공합니다.
6. `SecurityConfig`:
    - 이 파일은 Spring Security를 적용하기 위한 설정 파일입니다.
    - Spring Security를 사용하여 사용자 인증, 권한 부여 및 보안 설정을 관리합니다.

> +---controller
|       ClubController.java
|       JspController.java
|       MemberController.java
|       PostController.java
> 

> +---service
|      ClubService.java
|      MemberService.java
|      PostService.java
|      RequestOAuthInfoService.java
> 

> +---repository
|       ClubRepository.java
|       MemberRepository.java
|       PostRepository.java
> 
1. `controller` 디렉토리:
    - 이 디렉토리는 컨트롤러(Controller) 클래스들을 포함합니다.
    - 컨트롤러는 클라이언트의 HTTP 요청을 처리하고 해당 요청에 대한 비즈니스 로직을 호출하며, 결과를 클라이언트에게 반환합니다.
2. `service` 디렉토리:
    - 이 디렉토리는 서비스(Service) 인터페이스 및 구현 클래스들을 포함합니다.
    - 서비스는 비즈니스 로직을 추상화하고, 컨트롤러와 리포지토리 간의 중간 계층 역할을 합니다. 비즈니스 로직을 수행하고 데이터베이스와 상호작용하는데 사용됩니다.
3. `repository` 디렉토리:
    - 이 디렉토리는 리포지토리(Repository) 인터페이스들을 포함합니다.
    - 리포지토리는 데이터베이스와의 상호작용을 담당하며, 데이터베이스에서 데이터를 검색하고 조작하는 데 사용됩니다.

> +---domain
|       BaseEntity.java
|       BaseTimeEntity.java
|       Club.java
|       Comment.java
|       Member.java
|       Post.java
|       Role.java
> 
1. `BaseEntity.java`:
    - 엔티티의 공통적인 속성을 정의하고, 이러한 속성을 상속받아 재사용될 수 있도록 합니다.
    - `createdBy` 와 `lastModifiedBy` 가 필드로 추가 되어 있습니다.
2. `BaseTimeEntity.java`:
    - 엔티티의 공통적인 속성을 정의하고, 이러한 속성을 상속받아 재사용될 수 있도록 합니다.
    - `createdDate` , `lastModifiedDate` 가 필드로 추가 되어 있습니다.
3. `Club.java`:
    - 동아리에 관한 엔티티 입니다.
4. `Comment.java`:
    - 댓글에 관한 엔티티 입니다.
5. `Member.java`:
    - 회원에 관한 엔티티 입니다.
6. `Post.java`:
    - 게시물에 관한 엔티티 입니다.
7. `Role.java`:
    - 역할을 열거형(Enum)으로 정의하여 사용자의 권한을 관리하는 데 사용됩니다.

---



# 성능개선




