# trekko (Server-Backend)

### REST API Documentation

# Overview

All production-grade endpoints are accessed from a single project server deployment url. Unless explicitly stated otherwise, data exchange occurs in JSON format.

```
https://trekko-server.onrender.com
```

## Bearer Access Tokens

For secure access to most endpoints, authentication is required using bearer access tokens retrieved on sign in. Simply include the token in the header as follows:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2NTc4YTBlOWUyYzQ3YTE0YzkyODNjOTgiLCJleHAiOjE3MDI0OTEwMTV9.8rp-64KxkzfPGkTE6F_bxAU3ZlSIutPYytKcAyYFQLpTvbfJHWssTSQI8MS_MzB_uv6O-8t05KfgDeldQKJu3w
```

## Rate Limit

TODO

# Auth

## Sign up a user

![POST](https://img.shields.io/badge/POST-blue)

Register a new user account using an email and password.

```php
POST /auth/signup
```

<details>
<summary>References</summary>

#### Request

```json
{
  "email": "email@example.com",
  "password": "password"
}
```

#### Response

##### `OK`

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

![POST](https://img.shields.io/badge/POST-blue)

Log in an user and receive a JWT access token.

```php
POST /auth/signin
```

<details>
<summary>References</summary>

#### Request

```json
{
  "email": "email@example.com",
  "password": "password"
}
```

#### Response

##### `OK`

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

## Add/Donate trips

![POST](https://img.shields.io/badge/POST-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

```php
POST /trips/batch
```

<details>
<summary>References</summary>

#### Request

```json
[
  {
    "uid": "bbG8a0c8283c98e9e2c47a14",
    "startTimestamp": 1703759287970,
    ...
  },
  ...
]
```

#### Response

##### `OK`

```
Status: 201 Created
```

```json
[
  {
    "id": "6578a0e9e2c47a14c8283c98",
    "uid": "bbG8a0c8283c98e9e2c47a14",
    "startTimestamp": 1703759287970,
    "endTimestamp": 1703759287972,
    "distance": 40,
    "vehicles": ["CAR"],
    "purpose": "Freizeit",
    "comment": null
  },
  ...
]
```

</details>

## Retrieve a trip

![GET](https://img.shields.io/badge/GET-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

```php
GET /trips/{uid}
```

- `uid`: The user's locally generated trip id

<details>
<summary>References</summary>

#### Request

#### Response

##### `OK`

```
Status: 200 Success
```

```json
{
  "id": "6578a0e9e2c47a14c8283c98",
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 1703759287970,
  ...
}
```

</details>

## Update a trip

![PUT](https://img.shields.io/badge/PUT-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

Update/replace the trip resource identified by `{uid}` by submitting a PUT request with the updated record **as a whole**.

```php
PUT /trips/{uid}
```

- `uid`: The user's locally generated trip id

<details>
<summary>References</summary>

#### Request

```json
{
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 1703759287970,
  ...
}
```

#### Response

##### `OK`

```
Status: 200 Success
```

```json
{
  "id": "6578a0e9e2c47a14c8283c98",
  "uid": "bbG8a0c8283c98e9e2c47a14",
  "startTimestamp": 1703759287970,
  ...
}
```

</details>

## Delete a trip

![DELETE](https://img.shields.io/badge/DELETE-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

```php
DELETE /trips/{uid}
```

- `uid`: The user's locally generated trip id

<details>
<summary>References</summary>

#### Request

#### Response

##### `OK`

```
Status: 204 No Content
```

</details>


# Profile

A user's profile represents its filled out onboarding project form.

## Retrieve profile

![GET](https://img.shields.io/badge/GET-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

```php
GET /profile
```

<details>
<summary>References</summary>

#### Request

#### Response

##### `OK`

```
Status: 200 Success
```

```json
{
  "homeOffice": false,
  "gender": "male",
  "age": 21
}
```

</details>

## Update/set profile

![POST](https://img.shields.io/badge/POST-blue) ![Auth required](https://img.shields.io/badge/Auth%20required-8A2BE2)

```php
GET /profile
```

<details>
<summary>References</summary>

#### Request

```json
{
  "homeOffice": false,
  "gender": "female",
  "age": 21
}

```

#### Response

##### `OK`

```
Status: 201 Created
```

The submitted profile is verified against `form_template.json` to verify its integrity. In case of a malformed or invalid form submission, an error is returned.

##### `FAILED_INVALID_FORM_DATA`

```
Status: 401 Bad Request
```

```json
{
  "reason": "FAILED_INVALID_FORM_DATA"
}
```

</details>



# Form

The project's onboarding `form_template.json` is located inside the /resources directory. The file is statically served and schema-verified against `form_template.schema.json`.

## Retrieve form template

![GET](https://img.shields.io/badge/GET-blue)

```php
GET /form
```

<details>
<summary>References</summary>

#### Request

#### Response

##### `OK`

```
Status: 200 Success
```

```json
{
  "$schema": "./form_template.schema.json",
  "fields": [
    {
      "title": "Home Office",
      "key": "homeOffice",
      "type": "boolean",
      "required": false
    },
    {
      "title": "Geschlecht",
      "key": "gender",
      "type": "select",
      "required": true,
      "options": [
        {
          "title": "Männlich",
          "key": "male"
        },
        {
          "title": "Weiblich",
          "key": "female"
        },
        {
          "title": "Divers",
          "key": "divers"
        }
      ]
    },
    {
      "title": "Alter",
      "key": "age",
      "type": "number",
      "required": true
    }
  ]
}

```

</details>
