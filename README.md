# trekko (Server-Backend)

### REST API Documentation

# Overview

All production-grade endpoints are accessed from `TODO.example.com`. Unless explicitly stated otherwise, data exchange occurs in JSON format.

```
https://TODO.example.com
```

# Auth

## Sign up a user

Register a new user account using an email and password.

```php
POST /auth/signup
```

<details open>
<summary>References</summary>

#### Request

```json
{
  "email": "email@example.com",
  "password": "password"
}
```

#### Response

```
Status: 201 Created
```

```json
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w"
}
```

</details>

## Sign in

Log in an user and receive a JWT access token.

```php
POST /auth/signin
```

<details open>
<summary>References</summary>

#### Request

```json
{
  "email": "email@example.com",
  "password": "password"
}
```

#### Response

```
Status: 200 Status
```

```json
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w"
}
```

</details>

# Trips

## Add/Donate a trip

```php
POST /trips
```

<details open>
<summary>References</summary>

#### Request

```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w
```

```json
{
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 648294722,
  ...
}
```

#### Response

```
Status: 201 Created
```

```json
{
  "id": "6578a0e9e2c47a14c8283c98",
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 648294722,
  ...
}
```

</details>

## Retrieve a trip

```php
GET /trips/{uid}
```

- `uid`: The local user's locally generated trip id

<details open>
<summary>References</summary>

#### Request

```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w
```

#### Response

```
Status: 200 Success
```

```json
{
  "id": "6578a0e9e2c47a14c8283c98",
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 648294722,
  ...
}
```

</details>

## Update a trip

```php
PUT /trips/{uid}
```

- `uid`: The local user's locally generated trip id

<details open>
<summary>References</summary>

#### Request

```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w
```

#### Response

```
Status: 200 Success
```

```json
{
  "id": "6578a0e9e2c47a14c8283c98",
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 648294722,
  ...
}
```

</details>
